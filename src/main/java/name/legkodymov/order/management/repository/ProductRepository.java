package name.legkodymov.order.management.repository;

import name.legkodymov.order.management.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
