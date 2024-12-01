package com.example.backendrestaurant.security.service;

import com.example.backendrestaurant.models.MenuItem;

import java.util.List;
import java.util.Set;

public interface MenuItemService {

  MenuItem addItem(MenuItem item);

  List<MenuItem> itemsList() ;

  MenuItem updateItem(MenuItem item, Long id);

  void deleteItemById(Long id);

  MenuItem addFoodToCategories(Long id, Set<Long> categoryIds);
}
