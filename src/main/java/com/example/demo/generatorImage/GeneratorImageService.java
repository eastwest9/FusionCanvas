package com.example.demo.generatorImage;


import com.example.demo.DataNotFoundException;
import com.example.demo.imageprocess.ImageProcess;
import com.example.demo.imageprocess.ImageProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class GeneratorImageService {

    private final GeneratorImageRepository generatorImageRepository;

    @Value("${MYPATH}")
    private String uploadDir;

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    public GeneratorImage readImage(Long id){
        Optional<GeneratorImage> optionalGeneratorImage = generatorImageRepository.findById(id);
        if(optionalGeneratorImage.isPresent()){
            return optionalGeneratorImage.get();
        }else{
            throw new DataNotFoundException("image not found");
        }
    }

    public GeneratorImage findFirstImage(){
        return generatorImageRepository.findFirstByOrderByIdDesc();
    }


    public void saveImage(MultipartFile imageFile){

        try {
            String fileName = generateUniqueFileName() + ".png";
            String filePath = uploadDir+ "generate/";

            // 이미지 파일 저장
            byte[] imageData = imageFile.getBytes();
            try (FileOutputStream fos = new FileOutputStream(new File(filePath + fileName))) {
                fos.write(imageData);
                fos.flush();

                // 이미지 정보 DB에 저장
                GeneratorImage generatorImage = new GeneratorImage();
                generatorImage.setFileName(fileName);
                generatorImage.setFilePath(filePath);
                generatorImage.setImageUrl("/image/generate/" + fileName);
                generatorImageRepository.save(generatorImage);
            }
        } catch (
        IOException e) {
            e.printStackTrace();
            throw new ImageProcessingException("Error saving image file.", e);
        }

    }
    private String generateUniqueFileName() {
        String uniqueFileName = UUID.randomUUID().toString();
        return uniqueFileName;
    }

}
