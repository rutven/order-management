package name.legkodymov.orders.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrderEntity.
 */
@Entity
@Table(name = "order_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @OneToMany(mappedBy = "orderEntity")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderPosition> orderPositions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderEntity orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Set<OrderPosition> getOrderPositions() {
        return orderPositions;
    }

    public OrderEntity orderPositions(Set<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
        return this;
    }

    public OrderEntity addOrderPosition(OrderPosition orderPosition) {
        this.orderPositions.add(orderPosition);
        orderPosition.setOrderEntity(this);
        return this;
    }

    public OrderEntity removeOrderPosition(OrderPosition orderPosition) {
        this.orderPositions.remove(orderPosition);
        orderPosition.setOrderEntity(null);
        return this;
    }

    public void setOrderPositions(Set<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderEntity orderEntity = (OrderEntity) o;
        if (orderEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            "}";
    }
}
