package com.example.backendrestaurant.security.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendrestaurant.models.ListeChef;
import com.example.backendrestaurant.repository.ListeChefRepository;



@Service
public class ListeChefServiceImp implements ListeChefService {
	
	@Autowired
	private ListeChefRepository listeChefRepository;
	
	@Override
	public List<ListeChef> getAllChef() {
		return listeChefRepository.findAll();
	}
	
	@Override
	public ListeChef findChefById(Long id) {
		Optional<ListeChef> coaOptional = listeChefRepository.findById(id);
		if (coaOptional.isEmpty()) {
			return null;
		} else {
			return coaOptional.get();
		}
	}

	@Override
	public ListeChef createChef(ListeChef listeChef) {
		return listeChefRepository.save(listeChef);

	}


	@Override
	public ListeChef updateChef(ListeChef listeChef) {
		Optional<ListeChef> actOptional = listeChefRepository.findById(listeChef.getId());

		if (actOptional.isEmpty()) {
			return null;
		} else {
			return listeChefRepository.save(listeChef);
		}

	}

	@Override
	public void deleteChef(long id) {
		listeChefRepository.deleteById(id);
		
	}
	

	
}
