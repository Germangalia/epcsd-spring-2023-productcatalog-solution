package edu.uoc.epcsd.productcatalog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.uoc.epcsd.productcatalog.domain.Category;
import edu.uoc.epcsd.productcatalog.domain.Item;
import edu.uoc.epcsd.productcatalog.domain.ItemStatus;
import edu.uoc.epcsd.productcatalog.domain.Product;
import edu.uoc.epcsd.productcatalog.domain.repository.CategoryRepository;
import edu.uoc.epcsd.productcatalog.domain.repository.ItemRepository;
import edu.uoc.epcsd.productcatalog.domain.repository.ProductRepository;
import edu.uoc.epcsd.productcatalog.domain.service.CategoryServiceImpl;
import edu.uoc.epcsd.productcatalog.domain.service.ItemServiceImpl;
import edu.uoc.epcsd.productcatalog.domain.service.ProductServiceImpl;

import java.util.Optional;

@SpringBootTest
class ItemUnitTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Test
    public void itemStatusIsNonOperational() {
        
        Category category = Category.builder()
                .id(1L)
                .name("Electronics")
                .build();
        categoryServiceImpl.createCategory(category);

        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .categoryId(category.getId())
                .build();
        productServiceImpl.createProduct(product);

    Item item = new Item();
    item.setStatus(ItemStatus.NON_OPERATIONAL);
    
    Mockito.when(itemServiceImpl.findBySerialNumber("123456")).thenReturn(Optional.of(item));

    String cerateItemString = itemServiceImpl.createItem(product.getId(), "123456");
    Optional<Item> itemOptional = itemServiceImpl.findBySerialNumber("123456");
    Item retrievedItem = itemOptional.get();

    // Assert
    assertThat(retrievedItem.getStatus()).isEqualTo(ItemStatus.NON_OPERATIONAL);
    }
}
