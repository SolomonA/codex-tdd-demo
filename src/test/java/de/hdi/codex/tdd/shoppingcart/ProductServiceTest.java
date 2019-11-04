package de.hdi.codex.tdd.shoppingcart;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void addProductToShoppingCart() {
        //Arrange
        Product productToBeSaved = new Product(1L, "Birnen", 1);

        ProductService productService = new ProductService(productRepository);

        //Act
        productService.addProductToShoppingCart(productToBeSaved);

        //Assert
        verify(productRepository, times(1)).save(productToBeSaved);
    }

    @Test
    public void getAllProductsFromShoppingCart() {
        //Arrange
        List<Product> productToBeReturned = Arrays.asList(new Product(1L, "Birnen", 1),
                new Product(2L, "Bananen", 2));

        when(productRepository.findAll()).thenReturn(productToBeReturned);

        ProductService productService = new ProductService(productRepository);

        //Act
        BDDAssertions.assertThat(productService.getAllProducts()).isEqualTo(productToBeReturned);

        //Assert
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void deleteProductFromShoppingCart() {
        //Arrange
        Product productToBeDeleted = new Product(1L, "Kirschen", 1);
        when(productRepository.findByProductname("Kirschen"))
                .thenReturn(Optional.of(productToBeDeleted));

        ProductService productService = new ProductService(productRepository);

        //Act
        productService.deleteProductByProductname("Kirschen");

        //Assert
        verify(productRepository, times(1)).findByProductname("Kirschen");
        verify(productRepository, times(1)).delete(productToBeDeleted);
    }
}