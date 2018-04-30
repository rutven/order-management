package name.legkodymov.order.management.repository;

import name.legkodymov.order.management.model.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}
