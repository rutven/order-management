package name.legkodymov.orders.web.rest;

import name.legkodymov.orders.domain.OrderPosition;
import name.legkodymov.orders.service.OrderPositionService;
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
 * REST controller for managing {@link name.legkodymov.orders.domain.OrderPosition}.
 */
@RestController
@RequestMapping("/api")
public class OrderPositionResource {

    private final Logger log = LoggerFactory.getLogger(OrderPositionResource.class);

    private static final String ENTITY_NAME = "orderPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderPositionService orderPositionService;

    public OrderPositionResource(OrderPositionService orderPositionService) {
        this.orderPositionService = orderPositionService;
    }

    /**
     * {@code POST  /order-positions} : Create a new orderPosition.
     *
     * @param orderPosition the orderPosition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderPosition, or with status {@code 400 (Bad Request)} if the orderPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-positions")
    public ResponseEntity<OrderPosition> createOrderPosition(@Valid @RequestBody OrderPosition orderPosition) throws URISyntaxException {
        log.debug("REST request to save OrderPosition : {}", orderPosition);
        if (orderPosition.getId() != null) {
            throw new BadRequestAlertException("A new orderPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderPosition result = orderPositionService.save(orderPosition);
        return ResponseEntity.created(new URI("/api/order-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-positions} : Updates an existing orderPosition.
     *
     * @param orderPosition the orderPosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderPosition,
     * or with status {@code 400 (Bad Request)} if the orderPosition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderPosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-positions")
    public ResponseEntity<OrderPosition> updateOrderPosition(@Valid @RequestBody OrderPosition orderPosition) throws URISyntaxException {
        log.debug("REST request to update OrderPosition : {}", orderPosition);
        if (orderPosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderPosition result = orderPositionService.save(orderPosition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPosition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-positions} : get all the orderPositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderPositions in body.
     */
    @GetMapping("/order-positions")
    public List<OrderPosition> getAllOrderPositions() {
        log.debug("REST request to get all OrderPositions");
        return orderPositionService.findAll();
    }

    /**
     * {@code GET  /order-positions/:id} : get the "id" orderPosition.
     *
     * @param id the id of the orderPosition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderPosition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-positions/{id}")
    public ResponseEntity<OrderPosition> getOrderPosition(@PathVariable Long id) {
        log.debug("REST request to get OrderPosition : {}", id);
        Optional<OrderPosition> orderPosition = orderPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderPosition);
    }

    /**
     * {@code DELETE  /order-positions/:id} : delete the "id" orderPosition.
     *
     * @param id the id of the orderPosition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-positions/{id}")
    public ResponseEntity<Void> deleteOrderPosition(@PathVariable Long id) {
        log.debug("REST request to delete OrderPosition : {}", id);
        orderPositionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
