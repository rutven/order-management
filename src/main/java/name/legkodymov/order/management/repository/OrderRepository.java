package name.legkodymov.order.management.repository;

import name.legkodymov.order.management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
