package ec.edu.espe.arquitectura.inventario.test;

import ec.edu.espe.arquitectura.inventario.model.Product;
import ec.edu.espe.arquitectura.inventario.model.ProductCategory;
import ec.edu.espe.arquitectura.inventario.repository.ProductRepository;
import ec.edu.espe.arquitectura.inventario.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductCategory category;
    private List<ProductCategory> productCategories;
    private Product product;
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        this.productService = new ProductService(this.productRepository);
        this.category = ProductCategory.builder()
                .categoryName("Color")
                .units(3)
                .state("ACT")
                .build();
        this.productCategories = new ArrayList<>();
        productCategories.add(this.category);
        this.product = Product.builder()
                .id("64e84eb9f5783b17e73000x0")
                .name("ExampleProductName")
                .uniqueKey("123")
                .label("ExampleLabelName")
                .type("ExampleTypeName")
                .state("ACT")
                .creationDate(new Date())
                .lastModifiedDate(new Date())
                .comments("Lorem Ipsum Example Test")
                .categories(productCategories).build();
    }
    @Test
    void testObtainProductByUniqueKey() {
        when(this.productRepository.findFirstByUniqueKey("98a0e791-9903-4829-8672-2895e7ab1901")).thenReturn(this.product);
        assertDoesNotThrow(()->{
            this.productService.obtainProductByUniqueKey("98a0e791-9903-4829-8672-2895e7ab1901");
        });
        assertThrows(RuntimeException.class, () -> {
            this.productService.obtainProductByUniqueKey("e0ce35d5-0984-4408-9e6f-e6995d279a7e");
        });
        this.product.setState("INA");
        assertThrows(RuntimeException.class, () -> {
            this.productService.obtainProductByUniqueKey("98a0e791-9903-4829-8672-2895e7ab1901");
        });
    }
}