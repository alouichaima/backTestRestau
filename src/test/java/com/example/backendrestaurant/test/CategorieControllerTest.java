package com.example.backendrestaurant.test;

import com.example.backendrestaurant.controllers.CategorieController;
import com.example.backendrestaurant.models.Categorie;
import com.example.backendrestaurant.security.service.CatgeorieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategorieControllerTest {

    @InjectMocks
    private CategorieController categorieController;

    @Mock
    private CatgeorieService categorieService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categorieController).build();
    }

    @Test
    public void testAddCategorie() throws Exception {
        Categorie categorie = new Categorie();
        categorie.setId(1L);
        categorie.setName("Food");
        categorie.setDescription("Various types of food");

        when(categorieService.addCategorie(any(Categorie.class))).thenReturn(categorie);

        mockMvc.perform(post("/api/categorie/addcategorie")
                .contentType("application/json")
                .content("{\"name\": \"Food\", \"description\": \"Various types of food\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Food"))
                .andExpect(jsonPath("$.description").value("Various types of food"));
    }

    @Test
    public void testGetCategories() throws Exception {
        Categorie categorie1 = new Categorie();
        categorie1.setId(1L);
        categorie1.setName("Food");

        Categorie categorie2 = new Categorie();
        categorie2.setId(2L);
        categorie2.setName("Beverages");

        when(categorieService.CategorieList()).thenReturn(Arrays.asList(categorie1, categorie2));

        mockMvc.perform(get("/api/categorie/all-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Food"))
                .andExpect(jsonPath("$[1].name").value("Beverages"));
    }

    @Test
    public void testGetCategoriesByName() throws Exception {
        Categorie categorie = new Categorie();
        categorie.setId(1L);
        categorie.setName("Food");

        when(categorieService.getCategoriesByName("Food")).thenReturn(Arrays.asList(categorie));

        mockMvc.perform(get("/api/categorie/all-categories/Food"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Food"));
    }

    @Test
    public void testGetCategoriesByNameNotFound() throws Exception {
        when(categorieService.getCategoriesByName("NonExistent")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/categorie/all-categories/NonExistent"))
                .andExpect(status().isNotFound());  // This will now return 404
    }


    @Test
    public void testUpdateCategorie() throws Exception {
        Categorie existingCategorie = new Categorie();
        existingCategorie.setId(1L);
        existingCategorie.setName("Food");

        Categorie updatedCategorie = new Categorie();
        updatedCategorie.setId(1L);
        updatedCategorie.setName("Snacks");

        when(categorieService.updateCategorie(any(Categorie.class), eq(1L))).thenReturn(updatedCategorie);

        mockMvc.perform(put("/api/categorie/update-categorie/1")
                .contentType("application/json")
                .content("{\"name\": \"Snacks\", \"description\": \"Snacks and more\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Snacks"));
    }

    @Test
    public void testDeleteCategorie() throws Exception {
        doNothing().when(categorieService).deleteCategorieById(1L);

        mockMvc.perform(delete("/api/categorie/delete-categorie/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Category Has been Deleted Successfully"));
    }
}
