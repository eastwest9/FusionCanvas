package com.example.demo.imageprocess;

import com.example.demo.generatorImage.GeneratorImage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image_process")
public class ImageProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "imageProcess")
    private GeneratorImage generatorImage;

    private String fileName;
    private String filePath;
    private String imageUrl;
}
