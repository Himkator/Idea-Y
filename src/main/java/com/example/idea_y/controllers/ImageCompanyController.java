package com.example.idea_y.controllers;

import com.example.idea_y.models.ImageCompany;
import com.example.idea_y.repositories.ImageCompanyRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
public class ImageCompanyController {
    private final ImageCompanyRepositories imageCompanyRepositories;

    @GetMapping("/imageCompany/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id){
        ImageCompany imageCompany=imageCompanyRepositories.findById(id).orElse(null);
        return ResponseEntity.ok().header("fileName", imageCompany.getName()).
                contentLength(imageCompany.getSize()).
                body(new InputStreamResource(new ByteArrayInputStream(imageCompany.getBytes())));
    }
}
