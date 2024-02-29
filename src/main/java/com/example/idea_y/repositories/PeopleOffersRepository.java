package com.example.idea_y.repositories;

import com.example.idea_y.models.PeopleOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleOffersRepository extends JpaRepository<PeopleOffers, Long> {
    List<PeopleOffers> findAllByName(String name);
    List<PeopleOffers> findAllByLocation(String location);
}
