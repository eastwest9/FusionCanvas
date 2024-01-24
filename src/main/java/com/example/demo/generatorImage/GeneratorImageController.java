package com.example.demo.generatorImage;

import com.example.demo.text.Text;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@Controller
@RequestMapping("/generatorImage")
public class GeneratorImageController {

    private final GeneratorImageService generatorImageService;

    @PostMapping("/save")
    public String createGenerate(@RequestParam("imageFile") MultipartFile imageFile){
        generatorImageService.saveImage(imageFile);
        return "upload_form";
    }
    @GetMapping("/save")
    public String readGenerate(Model model){
        GeneratorImage gi = generatorImageService.findFirstImage();
        model.addAttribute("generatorImage",gi);
        return "upload_form";
    }

}
