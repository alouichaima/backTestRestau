package com.example.backendrestaurant.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.backendrestaurant.dto.WishlistDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wishlist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idwishL;
	
	@ManyToOne(fetch=FetchType.LAZY	,optional = false)
	@JoinColumn(name="menuItem_id",nullable = false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private MenuItem menuItem;
	

	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="user_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	public WishlistDto getWishlistDto() {
		WishlistDto wishlistDto=new WishlistDto();
		
		wishlistDto.setId(idwishL);
		wishlistDto.setMenuItem_id(menuItem.getId());
		//wishlistDto.setImage(menuItem.getImage());
		wishlistDto.setName(menuItem.getName());
		wishlistDto.setDescription(menuItem.getDescription());
		//wishlistDto.setPrice(menuItem.getPrice());
		wishlistDto.setUserId(user.getId());
		
		return wishlistDto;
	}


	public Long getIdwishL() {
		return idwishL;
	}


	public void setIdwishL(Long idwishL) {
		this.idwishL = idwishL;
	}


	public MenuItem getMenuItem() {
		return menuItem;
	}


	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	

}
