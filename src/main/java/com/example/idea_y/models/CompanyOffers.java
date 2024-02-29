package com.example.idea_y.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name="company_offers")
public class CompanyOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private String location;
    private String categories;
    @Column(name = "video", columnDefinition = "longblob")
    private byte[] video;
    private String telegram_link;
    private String linkedin_link;
    private String facebook_link;
    @Column(length = 25500)
    private String short_desc;
    @Column(length = 2550000)
    private String project_desc;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    private LocalDateTime dateOfCreate;
    @PrePersist
    private void init(){
        dateOfCreate=LocalDateTime.now();
    }
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ImageCompany> imageCompanyList=new ArrayList<>();
    private Long previewImageId;

    public  void addImageCompany(ImageCompany image){
        image.setCompanyOffers(this);
        imageCompanyList.add(image);
    }

}
