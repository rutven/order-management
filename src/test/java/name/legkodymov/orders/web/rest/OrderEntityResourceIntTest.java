package name.legkodymov.orders.web.rest;

import name.legkodymov.orders.MainApp;

import name.legkodymov.orders.domain.OrderEntity;
import name.legkodymov.orders.repository.OrderEntityRepository;
import name.legkodymov.orders.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static name.legkodymov.orders.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderEntityResource REST controller.
 *
 * @see OrderEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApp.class)
public class OrderEntityResourceIntTest {

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderEntityMockMvc;

    private OrderEntity orderEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderEntityResource orderEntityResource = new OrderEntityResource(orderEntityRepository);
        this.restOrderEntityMockMvc = MockMvcBuilders.standaloneSetup(orderEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

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

    @Before
    public void initTest() {
        orderEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderEntity() throws Exception {
        int databaseSizeBeforeCreate = orderEntityRepository.findAll().size();

        // Create the OrderEntity
        restOrderEntityMockMvc.perform(post("/api/order-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
        orderEntityRepository.saveAndFlush(orderEntity);

        int databaseSizeBeforeUpdate = orderEntityRepository.findAll().size();

        // Update the orderEntity
        OrderEntity updatedOrderEntity = orderEntityRepository.findById(orderEntity.getId()).get();
        // Disconnect from session so that the updates on updatedOrderEntity are not directly saved in db
        em.detach(updatedOrderEntity);
        updatedOrderEntity
            .orderDate(UPDATED_ORDER_DATE);

        restOrderEntityMockMvc.perform(put("/api/order-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
        orderEntityRepository.saveAndFlush(orderEntity);

        int databaseSizeBeforeDelete = orderEntityRepository.findAll().size();

        // Get the orderEntity
        restOrderEntityMockMvc.perform(delete("/api/order-entities/{id}", orderEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderEntity> orderEntityList = orderEntityRepository.findAll();
        assertThat(orderEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderEntity.class);
        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setId(1L);
        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(orderEntity1.getId());
        assertThat(orderEntity1).isEqualTo(orderEntity2);
        orderEntity2.setId(2L);
        assertThat(orderEntity1).isNotEqualTo(orderEntity2);
        orderEntity1.setId(null);
        assertThat(orderEntity1).isNotEqualTo(orderEntity2);
    }
}
