package com.example.backendrestaurant.dto;

public class WishlistDto {
	private long userId;
	private long menuItem_id;
	private long id;
	private String name;
	private String description;
	
	//private byte[]Image;
	private int price;
	
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getMenuItem_id() {
		return menuItem_id;
	}

	public void setMenuItem_id(long menuItem_id) {
		this.menuItem_id = menuItem_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
