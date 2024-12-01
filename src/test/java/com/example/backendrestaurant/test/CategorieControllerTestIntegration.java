package com.example.backendrestaurant.test;

import com.example.backendrestaurant.controllers.CategorieController;
import com.example.backendrestaurant.models.Categorie;
import com.example.backendrestaurant.security.service.CatgeorieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategorieController.class)
public class CategorieControllerTestIntegration {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatgeorieService categorieService;

    @InjectMocks
    private CategorieController categorieController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categorieController).build();
    }

    @Test
    public void testAddCategorie() throws Exception {
        Categorie categorie = new Categorie();
        categorie.setName("Test Category");
        categorie.setDescription("Description of Test Category");

        when(categorieService.addCategorie(any(Categorie.class))).thenReturn(categorie);

        System.out.println("Running test: testAddCategorie");
        System.out.println("Request Data: {\"name\":\"Test Category\", \"description\":\"Description of Test Category\"}");

        mockMvc.perform(post("/api/categorie/addcategorie")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Category\", \"description\":\"Description of Test Category\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category"))
                .andExpect(jsonPath("$.description").value("Description of Test Category"));

        verify(categorieService, times(1)).addCategorie(any(Categorie.class));

        System.out.println("Response Data: {\"name\":\"Test Category\", \"description\":\"Description of Test Category\"}");
        System.out.println("testAddCategorie passed");
    }

    @Test
    public void testGetAllCategories() throws Exception {
        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(new Categorie("Category1", "Description1"));
        categorieList.add(new Categorie("Category2", "Description2"));

        when(categorieService.getAllCategories()).thenReturn(categorieList);

        System.out.println("Running test: testGetAllCategories");
        
        mockMvc.perform(get("/api/categorie/all-categories"))
                .andExpect(status().isOk());

        System.out.println("Response Data: " + categorieList.toString());
        System.out.println("testGetAllCategories passed");
    }

    @Test
    public void testGetCategoriesByName() throws Exception {
        String name = "Category1";
        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(new Categorie("Category1", "Description1"));

        when(categorieService.getCategoriesByName(name)).thenReturn(categorieList);

        System.out.println("Running test: testGetCategoriesByName");
        System.out.println("Request Data: Category name: " + name);

        mockMvc.perform(get("/api/categorie/all-categories/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Category1"));

        verify(categorieService, times(1)).getCategoriesByName(name);

        System.out.println("Response Data: " + categorieList.toString());
        System.out.println("testGetCategoriesByName passed");
    }

    @Test
    public void testUpdateCategorie() throws Exception {
        Long id = 1L;
        Categorie existingCategorie = new Categorie("Category1", "Description1");
        existingCategorie.setId(id);

        Categorie updatedCategorie = new Categorie("Updated Category", "Updated Description");
        updatedCategorie.setId(id);

        when(categorieService.updateCategorie(any(Categorie.class), eq(id))).thenReturn(updatedCategorie);

        System.out.println("Running test: testUpdateCategorie");
        System.out.println("Request Data: {\"name\":\"Updated Category\", \"description\":\"Updated Description\"}");

        mockMvc.perform(put("/api/categorie/update-categorie/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Category\", \"description\":\"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        verify(categorieService, times(1)).updateCategorie(any(Categorie.class), eq(id));

        System.out.println("Response Data: {\"name\":\"Updated Category\", \"description\":\"Updated Description\"}");
        System.out.println("testUpdateCategorie passed");
    }

    @Test
    public void testDeleteCategorie() throws Exception {
        Long id = 1L;

        doNothing().when(categorieService).deleteCategorieById(eq(id));

        System.out.println("Running test: testDeleteCategorie");
        System.out.println("Request Data: Delete category with ID: " + id);

        mockMvc.perform(delete("/api/categorie/delete-categorie/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Category Has been Deleted Successfully"));

        verify(categorieService, times(1)).deleteCategorieById(eq(id));

        System.out.println("Response Data: Category has been deleted successfully");
        System.out.println("testDeleteCategorie passed");
    }
}
