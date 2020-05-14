package name.legkodymov.orders.repository;

import name.legkodymov.orders.domain.OrderPosition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}
