package com.example.backendrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendrestaurant.models.ListeChef;

@Repository

public interface ListeChefRepository extends JpaRepository<ListeChef,Long> {

}
