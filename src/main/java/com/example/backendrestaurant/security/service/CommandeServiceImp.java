package com.example.backendrestaurant.security.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.backendrestaurant.dto.CommandeDto;
import com.example.backendrestaurant.models.CommandeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendrestaurant.models.Commande;
import com.example.backendrestaurant.models.MenuItem;
import com.example.backendrestaurant.models.User;
import com.example.backendrestaurant.repository.CommandeRepository;
import com.example.backendrestaurant.repository.MenuItemRepository;
import com.example.backendrestaurant.repository.UserRepository;



@Service
public class CommandeServiceImp implements CommandeService {

	@Autowired
	private CommandeRepository commandeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	public void passercommande(Long iduser, Long idmenuItem) {
		User user = userRepository.findById(iduser).get();
		MenuItem menuItem = menuItemRepository.findById(idmenuItem).get();
		Commande cmd = new Commande();

		cmd.setUser(user);
		cmd.setMenuItem(menuItem);
		cmd.setDatedecommande(new Date());
		commandeRepository.save(cmd);
	}

	@Override
	public List<Commande> getAllCommandes() {
		return null;
	}

	@Override
	public List<CommandeDto> getCommandes() {
		return commandeRepository.findAll().stream().map(Commande::getCommandeDto).collect(Collectors.toList());
	}


	@Override
	public void accepterCommande(Long id) {
		Optional<Commande> optionalCommande = commandeRepository.findById(id);
		if (optionalCommande.isPresent()) {
			Commande commande = optionalCommande.get();
			commande.setCommandeStatus(CommandeStatus.APPROVED);
			commandeRepository.save(commande);
		}
	}

	@Override
	public void refuserCommande(Long id) {
		Optional<Commande> optionalCommande = commandeRepository.findById(id);
		if (optionalCommande.isPresent()) {
			Commande commande = optionalCommande.get();
			commande.setCommandeStatus(CommandeStatus.DISAPPROVE);
			commandeRepository.save(commande);
		}
	}
	@Override
	public Commande passercommande(CommandeDto commandeDto) {
		Long userId = commandeDto.getUserId();
		Long menuItemId = commandeDto.getMenuItemId();

		if (userId != null && menuItemId != null) {
			Optional<User> optionalUser = userRepository.findById(userId);
			Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);

			if (optionalUser.isPresent() && optionalMenuItem.isPresent()) {
				User user = optionalUser.get();
				MenuItem menuItem = optionalMenuItem.get();

				Commande commande = new Commande();
				commande.setUser(user);
				commande.setMenuItem(menuItem);
				commande.setDatedecommande(new Date());
				commande.setCommandeStatus(CommandeStatus.PENDING);

				return commandeRepository.save(commande);
			}
		}

		return null;
	}


}
