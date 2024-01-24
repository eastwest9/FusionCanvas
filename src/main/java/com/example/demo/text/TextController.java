package com.example.demo.text;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/text")
public class TextController {

    private final TextService textService;

    // 텍스트 조회
    @GetMapping("/input")
    public String inputText(TextForm textForm){
        return "text_form";
    }
    // 텍스트 입력
    @PostMapping("/input")
    public String inputText(@Valid TextForm textForm, BindingResult bindingResult, @RequestParam("text") String content){
        Text text = new Text();
        if (bindingResult.hasErrors()) {
            return "text_form";
        }
        this.textService.input(text.getId(), textForm.getText());
        try {
            this.textService.toFlask(content);
            return "redirect:/generatorImage/save";

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

}
