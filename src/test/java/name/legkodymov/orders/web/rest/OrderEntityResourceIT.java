package name.legkodymov.orders.web.rest;

import name.legkodymov.orders.MainApp;
import name.legkodymov.orders.domain.OrderEntity;
import name.legkodymov.orders.repository.OrderEntityRepository;
import name.legkodymov.orders.service.OrderEntityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderEntityResource} REST controller.
 */
@SpringBootTest(classes = MainApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class OrderEntityResourceIT {

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @Autowired
    private OrderEntityService orderEntityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderEntityMockMvc;

    private OrderEntity orderEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderEntity createEntity(EntityManager em) {
        OrderEntity orderEntity = new OrderEntity()
            .orderDate(DEFAULT_ORDER_DATE);
        return orderEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderEntity createUpdatedEntity(EntityManager em) {
        OrderEntity orderEntity = new OrderEntity()
            .orderDate(UPDATED_ORDER_DATE);
        return orderEntity;
    }

    @BeforeEach
    public void initTest() {
        orderEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderEntity() throws Exception {
        int databaseSizeBeforeCreate = orderEntityRepository.findAll().size();

        // Create the OrderEntity
        restOrderEntityMockMvc.perform(post("/api/order-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderEntity)))
            .andExpect(status().isCreated());

        // Validate the OrderEntity in the database
        List<OrderEntity> orderEntityList = orderEntityRepository.findAll();
        assertThat(orderEntityList).hasSize(databaseSizeBeforeCreate + 1);
        OrderEntity testOrderEntity = orderEntityList.get(orderEntityList.size() - 1);
        assertThat(testOrderEntity.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
    }

    @Test
    @Transactional
    public void createOrderEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderEntityRepository.findAll().size();

        // Create the OrderEntity with an existing ID
        orderEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderEntityMockMvc.perform(post("/api/order-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderEntity)))
            .andExpect(status().isBadRequest());

        // Validate the OrderEntity in the database
        List<OrderEntity> orderEntityList = orderEntityRepository.findAll();
        assertThat(orderEntityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderEntityRepository.findAll().size();
        // set the field null
        orderEntity.setOrderDate(null);

        // Create the OrderEntity, which fails.

        restOrderEntityMockMvc.perform(post("/api/order-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderEntity)))
            .andExpect(status().isBadRequest());

        List<OrderEntity> orderEntityList = orderEntityRepository.findAll();
        assertThat(orderEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderEntities() throws Exception {
        // Initialize the database
        orderEntityRepository.saveAndFlush(orderEntity);

        // Get all the orderEntityList
        restOrderEntityMockMvc.perform(get("/api/order-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderEntity() throws Exception {
        // Initialize the database
        orderEntityRepository.saveAndFlush(orderEntity);

        // Get the orderEntity
        restOrderEntityMockMvc.perform(get("/api/order-entities/{id}", orderEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderEntity.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderEntity() throws Exception {
        // Get the orderEntity
        restOrderEntityMockMvc.perform(get("/api/order-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderEntity() throws Exception {
        // Initialize the database
        orderEntityService.save(orderEntity);

        int databaseSizeBeforeUpdate = orderEntityRepository.findAll().size();

        // Update the orderEntity
        OrderEntity updatedOrderEntity = orderEntityRepository.findById(orderEntity.getId()).get();
        // Disconnect from session so that the updates on updatedOrderEntity are not directly saved in db
        em.detach(updatedOrderEntity);
        updatedOrderEntity
            .orderDate(UPDATED_ORDER_DATE);

        restOrderEntityMockMvc.perform(put("/api/order-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderEntity)))
            .andExpect(status().isOk());

        // Validate the OrderEntity in the database
        List<OrderEntity> orderEntityList = orderEntityRepository.findAll();
        assertThat(orderEntityList).hasSize(databaseSizeBeforeUpdate);
        OrderEntity testOrderEntity = orderEntityList.get(orderEntityList.size() - 1);
        assertThat(testOrderEntity.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderEntity() throws Exception {
        int databaseSizeBeforeUpdate = orderEntityRepository.findAll().size();

        // Create the OrderEntity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderEntityMockMvc.perform(put("/api/order-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderEntity)))
            .andExpect(status().isBadRequest());

        // Validate the OrderEntity in the database
        List<OrderEntity> orderEntityList = orderEntityRepository.findAll();
        assertThat(orderEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderEntity() throws Exception {
        // Initialize the database
        orderEntityService.save(orderEntity);

        int databaseSizeBeforeDelete = orderEntityRepository.findAll().size();

        // Delete the orderEntity
        restOrderEntityMockMvc.perform(delete("/api/order-entities/{id}", orderEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderEntity> orderEntityList = orderEntityRepository.findAll();
        assertThat(orderEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
