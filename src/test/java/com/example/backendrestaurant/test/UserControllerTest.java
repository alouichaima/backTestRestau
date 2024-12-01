package com.example.backendrestaurant.test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.example.backendrestaurant.controllers.UserController;
import com.example.backendrestaurant.models.User;
import com.example.backendrestaurant.payload.request.SignupRequest;
import com.example.backendrestaurant.payload.response.MessageResponse;
import com.example.backendrestaurant.repository.UserRepository;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOneUser() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setUsername("testuser");
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = userController.GetOneUser(userId);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        System.out.println("Test GetOneUser passed. Result: " + result.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testModifierUser() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldusername");
        existingUser.setEmail("oldemail@example.com");

        SignupRequest request = new SignupRequest();
        request.setUsername("newusername");
        request.setEmail("newemail@example.com");
        request.setPassword("newpassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedpassword");

        // Act
        ResponseEntity<?> response = userController.ModifierUser(userId, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((MessageResponse) response.getBody()).getMessage()).isEqualTo("Utilisateur Modifier avec succée!");
        System.out.println("Test ModifierUser passed. Response: " + ((MessageResponse) response.getBody()).getMessage());
        assertThat(existingUser.getUsername()).isEqualTo("newusername");
        System.out.println("Updated username: " + existingUser.getUsername());
        assertThat(existingUser.getEmail()).isEqualTo("newemail@example.com");
        System.out.println("Updated email: " + existingUser.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testModifierUserPhoto() throws Exception {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        MultipartFile mockFile = mock(MultipartFile.class);
        String fileName = "testphoto.jpg";
        byte[] fileBytes = "testimagecontent".getBytes();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(mockFile.getOriginalFilename()).thenReturn(fileName);
        when(mockFile.getBytes()).thenReturn(fileBytes);

        // Act
        ResponseEntity<?> response = userController.ModifierUserPhoto(userId, mockFile);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((MessageResponse) response.getBody()).getMessage()).isEqualTo("User Photo Modifier successfully! " + fileName);
        System.out.println("Test ModifierUserPhoto passed. Response: " + ((MessageResponse) response.getBody()).getMessage());
        assertThat(existingUser.getPhoto()).isEqualTo(Base64.getEncoder().encodeToString(fileBytes));
        System.out.println("Updated photo: " + existingUser.getPhoto());
        verify(userRepository, times(1)).save(existingUser);
    }
}
