package name.legkodymov.orders.service;

import name.legkodymov.orders.domain.OrderPosition;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing OrderPosition.
 */
public interface OrderPositionService {

    /**
     * Save a orderPosition.
     *
     * @param orderPosition the entity to save
     * @return the persisted entity
     */
    OrderPosition save(OrderPosition orderPosition);

    /**
     * Get all the orderPositions.
     *
     * @return the list of entities
     */
    List<OrderPosition> findAll();


    /**
     * Get the "id" orderPosition.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrderPosition> findOne(Long id);

    /**
     * Delete the "id" orderPosition.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
