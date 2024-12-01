package com.example.backendrestaurant.controllers;

import com.example.backendrestaurant.models.MenuItem;
import com.example.backendrestaurant.security.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/menuitems")

public class MenuItemController {

  @Autowired
  private MenuItemService itemService;

  // http://localhost:8021/additem
  @PostMapping("/additem")
  public MenuItem addItem(@RequestBody MenuItem item){

    return itemService.addItem(item);
  }

  // http://localhost:8021/all-items
  @GetMapping("/all-items")
  public List<MenuItem> fetchItemList()
  {
    return itemService.itemsList();
  }

  // http://localhost:8021/update-item/{id}
  @PutMapping("/update-item/{id}")
  public MenuItem updateItem(@RequestBody MenuItem item, @PathVariable("id") Long id)
  {
    return itemService.updateItem(item,id);
  }

  //http://localhost:8021/delete-item/{id}
  @DeleteMapping("/delete-item/{id}")
  public String deleteItemById(@PathVariable("id") Long id)
  {
    itemService.deleteItemById(id);
    return ("Item Has been Deleted Successfully");
  }

  @PostMapping("/{foodId}/categories")
  public ResponseEntity<MenuItem> addFoodToCategories(@PathVariable Long foodId, @RequestBody Set<Long> categoryIds) {
    MenuItem food = itemService.addFoodToCategories(foodId, categoryIds);
    return ResponseEntity.ok(food);
  }
}
