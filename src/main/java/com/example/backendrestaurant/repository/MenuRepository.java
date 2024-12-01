package com.example.backendrestaurant.repository;

import com.example.backendrestaurant.models.Menu;
import com.example.backendrestaurant.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
