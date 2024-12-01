package com.example.backendrestaurant.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.backendrestaurant.controllers.AuthController;
import com.example.backendrestaurant.models.ERole;
import com.example.backendrestaurant.models.Role;
import com.example.backendrestaurant.models.User;
import com.example.backendrestaurant.payload.request.LoginRequest;
import com.example.backendrestaurant.payload.request.SignupRequest;
import com.example.backendrestaurant.payload.response.JwtResponse;
import com.example.backendrestaurant.payload.response.MessageResponse;
import com.example.backendrestaurant.repository.RoleRepository;
import com.example.backendrestaurant.repository.UserRepository;
import com.example.backendrestaurant.security.jwt.JwtUtils;
import com.example.backendrestaurant.security.service.UserDetailsImpl;

import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private LoginRequest loginRequest1;
    private SignupRequest signupRequest;

    @BeforeEach
    public void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("alouich");
        loginRequest.setPassword("chaima123");

        Set<String> roles = new HashSet<>();
        roles.add("client");
        signupRequest = new SignupRequest("newuser", "newuser@email.com", roles, "password123");
    }

    @Test
    public void testAuthenticateUser_Success() {
        // Mock Authentication object
        Authentication authentication = mock(Authentication.class);

        // Mock UserDetailsImpl to simulate a logged-in user
        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,
                "alouich",
                "chaima@gmail.com",
                "password123",
                List.of()
        );
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mock AuthenticationManager to return the mocked Authentication object
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenAnswer(invocation -> {
                UsernamePasswordAuthenticationToken token = invocation.getArgument(0);
                assertEquals("alouich", token.getPrincipal());
                assertEquals("chaima123", token.getCredentials());
                return authentication;
            });

        // Mock JWT token generation
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt_token");

        // Prepare LoginRequest
        LoginRequest loginRequest1 = new LoginRequest();
        loginRequest1.setUsername("alouich");
        loginRequest1.setPassword("chaima123");
        System.out.println("LoginRequest: " + loginRequest1.getUsername() + ", " + loginRequest1.getPassword());

        // Call the method under test
        ResponseEntity<?> response = authController.authenticateUser(loginRequest1);

        // Debug response
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtResponse actualResponse = (JwtResponse) response.getBody();
        assertNotNull(actualResponse);
        assertEquals("jwt_token", actualResponse.getAccessToken());
        assertEquals("alouich", actualResponse.getUsername());
        assertEquals("chaima@gmail.com", actualResponse.getEmail());

        // Verify interactions
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateJwtToken(authentication);
    }




    @Test
    public void testAuthenticateUser_Failure() {
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Authentication failed"));
        loginRequest1 = new LoginRequest();
        loginRequest1.setUsername("testuser");
        loginRequest1.setPassword("password123");
        System.out.println(loginRequest1.getUsername());
        ResponseEntity<?> response = authController.authenticateUser(loginRequest1);
        System.out.println(response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals("Error: Authentication failed", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    public void testRegisterUser_Success() {
        // Initialize signupRequest with valid values
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setEmail("testuser@email.com");
        signupRequest.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        signupRequest.setRole(roles);

        // Mock role repository to return the admin role
        Role adminRole = new Role(ERole.ADMIN);
        when(roleRepository.findByName(ERole.ADMIN)).thenReturn(Optional.of(adminRole));

        // Mock user repository to indicate no user exists with the same username or email
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("testuser@email.com")).thenReturn(false);

        // Mock password encoding
        when(encoder.encode("password123")).thenReturn("encoded_password");

        // Mock user creation
        User newUser = new User(signupRequest.getUsername(), signupRequest.getEmail(), "encoded_password");
        newUser.setRoles(Set.of(adminRole));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser);

        // Call the registerUser method
        ResponseEntity<?> response = authController.registerUser(signupRequest);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check the response body content
        MessageResponse actualResponse = (MessageResponse) response.getBody();
        assertNotNull(actualResponse);
        assertEquals("User registered successfully!", actualResponse.getMessage());

        // Verify that the correct methods were called
        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("testuser@email.com");
        verify(roleRepository).findByName(ERole.ADMIN);
        verify(userRepository).save(Mockito.any(User.class));
    }


    @Test
    public void testRegisterUser_UsernameTaken() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(signupRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals("Error: Username is already taken!", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    public void testRegisterUser_EmailTaken() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(signupRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals("Error: Email is already in use!", ((MessageResponse) response.getBody()).getMessage());
    }
}
