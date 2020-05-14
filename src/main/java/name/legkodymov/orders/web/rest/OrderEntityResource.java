package name.legkodymov.orders.web.rest;

import name.legkodymov.orders.domain.OrderEntity;
import name.legkodymov.orders.service.OrderEntityService;
import name.legkodymov.orders.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link name.legkodymov.orders.domain.OrderEntity}.
 */
@RestController
@RequestMapping("/api")
public class OrderEntityResource {

    private final Logger log = LoggerFactory.getLogger(OrderEntityResource.class);

    private static final String ENTITY_NAME = "orderEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderEntityService orderEntityService;

    public OrderEntityResource(OrderEntityService orderEntityService) {
        this.orderEntityService = orderEntityService;
    }

    /**
     * {@code POST  /order-entities} : Create a new orderEntity.
     *
     * @param orderEntity the orderEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderEntity, or with status {@code 400 (Bad Request)} if the orderEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-entities")
    public ResponseEntity<OrderEntity> createOrderEntity(@Valid @RequestBody OrderEntity orderEntity) throws URISyntaxException {
        log.debug("REST request to save OrderEntity : {}", orderEntity);
        if (orderEntity.getId() != null) {
            throw new BadRequestAlertException("A new orderEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderEntity result = orderEntityService.save(orderEntity);
        return ResponseEntity.created(new URI("/api/order-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-entities} : Updates an existing orderEntity.
     *
     * @param orderEntity the orderEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderEntity,
     * or with status {@code 400 (Bad Request)} if the orderEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-entities")
    public ResponseEntity<OrderEntity> updateOrderEntity(@Valid @RequestBody OrderEntity orderEntity) throws URISyntaxException {
        log.debug("REST request to update OrderEntity : {}", orderEntity);
        if (orderEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderEntity result = orderEntityService.save(orderEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-entities} : get all the orderEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderEntities in body.
     */
    @GetMapping("/order-entities")
    public List<OrderEntity> getAllOrderEntities() {
        log.debug("REST request to get all OrderEntities");
        return orderEntityService.findAll();
    }

    /**
     * {@code GET  /order-entities/:id} : get the "id" orderEntity.
     *
     * @param id the id of the orderEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-entities/{id}")
    public ResponseEntity<OrderEntity> getOrderEntity(@PathVariable Long id) {
        log.debug("REST request to get OrderEntity : {}", id);
        Optional<OrderEntity> orderEntity = orderEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderEntity);
    }

    /**
     * {@code DELETE  /order-entities/:id} : delete the "id" orderEntity.
     *
     * @param id the id of the orderEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-entities/{id}")
    public ResponseEntity<Void> deleteOrderEntity(@PathVariable Long id) {
        log.debug("REST request to delete OrderEntity : {}", id);
        orderEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
