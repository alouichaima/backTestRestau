package com.example.backendrestaurant.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.backendrestaurant.dto.WishlistDto;
import com.example.backendrestaurant.models.Wishlist;
import com.example.backendrestaurant.repository.WishlistRepository;
import com.example.backendrestaurant.security.service.WishlistService;


@Controller
@RequestMapping("/api/wishlist")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;
    
    @Autowired
    private WishlistRepository wishlistRepository;

    @PostMapping("/avis")
    public ResponseEntity<?> addMenuItemToWishlist(@RequestBody WishlistDto wishlistDto) {
        WishlistDto postedWishlistDto = wishlistService.addMenuItemToWishlist(wishlistDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postedWishlistDto);
    }
    
    @GetMapping("/meswishlist/{id}")
    public ResponseEntity<List<WishlistDto>> getAllByid(@PathVariable Long id) {
        List<Wishlist> wishlists = wishlistRepository.findMesWishlist(id);
        List<WishlistDto> wishlistDtos = wishlists.stream()
                .map(wishlist -> {
                    WishlistDto dto = new WishlistDto();
                    dto.setId(wishlist.getIdwishL());
                    dto.setMenuItem_id(wishlist.getMenuItem().getId()); 
                    dto.setUserId(wishlist.getUser().getId());
                    // Mettre à jour les autres propriétés du DTO à partir de l'objet Wishlist
                    dto.setName(wishlist.getMenuItem().getName());
                    dto.setDescription(wishlist.getMenuItem().getDescription());
                    dto.setPrice((int) wishlist.getMenuItem().getPrice());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(wishlistDtos);
    }



}
