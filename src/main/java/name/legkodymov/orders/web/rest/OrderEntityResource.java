package name.legkodymov.orders.web.rest;

import com.codahale.metrics.annotation.Timed;
import name.legkodymov.orders.domain.OrderEntity;
import name.legkodymov.orders.repository.OrderEntityRepository;
import name.legkodymov.orders.web.rest.errors.BadRequestAlertException;
import name.legkodymov.orders.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderEntity.
 */
@RestController
@RequestMapping("/api")
public class OrderEntityResource {

    private final Logger log = LoggerFactory.getLogger(OrderEntityResource.class);

    private static final String ENTITY_NAME = "orderEntity";

    private final OrderEntityRepository orderEntityRepository;

    public OrderEntityResource(OrderEntityRepository orderEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
    }

    /**
     * POST  /order-entities : Create a new orderEntity.
     *
     * @param orderEntity the orderEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderEntity, or with status 400 (Bad Request) if the orderEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-entities")
    @Timed
    public ResponseEntity<OrderEntity> createOrderEntity(@Valid @RequestBody OrderEntity orderEntity) throws URISyntaxException {
        log.debug("REST request to save OrderEntity : {}", orderEntity);
        if (orderEntity.getId() != null) {
            throw new BadRequestAlertException("A new orderEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderEntity result = orderEntityRepository.save(orderEntity);
        return ResponseEntity.created(new URI("/api/order-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-entities : Updates an existing orderEntity.
     *
     * @param orderEntity the orderEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderEntity,
     * or with status 400 (Bad Request) if the orderEntity is not valid,
     * or with status 500 (Internal Server Error) if the orderEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-entities")
    @Timed
    public ResponseEntity<OrderEntity> updateOrderEntity(@Valid @RequestBody OrderEntity orderEntity) throws URISyntaxException {
        log.debug("REST request to update OrderEntity : {}", orderEntity);
        if (orderEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderEntity result = orderEntityRepository.save(orderEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-entities : get all the orderEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderEntities in body
     */
    @GetMapping("/order-entities")
    @Timed
    public List<OrderEntity> getAllOrderEntities() {
        log.debug("REST request to get all OrderEntities");
        return orderEntityRepository.findAll();
    }

    /**
     * GET  /order-entities/:id : get the "id" orderEntity.
     *
     * @param id the id of the orderEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderEntity, or with status 404 (Not Found)
     */
    @GetMapping("/order-entities/{id}")
    @Timed
    public ResponseEntity<OrderEntity> getOrderEntity(@PathVariable Long id) {
        log.debug("REST request to get OrderEntity : {}", id);
        Optional<OrderEntity> orderEntity = orderEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderEntity);
    }

    /**
     * DELETE  /order-entities/:id : delete the "id" orderEntity.
     *
     * @param id the id of the orderEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderEntity(@PathVariable Long id) {
        log.debug("REST request to delete OrderEntity : {}", id);

        orderEntityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
