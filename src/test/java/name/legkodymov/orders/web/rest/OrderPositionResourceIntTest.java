package name.legkodymov.orders.web.rest;

import name.legkodymov.orders.MainApp;

import name.legkodymov.orders.domain.OrderPosition;
import name.legkodymov.orders.repository.OrderPositionRepository;
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
import java.util.List;


import static name.legkodymov.orders.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderPositionResource REST controller.
 *
 * @see OrderPositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApp.class)
public class OrderPositionResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private OrderPositionRepository orderPositionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderPositionMockMvc;

    private OrderPosition orderPosition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderPositionResource orderPositionResource = new OrderPositionResource(orderPositionRepository);
        this.restOrderPositionMockMvc = MockMvcBuilders.standaloneSetup(orderPositionResource)
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
    public static OrderPosition createEntity(EntityManager em) {
        OrderPosition orderPosition = new OrderPosition()
            .quantity(DEFAULT_QUANTITY);
        return orderPosition;
    }

    @Before
    public void initTest() {
        orderPosition = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderPosition() throws Exception {
        int databaseSizeBeforeCreate = orderPositionRepository.findAll().size();

        // Create the OrderPosition
        restOrderPositionMockMvc.perform(post("/api/order-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPosition)))
            .andExpect(status().isCreated());

        // Validate the OrderPosition in the database
        List<OrderPosition> orderPositionList = orderPositionRepository.findAll();
        assertThat(orderPositionList).hasSize(databaseSizeBeforeCreate + 1);
        OrderPosition testOrderPosition = orderPositionList.get(orderPositionList.size() - 1);
        assertThat(testOrderPosition.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createOrderPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderPositionRepository.findAll().size();

        // Create the OrderPosition with an existing ID
        orderPosition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderPositionMockMvc.perform(post("/api/order-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPosition)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPosition in the database
        List<OrderPosition> orderPositionList = orderPositionRepository.findAll();
        assertThat(orderPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderPositionRepository.findAll().size();
        // set the field null
        orderPosition.setQuantity(null);

        // Create the OrderPosition, which fails.

        restOrderPositionMockMvc.perform(post("/api/order-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPosition)))
            .andExpect(status().isBadRequest());

        List<OrderPosition> orderPositionList = orderPositionRepository.findAll();
        assertThat(orderPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderPositions() throws Exception {
        // Initialize the database
        orderPositionRepository.saveAndFlush(orderPosition);

        // Get all the orderPositionList
        restOrderPositionMockMvc.perform(get("/api/order-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getOrderPosition() throws Exception {
        // Initialize the database
        orderPositionRepository.saveAndFlush(orderPosition);

        // Get the orderPosition
        restOrderPositionMockMvc.perform(get("/api/order-positions/{id}", orderPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderPosition.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingOrderPosition() throws Exception {
        // Get the orderPosition
        restOrderPositionMockMvc.perform(get("/api/order-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderPosition() throws Exception {
        // Initialize the database
        orderPositionRepository.saveAndFlush(orderPosition);

        int databaseSizeBeforeUpdate = orderPositionRepository.findAll().size();

        // Update the orderPosition
        OrderPosition updatedOrderPosition = orderPositionRepository.findById(orderPosition.getId()).get();
        // Disconnect from session so that the updates on updatedOrderPosition are not directly saved in db
        em.detach(updatedOrderPosition);
        updatedOrderPosition
            .quantity(UPDATED_QUANTITY);

        restOrderPositionMockMvc.perform(put("/api/order-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderPosition)))
            .andExpect(status().isOk());

        // Validate the OrderPosition in the database
        List<OrderPosition> orderPositionList = orderPositionRepository.findAll();
        assertThat(orderPositionList).hasSize(databaseSizeBeforeUpdate);
        OrderPosition testOrderPosition = orderPositionList.get(orderPositionList.size() - 1);
        assertThat(testOrderPosition.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderPosition() throws Exception {
        int databaseSizeBeforeUpdate = orderPositionRepository.findAll().size();

        // Create the OrderPosition

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderPositionMockMvc.perform(put("/api/order-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPosition)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPosition in the database
        List<OrderPosition> orderPositionList = orderPositionRepository.findAll();
        assertThat(orderPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderPosition() throws Exception {
        // Initialize the database
        orderPositionRepository.saveAndFlush(orderPosition);

        int databaseSizeBeforeDelete = orderPositionRepository.findAll().size();

        // Get the orderPosition
        restOrderPositionMockMvc.perform(delete("/api/order-positions/{id}", orderPosition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderPosition> orderPositionList = orderPositionRepository.findAll();
        assertThat(orderPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPosition.class);
        OrderPosition orderPosition1 = new OrderPosition();
        orderPosition1.setId(1L);
        OrderPosition orderPosition2 = new OrderPosition();
        orderPosition2.setId(orderPosition1.getId());
        assertThat(orderPosition1).isEqualTo(orderPosition2);
        orderPosition2.setId(2L);
        assertThat(orderPosition1).isNotEqualTo(orderPosition2);
        orderPosition1.setId(null);
        assertThat(orderPosition1).isNotEqualTo(orderPosition2);
    }
}
