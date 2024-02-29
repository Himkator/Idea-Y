package com.example.idea_y.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "imageCompany")
@AllArgsConstructor
@NoArgsConstructor
public class ImageCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long size;
    private boolean isPreviewImage;
    @Column(name = "bytes", columnDefinition = "longblob")
    private byte[] bytes;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private CompanyOffers companyOffers;
}
