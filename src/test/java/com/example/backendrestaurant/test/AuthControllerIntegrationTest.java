package com.example.backendrestaurant.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import com.example.backendrestaurant.models.ERole;
import com.example.backendrestaurant.models.Role;
import com.example.backendrestaurant.models.User;
import com.example.backendrestaurant.payload.request.SignupRequest;
import com.example.backendrestaurant.repository.RoleRepository;
import com.example.backendrestaurant.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        // Vérifiez si les rôles existent déjà pour éviter les doublons
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.saveAll(List.of(
                new Role(ERole.CLIENT),
                new Role(ERole.ADMIN)
            ));
        }
    }

    @AfterEach
    @Rollback
    void tearDown() {
        // Nettoyer la base de données après chaque test
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        System.out.println("Test: Register User Success");

        // Créer une requête de test valide
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setEmail("testuser@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setRole(Collections.singleton("client")); // Ensure role is valid

        System.out.println("Request Data: " + asJsonString(signupRequest));

        // Assurez-vous que le chemin correspond à l'API définie dans le contrôleur
        mockMvc.perform(post("/api/auth/signup")  // Correct URL
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signupRequest)))
                .andExpect(status().isOk());  // Attendez une réponse 200 OK

        System.out.println("POST request sent to /api/auth/signup");

        // Vérifier que l'utilisateur est créé dans la base de données
        Optional<User> user = userRepository.findByUsername("testuser");
        assertTrue(user.isPresent());
        assertEquals("testuser@example.com", user.get().getEmail());
        assertTrue(encoder.matches("password123", user.get().getPassword()));

        System.out.println("User created: " + user.get().getUsername());
        System.out.println("Email: " + user.get().getEmail());
        System.out.println("Password matches: " + encoder.matches("password123", user.get().getPassword()));

        System.out.println("Test completed: User successfully registered.");
    }


    @Test
    public void testRegisterUserUsernameTaken() throws Exception {
        System.out.println("Test: Register User with Existing Username");

        // Ajouter un utilisateur existant
        User existingUser = new User("existinguser", "existing@example.com", encoder.encode("password123"));
        userRepository.save(existingUser);
        System.out.println("Existing user created with username: " + existingUser.getUsername());

        // Créer une requête avec le même nom d'utilisateur
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("existinguser");
        signupRequest.setEmail("newuser@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setRole(Collections.singleton("client"));

        System.out.println("Signup Request Data: " + asJsonString(signupRequest));

        // Essayer de créer un utilisateur avec un nom d'utilisateur existant
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signupRequest)));
        System.out.println("POST request sent to /signup with existing username");

        // Optionally, verify if the user count is still the same
        List<User> users = userRepository.findAll();
        System.out.println("Total users in DB: " + users.size());
        System.out.println("Test completed: Username already taken, user not created.");
    }


    @Test
    public void testRegisterUserEmailTaken() throws Exception {
        System.out.println("Test: Register User with Existing Email");

        // Ajouter un utilisateur existant
        User existingUser = new User("existinguser", "existing@example.com", encoder.encode("password123"));
        userRepository.save(existingUser);
        System.out.println("Existing user created with email: " + existingUser.getEmail());

        // Créer une requête avec le même email
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setEmail("existing@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setRole(Collections.singleton("client"));

        System.out.println("Signup Request Data: " + asJsonString(signupRequest));

        // Créer un jeton JWT (si nécessaire pour le test)
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGFpbWFhbG91aSIsImlhdCI6MTcwOTQyMzU3OX0.PSni5Bvm6aWlz-31F-fucmUPF5bA3AhOJPb9ngbgb-I"; // Jeton JWT exemple

        // Essayer de créer un utilisateur avec un email existant
        mockMvc.perform(post("/signup")
                .header("Authorization", "Bearer " + jwtToken)  // Ajouter le jeton JWT dans l'en-tête
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signupRequest)));

        System.out.println("POST request sent to /signup with existing email");

        // Optionally, verify if the user count is still the same
        List<User> users = userRepository.findAll();
        System.out.println("Total users in DB: " + users.size());
        System.out.println("Test completed: Email already taken, user not created.");
    }




    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
