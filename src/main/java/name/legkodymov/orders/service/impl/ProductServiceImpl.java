package name.legkodymov.orders.service.impl;

import name.legkodymov.orders.service.ProductService;
import name.legkodymov.orders.domain.Product;
import name.legkodymov.orders.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Save a product.
     *
     * @param product the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Product save(Product product) {
        log.debug("Request to save Product : {}", product);
        return productRepository.save(product);
    }

    /**
     * Get all the products.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        log.debug("Request to get all Products");
        return productRepository.findAll();
    }


    /**
     * Get one product by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
