package com.example.backendrestaurant.repository;

import java.util.Optional;

import com.example.backendrestaurant.models.ERole;
import com.example.backendrestaurant.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

	boolean existsByName(ERole client);
}
