package de.hdi.codex.tdd.shoppingcart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingcart/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {
        productService.addProductToShoppingCart(product);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{produktname}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getProductByProductname(@PathVariable String produktname) {
        productService.deleteProductByProductname(produktname);
    }
}
