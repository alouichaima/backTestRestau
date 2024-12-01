package com.example.backendrestaurant.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backendrestaurant.models.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    @Query("SELECT c FROM Commande c WHERE c.user.id = :userId")
    public List<Commande> findMesCommandes(@Param("userId") Long userId);



}
