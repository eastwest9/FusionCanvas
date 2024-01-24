package com.example.demo.imageprocess;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/imageProcess")
@Controller
public class ImageProcessController {

    private final ImageProcessService imageProcessService;


    @GetMapping("/concat")
    public String designImage(Model model) {
        ImageProcess imageProcess = imageProcessService.findFirstImage();
        model.addAttribute("imageProcess",imageProcess);
        return "imageConcat_form";
    }

    @PostMapping("/concat")
    public String receiveImage(@RequestParam("imageFile") MultipartFile imageFile) {
        imageProcessService.processImage(imageFile);
        return "imageConcat_form";
    }
}
