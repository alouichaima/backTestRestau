package com.example.backendrestaurant.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backendrestaurant.models.ListeChef;
import com.example.backendrestaurant.repository.ListeChefRepository;
import com.example.backendrestaurant.security.service.ListeChefService;

@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/chef")
@RestController
public class ListeChefController {
	
	@Autowired
	private ListeChefService listeChefService;
	
	@Autowired

	private ListeChefRepository listeChefRepository;


	
	@GetMapping(path="/all")
	public List<ListeChef> getAllChef() {
		return listeChefService.getAllChef();
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ListeChef>  findChefById (@PathVariable Long id) {
		ListeChef listeChef = listeChefService.findChefById(id);
		if (listeChef==null) {
			return new ResponseEntity<ListeChef>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<ListeChef>(listeChef, HttpStatus.OK);
		}
	}
	
	@PostMapping("/addChef")
	public ListeChef createChef(@RequestBody ListeChef listeChef) {
		return listeChefService.createChef(listeChef);
	}
	

	@PutMapping("/updateChef/{id}")
	public ListeChef updateChef(@RequestBody ListeChef listeChef) {
		return listeChefService.updateChef(listeChef);
				
	}
	@DeleteMapping(path= "/{id}") 
	public void deleteChef(@PathVariable Long id) {
		 listeChefService.deleteChef(id);
	}
	
	
}
