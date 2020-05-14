package name.legkodymov.orders.service.impl;

import name.legkodymov.orders.service.OrderEntityService;
import name.legkodymov.orders.domain.OrderEntity;
import name.legkodymov.orders.repository.OrderEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderEntity}.
 */
@Service
@Transactional
public class OrderEntityServiceImpl implements OrderEntityService {

    private final Logger log = LoggerFactory.getLogger(OrderEntityServiceImpl.class);

    private final OrderEntityRepository orderEntityRepository;

    public OrderEntityServiceImpl(OrderEntityRepository orderEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
    }

    /**
     * Save a orderEntity.
     *
     * @param orderEntity the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        log.debug("Request to save OrderEntity : {}", orderEntity);
        return orderEntityRepository.save(orderEntity);
    }

    /**
     * Get all the orderEntities.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> findAll() {
        log.debug("Request to get all OrderEntities");
        return orderEntityRepository.findAll();
    }

    /**
     * Get one orderEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderEntity> findOne(Long id) {
        log.debug("Request to get OrderEntity : {}", id);
        return orderEntityRepository.findById(id);
    }

    /**
     * Delete the orderEntity by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderEntity : {}", id);
        orderEntityRepository.deleteById(id);
    }
}
