package de.hdi.codex.tdd.shoppingcart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


/**
 * You should use MockMvc when you want to test Server-side of application.
 * MockMvc considers controller endpoints as the methods of the class and tests method behavior.
 *
 * @WebMvcTest Annotation that can be used for a Spring MVC test that focuses <strong>only</strong> on
 * Spring MVC components.
 * <p>
 * Using this annotation will disable full auto-configuration and instead apply only
 * configuration relevant to MVC tests (i.e. {@code @Controller},
 * {@code @ControllerAdvice}, {@code @JsonComponent},
 * @RunWith(SpringRunner.class) is used to provide a bridge between Spring Boot test features and JUnit.
 * Whenever we are using any Spring Boot testing features in our JUnit tests, this annotation will be required.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Zum Simulieren der Service Aufrufe und zum Prüfen ob der Service aufgerufen wurde
     */
    @MockBean
    ProductService mockProductService;

    @Test
    public void getProducts_StatusOK_1ProductInList() throws Exception {
        //Arrange
        String uriToBeCalled = "/shoppingcart/products";

        //Da wir nur den Controller Layer testen wollen, mocken wir den
        // Aufruf des Services und geben eine Liste von Produkten zurück.
        Mockito.when(mockProductService.getAllProducts())
                .thenReturn(Collections.singletonList(new Product(1L, "Banane", 1)));

        //Act
        mockMvc.perform(MockMvcRequestBuilders.get(uriToBeCalled)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                //Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productname", is("Banane")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));

        //Assert
        //Verifiziere dass die Methode zum Ermitteln des Produkte 1 mal aufgerufen wurde
        Mockito.verify(mockProductService, Mockito.times(1))
                .getAllProducts();
    }

    @Test
    public void addProduct_StatusCreated() throws Exception {

        //Arrange

        String uriToBeCalled = "/shoppingcart/products";

        Product productToBeAdded = new Product(1L, "Banane", 1);

        ObjectMapper objectMapper = new ObjectMapper();

        //Act
        mockMvc.perform(MockMvcRequestBuilders.post(uriToBeCalled)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToBeAdded))

                //Assert
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //Assert
        //Verifiziere dass die Methode zum Speichern des Produkts 1 mal aufgerufen wurde
        Mockito.verify(mockProductService, Mockito.times(1))
                .addProductToShoppingCart(productToBeAdded);
    }

    @Test
    public void deleteProduct_StatusNoContent() throws Exception {
        //Arrange

        String uriToBeCalled = "/shoppingcart/products/Birnen";
        String productnameToBeDelete = "Birnen";

        //Da wir nur den Controller Layer testen, mocken wir den
        // Aufruf des Services und geben eine Liste von Produkten zurück
        Mockito.doNothing().when(mockProductService)
                .deleteProductByProductname(productnameToBeDelete);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete(uriToBeCalled)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Assert

        //Verifiziere dass die Methode zum Löschen des Produkts 1 mal aufgerufen wurde
        Mockito.verify(mockProductService, Mockito.times(1))
                .deleteProductByProductname(productnameToBeDelete);
    }
}
