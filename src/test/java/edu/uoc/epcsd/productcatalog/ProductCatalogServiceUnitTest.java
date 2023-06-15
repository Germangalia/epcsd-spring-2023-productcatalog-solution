package edu.uoc.epcsd.productcatalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import edu.uoc.epcsd.productcatalog.domain.Product;
import edu.uoc.epcsd.productcatalog.domain.repository.ProductRepository;
import edu.uoc.epcsd.productcatalog.exception.ProductNotFoundException;
import edu.uoc.epcsd.productcatalog.service.ProductCatalogService;

@SpringBootTest
class ProductCatalogServiceUnitTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductCatalogService productCatalogService;

    @Test
    public void givenValidIdWhenFindProductByIdThenReturnProduct() {
        // Configuració
        Product expectedProduct = new Product();
        Mockito.when(productRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(expectedProduct));

        // Acció
        Product product = productCatalogService.findProductById(1L);

        // Verificació
        assertThat(product).isEqualTo(expectedProduct);
    }

    @Test
    public void givenInvalidIdWhenFindProductByIdThenThrowException() {
        // Configuració
        Mockito.when(productRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        // Acció
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productCatalogService.findProductById(1L);
        });

        // Verificació - Esperem que es llanci ProductNotFoundException
        assertThat(exception.getMessage()).contains("Product not found");
    }
}
