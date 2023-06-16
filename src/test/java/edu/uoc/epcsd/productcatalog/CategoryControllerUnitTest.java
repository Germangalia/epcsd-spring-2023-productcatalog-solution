package edu.uoc.epcsd.productcatalog;

import edu.uoc.epcsd.productcatalog.domain.Category;
import edu.uoc.epcsd.productcatalog.domain.service.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl categoryServiceImpl;

    private List<Category> categoryList;

    @BeforeEach
    public void setup() {
        Category category1 = Category.builder()
            .id(1L)
            .name("Category1")
            .build();
        Category category2 = Category.builder()
            .id(2L)
            .name("Category2")
            .build();
        categoryList = Arrays.asList(category1, category2);
    }

    @Test
    public void getAllCategoriesTest() throws Exception {
        when(categoryServiceImpl.findAllCategories()).thenReturn(categoryList);

        mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Category1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Category2")));
    }
}
