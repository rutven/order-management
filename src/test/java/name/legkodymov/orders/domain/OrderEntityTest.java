package name.legkodymov.orders.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import name.legkodymov.orders.web.rest.TestUtil;

public class OrderEntityTest {

    @Test
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
