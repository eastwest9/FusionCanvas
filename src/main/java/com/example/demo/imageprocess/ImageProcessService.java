package com.example.demo.imageprocess;

import com.example.demo.generatorImage.GeneratorImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageProcessService {

    private final ImageProcessRepository imageProcessRepository;

    @Value("${MYPATH}")
    private String uploadDir;

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    public ImageProcess findFirstImage(){
        return imageProcessRepository.findFirstByOrderByIdDesc();
    }

    public void processImage(MultipartFile imageFile) {
        if(imageFile == null || imageFile.isEmpty()) {
            return;
        }
        try {
            String path =  uploadDir + "remove/";
            String fileName = generateUniqueFileName() + ".png";
            String filePath = path + fileName;

            // 이미지 파일 저장
            byte[] imageData = imageFile.getBytes();
            try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
                fos.write(imageData);
                fos.flush();

                // 이미지 정보 DB에 저장
                ImageProcess imageProcess = new ImageProcess();
                imageProcess.setFileName(fileName);
                imageProcess.setFilePath(path);
                imageProcess.setImageUrl("/image/remove/"+ fileName);
                imageProcessRepository.save(imageProcess);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImageProcessingException("Error saving image file.", e);
        }
    }
    private String generateUniqueFileName() {
        String uniqueFileName = UUID.randomUUID().toString();
        return uniqueFileName;
    }
}
