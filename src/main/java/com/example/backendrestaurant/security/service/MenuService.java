package com.example.backendrestaurant.security.service;

import com.example.backendrestaurant.models.Categorie;
import com.example.backendrestaurant.models.Menu;
import com.example.backendrestaurant.models.MenuItem;

import java.util.List;
import java.util.Map;

public interface MenuService {

  List<Categorie> getMenuWithCategories();

  Menu addMenu(MenuItem item, Categorie categorie);

  List<Menu> MenuList() ;

  Menu updateMenu(Menu menu,Long id);

  void deleteMenuById(Long id);

}
