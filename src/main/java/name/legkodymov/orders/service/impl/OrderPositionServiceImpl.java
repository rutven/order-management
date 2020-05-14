package name.legkodymov.orders.service.impl;

import name.legkodymov.orders.service.OrderPositionService;
import name.legkodymov.orders.domain.OrderPosition;
import name.legkodymov.orders.repository.OrderPositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderPosition}.
 */
@Service
@Transactional
public class OrderPositionServiceImpl implements OrderPositionService {

    private final Logger log = LoggerFactory.getLogger(OrderPositionServiceImpl.class);

    private final OrderPositionRepository orderPositionRepository;

    public OrderPositionServiceImpl(OrderPositionRepository orderPositionRepository) {
        this.orderPositionRepository = orderPositionRepository;
    }

    /**
     * Save a orderPosition.
     *
     * @param orderPosition the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderPosition save(OrderPosition orderPosition) {
        log.debug("Request to save OrderPosition : {}", orderPosition);
        return orderPositionRepository.save(orderPosition);
    }

    /**
     * Get all the orderPositions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderPosition> findAll() {
        log.debug("Request to get all OrderPositions");
        return orderPositionRepository.findAll();
    }

    /**
     * Get one orderPosition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderPosition> findOne(Long id) {
        log.debug("Request to get OrderPosition : {}", id);
        return orderPositionRepository.findById(id);
    }

    /**
     * Delete the orderPosition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderPosition : {}", id);
        orderPositionRepository.deleteById(id);
    }
}
