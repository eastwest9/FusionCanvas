package com.example.demo.imageprocess;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProcessRepository extends JpaRepository<ImageProcess, Long> {
    ImageProcess findFirstByOrderByIdDesc();
}

