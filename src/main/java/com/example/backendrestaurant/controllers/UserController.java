package com.example.backendrestaurant.controllers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backendrestaurant.models.User;
import com.example.backendrestaurant.payload.request.SignupRequest;
import com.example.backendrestaurant.payload.response.MessageResponse;
import com.example.backendrestaurant.repository.UserRepository;

import jakarta.persistence.Entity;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/utilisateur")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping (value="/GetOneUser/{id}")
	@ResponseBody
	public User GetOneUser(@PathVariable Long id) {
		
		return userRepository.findById(id).get();
	}


	
	@PutMapping(value="/modifier/{id}")

    public ResponseEntity<?> ModifierUser(@PathVariable("id")Long id ,

       @Valid @RequestBody SignupRequest registerRequest ) {


              User user1 = userRepository.findById(id).get();

              user1.setUsername(registerRequest.getUsername());


              user1.setEmail(registerRequest.getEmail());

              encoder.encode(registerRequest.getPassword());

              
              user1.setRoles(user1.getRoles());


              userRepository.save(user1);

              return ResponseEntity.ok(new MessageResponse("Utilisateur Modifier avec succée!"));

    }
	
	@PutMapping(value = "/ModifierPhoto/{iduser}")
	public ResponseEntity<?> ModifierUserPhoto(@PathVariable("iduser") Long id,
			@RequestParam("file") MultipartFile file) {

		User user = new User();
		user = userRepository.findById(id).get();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		String message = "";
		if (fileName.contains("..")) {
			System.out.println("not a a valid file");
		}
		try {
			user.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
			message = "User Photo Modifier successfully! " + file.getOriginalFilename();
			userRepository.save(user);
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			message = "Could not upload the Photo: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}

	}
	

}
