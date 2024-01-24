package com.example.demo.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImageForm {

    private MultipartFile attachFile;          // 첨부 파일
    private List<MultipartFile> imageFiles;    // 첨부 이미지
}
