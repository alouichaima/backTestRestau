package com.example.backendrestaurant.security.service;

import java.util.List;

import com.example.backendrestaurant.dto.CommandeDto;
import com.example.backendrestaurant.models.Commande;

public interface CommandeService {
	
	public void passercommande(
			Long iduser,
			Long idmenuItem);


    List<Commande> getAllCommandes();

    List<CommandeDto> getCommandes();

    void accepterCommande(Long id);

	void refuserCommande(Long id);

	Commande passercommande(CommandeDto commandeDto);
}
