package com.example.backendrestaurant.security.service;

import com.example.backendrestaurant.models.Categorie;

import java.util.List;
import java.util.Optional;

public interface CatgeorieService {

  Categorie addCategorie(Categorie categorie);

  List<Categorie> CategorieList() ;

  Categorie updateCategorie(Categorie categorie,Long id);

  void deleteCategorieById(Long id);


  List<Categorie> getCategoriesByName(String name);

Object getAllCategories();





}
