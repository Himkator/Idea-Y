package com.example.idea_y.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name="people_offers")
public class PeopleOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String location;
    @Column(name = "photo", columnDefinition = "longblob")
    private byte[] photo;
    private String occupation;
    private String telegram_link;
    private String linkedin_link;
    private String facebook_link;
    private String portfolio_link;
    @Column(length = 25500)
    private String biography;
    private String resume_link;
    private String categories;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    private LocalDateTime dateOfCreate;
    @PrePersist
    private void init(){
        dateOfCreate=LocalDateTime.now();
    }
}
