package com.example.backendrestaurant.models;


import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class ListeChef implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id ;
	private String NomPrenom;
	private String Description ;
	private String image;
	private String facebook;
	private String instagram; 

	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getNomPrenom() {
		return NomPrenom;
	}
	public void setNomPrenom(String nomPrenom) {
		NomPrenom = nomPrenom;
	}


	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public ListeChef() {
		super();
	}

	@Override
	public String toString() {
		return "ListeChef [Id=" + Id + ", NomPrenom=" + NomPrenom + ",  Description=" + Description
				+ ", image=" + image + ", facebook=" + facebook + ", instagram=" + instagram + "]";
	}}
	
	
	

