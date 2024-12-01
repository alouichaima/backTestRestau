package com.example.backendrestaurant.security.service;

import com.example.backendrestaurant.models.Categorie;
import com.example.backendrestaurant.models.MenuItem;
import com.example.backendrestaurant.repository.CategorieRepository;
import com.example.backendrestaurant.repository.MenuItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class MenuItemServiceImp implements MenuItemService{

  @Autowired
  private MenuItemRepository menuItemRepository;
  @Autowired
  private CategorieRepository categorieRepository;
  @Override
  public MenuItem addItem(MenuItem item) {
    if(item.getName()==null || item.getName().isEmpty()){
      throw new IllegalArgumentException("Item Name cannot be empty !!");
    }
    try{
      return menuItemRepository.save(item);
    }catch (Exception e){
      throw new RuntimeException("Failed to add the item",e);
    }  }

  @Override
  public List<MenuItem> itemsList() {
    return (List<MenuItem>) menuItemRepository.findAll();
  }

  @Override
  public MenuItem updateItem(MenuItem item, Long id) throws RuntimeException{
    MenuItem existingItem = menuItemRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));

    if (Objects.nonNull(item.getName()) && !item.getName().isEmpty()) {
      existingItem.setName(item.getName());
    }

      existingItem.setPrice(item.getPrice());

      if (Objects.nonNull(item.getDescription()) && !item.getDescription().isEmpty()) {
      existingItem.setDescription(item.getDescription());
    }


    return menuItemRepository.save(existingItem);
  }


  @Override
  public void deleteItemById(Long id) {
    menuItemRepository.deleteById(id);
  }

  @Transactional
  public MenuItem addFoodToCategories(Long itemId, Set<Long> categoryIds) {
    MenuItem food = menuItemRepository.findById(itemId)
      .orElseThrow(() -> new RuntimeException("Food not found with id: " + itemId));

    // Clear existing categories to avoid duplicates
    food.getCategories().clear();

    for (Long categoryId : categoryIds) {
      Categorie category = categorieRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
      food.getCategories().add(category);
      category.getFoods().add(food);
    }
    return menuItemRepository.save(food);
  }
  }

