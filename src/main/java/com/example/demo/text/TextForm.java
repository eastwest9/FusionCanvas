package com.example.demo.text;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextForm {

    @NotEmpty(message = "내용은 필수 항목입니다.")
    @Size(max=200)
    private String text;
}
