package name.legkodymov.order.management.service;

import name.legkodymov.order.management.model.Product;
import name.legkodymov.order.management.repository.ProductRepository;
import name.legkodymov.order.management.service.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Long productId) {
        return productRepository.getOne(productId);
    }

    public Product updateProduct(Long id, Product product) {
        Product productForUpdate = productRepository.getOne(id);

        if (productForUpdate != null) {
            productForUpdate.setName(product.getName());

            return productRepository.save(product);
        } else {
            throw new NotFoundException("Product with id=" + id + " not found!");
        }
    }
}
