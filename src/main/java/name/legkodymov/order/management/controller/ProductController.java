package name.legkodymov.order.management.controller;

import name.legkodymov.order.management.model.Product;
import name.legkodymov.order.management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private static final String PRODUCT_PATH = "/product";
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = PRODUCT_PATH)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = PRODUCT_PATH + "/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping(value = PRODUCT_PATH)
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PostMapping(value = PRODUCT_PATH + "/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);

    }
}