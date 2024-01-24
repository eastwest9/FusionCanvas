package com.example.demo.generatorImage;

import com.example.demo.imageprocess.ImageProcess;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class GeneratorImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "IMAGE_ID")
    private ImageProcess imageProcess;

    private String fileName;
    private String filePath;
    private String imageUrl;
}
