package com.example.backendrestaurant.repository;

import com.example.backendrestaurant.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {


    List<Categorie> findAllByNameContaining(String name);
}

