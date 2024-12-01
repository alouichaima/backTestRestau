package com.example.backendrestaurant.controllers;


import java.util.List;

import com.example.backendrestaurant.dto.CommandeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backendrestaurant.repository.CommandeRepository;
import com.example.backendrestaurant.security.service.CommandeService;
import com.example.backendrestaurant.models.Commande;


@RestController
@RequestMapping("/commande")
public class CommandeController {

	@Autowired
	private CommandeService commandeService;
    
	@Autowired
	private CommandeRepository commandeRepository;



	@PostMapping("/passer")
	public ResponseEntity<?> passerCommande(@RequestBody CommandeDto commandeDto) {
		try {
			// Créer une commande en utilisant les détails de la commande reçus
			Commande nouvelleCommande = commandeService.passercommande(commandeDto);

			// Si la commande est créée avec succès, renvoyer une réponse HTTP 201 (CREATED) avec la commande créée
			return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleCommande);
		} catch (Exception e) {
			// Si une erreur se produit pendant le traitement de la commande, renvoyer une réponse HTTP 500 (INTERNAL SERVER ERROR)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors du traitement de la commande.");
		}
	}

	@GetMapping("/mescommandes/{id}")
	public List<Commande> getAllByid(@PathVariable Long id) {
		return commandeRepository.findMesCommandes(id);
	}
	@GetMapping("/admin/toutescommandes")

	public ResponseEntity<List<Commande>> getAllCommandes() {
		List<Commande> commandes = commandeRepository.findAll();
		if (commandes.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(commandes);
		}
	}

	@PutMapping("/admin/accepter/{id}")
	public ResponseEntity<?> accepterCommande(@PathVariable Long id) {
		commandeService.accepterCommande(id);
		return ResponseEntity.ok().build(); // Renvoie une réponse 200 OK avec un corps vide
	}

	@PutMapping("/admin/refuser/{id}")
	public ResponseEntity<?> refuserCommande(@PathVariable Long id) {
		commandeService.refuserCommande(id);
		return ResponseEntity.ok().build(); // Renvoie une réponse 200 OK avec un corps vide
	}}