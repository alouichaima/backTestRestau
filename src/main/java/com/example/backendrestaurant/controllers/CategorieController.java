package com.example.backendrestaurant.controllers;

import com.example.backendrestaurant.security.service.CatgeorieService;
import com.example.backendrestaurant.models.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/categorie")

public class CategorieController {

  @Autowired
  private CatgeorieService categorieService;

  // http://localhost:5000/addcategorie
  @PostMapping("/addcategorie")
  public Categorie addCategorie(@RequestBody Categorie categorie){

    return categorieService.addCategorie(categorie);
  }

  // http://localhost:5000/all-categories
  @GetMapping("/all-categories")
  public List<Categorie> fetchCategorieList()

  {
    return categorieService.CategorieList();
  }

  @GetMapping("/all-categories/{name}")
  public ResponseEntity<List<Categorie>> getCategoriesByName(@PathVariable String name) {
      List<Categorie> categorieList = categorieService.getCategoriesByName(name);
      if (categorieList == null || categorieList.isEmpty()) {
          return ResponseEntity.notFound().build(); // Return 404 if null or empty
      }
      return ResponseEntity.ok(categorieList); // Return 200 OK if categories found
  }





  // http://localhost:5000/update-categorie/{id}
  @PutMapping("/update-categorie/{id}")
  public Categorie updateCategorie(@RequestBody Categorie categorie,@PathVariable("id") Long id)
  {
    return categorieService.updateCategorie(categorie,id);
  }

  //http://localhost:5000/delete-categorie/{id}
  @DeleteMapping("/delete-categorie/{id}")
  public String deleteCategorieById(@PathVariable("id") Long id)
  {
    categorieService.deleteCategorieById(id);
    return ("Category Has been Deleted Successfully");
  }


}
