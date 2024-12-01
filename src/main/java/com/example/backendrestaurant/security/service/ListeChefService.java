package com.example.backendrestaurant.security.service;

import java.util.List;

import com.example.backendrestaurant.models.ListeChef;

public interface ListeChefService {
	
	public List<ListeChef> getAllChef();
	public ListeChef findChefById (Long id);
	public ListeChef createChef( ListeChef listeChef);
	public ListeChef updateChef( ListeChef listeChef);
	public void deleteChef( long id);
}
