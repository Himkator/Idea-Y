package com.example.idea_y.repositories;

import com.example.idea_y.models.CompanyOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyOfferRepository extends JpaRepository<CompanyOffers, Long> {
    List<CompanyOffers> findAllByTitle(String title);
    List<CompanyOffers> findAllByLocation(String location);
}
