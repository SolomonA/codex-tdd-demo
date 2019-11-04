package de.hdi.codex.tdd.shoppingcart;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @RunWith(SpringRunner.class). The annotation disables full auto-configuration and applies only configuration relevant to JPA tests.
 * By default, tests annotated with @DataJpaTest use an embedded in-memory database.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void findByProductname() {
        //Arrange
        Product product = new Product(1L, "Apfel", 1);
        entityManager.persist(product);

        //Act
        Optional<Product> result = productRepository.findByProductname("Apfel");

        //Assert
        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result).isEqualTo(Optional.of(product));
    }

}
