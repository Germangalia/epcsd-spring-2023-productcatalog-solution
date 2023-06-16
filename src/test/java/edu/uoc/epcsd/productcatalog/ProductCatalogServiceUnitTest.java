package edu.uoc.epcsd.productcatalog;

import edu.uoc.epcsd.productcatalog.domain.Product;
import edu.uoc.epcsd.productcatalog.domain.repository.ProductRepository;
import edu.uoc.epcsd.productcatalog.domain.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductCatalogServiceUnitTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Test
    public void findProductByIdWithValidId() {

        // Arrange
        Long validId = 1L;
        Product mockProduct = Product.builder()
                .id(validId)
                .name("Test Product")
                .categoryId(2L)
                .build();
        
        Mockito.when(productRepository.findProductById(validId)).thenReturn(Optional.of(mockProduct));

        // Act
        Optional<Product> productOptional = productServiceImpl.findProductById(validId);

        // Assert
        assertThat(productOptional.isPresent()).isTrue();
        assertThat(productOptional.get()).usingRecursiveComparison().isEqualTo(mockProduct);
    }

    @Test
    public void findProductByIdWithInvalidId() {

        // Arrange
        Long nonExistentId = 999L;
        Mockito.when(productRepository.findProductById(nonExistentId)).thenReturn(Optional.empty());

        // Act
        Optional<Product> result = productServiceImpl.findProductById(nonExistentId);

        // Assert
        assertThat(result).isEmpty();
    }
}
