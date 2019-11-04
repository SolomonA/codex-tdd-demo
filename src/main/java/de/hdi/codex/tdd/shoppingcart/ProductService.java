package de.hdi.codex.tdd.shoppingcart;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProductToShoppingCart(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductByProductname(String productname) {
        Optional<Product> product = productRepository.findByProductname(productname);
        product.ifPresent(productRepository::delete);
    }
}
