package com.example.demo.image;


import com.example.demo.imageprocess.ImageProcess;
import com.example.demo.imageprocess.ImageProcessRepository;
import com.example.demo.imageprocess.ImageProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageProcessRepository imageProcessRepository;
    private final RestTemplate restTemplate;

    @Value("${MYPATH}")
    private String uploadDir;

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    public void upload(MultipartFile imageFile) throws IOException {

        String path = uploadDir + "original/";
        // 이미지 업로드
        String fileName = imageFile.getOriginalFilename();
        File dest = new File(path + fileName);
        imageFile.transferTo(dest);

        Image image = new Image();
        image.setFileName(fileName);
        image.setFilePath(path);
        image.setImageUrl("/image/original/" + fileName); // 이미지 URL 설정
        imageRepository.save(image); // 이미지 로컬에 저장

        // 이미지 파일을 바이트 배열로 읽기
        byte[] imageBytes = Files.readAllBytes(dest.toPath());

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // HTTP 요청 본문에 이미지 바이트 배열 추가
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(imageBytes, headers);

        // 플라스크 서버로 HTTP POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(flaskServerUrl+"removeImage", HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Image successfully processed by Flask.");
        } else {
            System.err.println("Error processing image.");
        }
    }
    public List<Image> getOriginalImages() {
        return imageRepository.findAll();
    }
    public List<ImageProcess> getProcessImages() {
        return imageProcessRepository.findAll();
    }


    private String generateUniqueFileName() {
        String uniqueFileName = UUID.randomUUID().toString();
        return uniqueFileName;
    }

}
