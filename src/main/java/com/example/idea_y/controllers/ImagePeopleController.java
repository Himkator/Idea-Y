package com.example.idea_y.controllers;

import com.example.idea_y.models.PeopleOffers;
import com.example.idea_y.repositories.PeopleOffersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
public class ImagePeopleController {
    private final PeopleOffersRepository peopleOffersRepository;
    @GetMapping("/imagePeople/{id}")
    public ResponseEntity<?> getImagePeopleById(@PathVariable Long id){
        PeopleOffers peopleOffers=peopleOffersRepository.findById(id).orElse(null);
        return ResponseEntity.ok().
                body(new InputStreamResource(new ByteArrayInputStream(peopleOffers.getPhoto())));
    }
}
