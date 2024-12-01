package com.example.backendrestaurant.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendrestaurant.dto.WishlistDto;
import com.example.backendrestaurant.models.MenuItem;
import com.example.backendrestaurant.models.User;
import com.example.backendrestaurant.models.Wishlist;
import com.example.backendrestaurant.repository.MenuItemRepository;
import com.example.backendrestaurant.repository.UserRepository;
import com.example.backendrestaurant.repository.WishlistRepository;

@Service
public class WishlistserviceImp implements WishlistService {
    @Autowired
	private UserRepository userRepository;
    @Autowired
	private MenuItemRepository menuItemRepository;
    @Autowired
	private WishlistRepository wishlistRepository;
	
	public WishlistDto addMenuItemToWishlist(WishlistDto wishlistDto) {
		Optional<MenuItem> optionalMenuItem=menuItemRepository.findById(wishlistDto.getMenuItem_id());
		Optional<User>optionalUser=userRepository.findById(wishlistDto.getUserId());
		
		if(optionalMenuItem.isPresent() && optionalUser.isPresent()) {
			Wishlist wishlist = new Wishlist();
			wishlist.setMenuItem(optionalMenuItem.get());
			wishlist.setUser(optionalUser.get());
			return wishlistRepository.save(wishlist).getWishlistDto();
		}
		return null;
	}

}



