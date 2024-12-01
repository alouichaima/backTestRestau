package com.example.backendrestaurant.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class MenuItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String description;
  private Long price;

  @ManyToMany(mappedBy = "foods")
  private Set<Categorie> categories = new HashSet<>();
  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;
  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public long getPrice() {
    return price;
  }


  public void setId(Long id) {
    this.id = id;
  }

  public Set<Categorie> getCategories() {
    return categories;
  }

  public void setCategories(Set<Categorie> categories) {
    this.categories = categories;
  }

  public Menu getMenu() {
    return menu;
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
  }
}

