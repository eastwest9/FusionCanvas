package com.example.demo.image;
import com.example.demo.generatorImage.GeneratorImage;
import com.example.demo.generatorImage.GeneratorImageService;
import com.example.demo.imageprocess.ImageProcess;
import com.example.demo.imageprocess.ImageProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/image")
@Controller
public class ImageController {

    private final ImageService imageService;



    @Value("${MYPATH}")
    private String uploadDir;

    @GetMapping("/upload")
    public String uploadImage(ImageForm imageForm){
        return "upload_form";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        // 이미지 업로드 및 처리 로직 작성
        try {
            imageService.upload(imageFile);
            return "redirect:/imageProcess/concat";

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

}


