package com.example.demo.generatorImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratorImageRepository extends JpaRepository<GeneratorImage, Long> {
    GeneratorImage findFirstByOrderByIdDesc();
}
