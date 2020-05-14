package name.legkodymov.orders.service;

import name.legkodymov.orders.domain.OrderEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrderEntity}.
 */
public interface OrderEntityService {

    /**
     * Save a orderEntity.
     *
     * @param orderEntity the entity to save.
     * @return the persisted entity.
     */
    OrderEntity save(OrderEntity orderEntity);

    /**
     * Get all the orderEntities.
     *
     * @return the list of entities.
     */
    List<OrderEntity> findAll();

    /**
     * Get the "id" orderEntity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderEntity> findOne(Long id);

    /**
     * Delete the "id" orderEntity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
