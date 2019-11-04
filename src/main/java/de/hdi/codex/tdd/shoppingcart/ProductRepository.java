package de.hdi.codex.tdd.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductname(String productToBeDeleted);
}
