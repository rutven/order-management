package name.legkodymov.orders.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import name.legkodymov.orders.web.rest.TestUtil;

public class OrderPositionTest {

    @Test
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
