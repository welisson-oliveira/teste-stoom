package br.com.stoom.store.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sequence")
    @SequenceGenerator(name = "order_item_sequence", sequenceName = "ORDER_ITEM_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    public OrderItem(final Product product, final Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return Objects.isNull(this.product) ? null : this.product.getPrice();
    }

    public void writeOff() {
        this.product.writeOff(this.quantity);
    }

    public void stockEntry() {
        this.product.stockEntry(this.quantity);
    }
}
