package edu.uoc.epcsd.productcatalog;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uoc.epcsd.productcatalog.controller.CategoryController;
import edu.uoc.epcsd.productcatalog.domain.Category;
import edu.uoc.epcsd.productcatalog.service.ProductCatalogService;

@SpringBootTest
@WebMvcTest(CategoryController.class)
class CategoryControllerUnitTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductCatalogService productCatalogService;

    @Test
    public void givenCategoriesWhenGetCategoriesThenReturnCategories() throws Exception {
        // Configuració
        List<Category> expectedCategories = Arrays.asList(new Category(), new Category());
        Mockito.when(productCatalogService.getCategories()).thenReturn(expectedCategories);

        // Acció i Verificació
        mvc.perform(MockMvcRequestBuilders.get("/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedCategories)));
    }
}