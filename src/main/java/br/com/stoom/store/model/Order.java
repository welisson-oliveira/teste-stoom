package br.com.stoom.store.model;

import br.com.stoom.store.exceptions.InvalidStatusException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "\"order\"")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "ORDER_SEQ")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private final List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus previousStatus;

    public Order(final List<OrderItem> items) {
        this.orderDate = LocalDateTime.now();
        this.totalAmount = BigDecimal.ZERO;
        items.forEach(this::addItem);
        this.status = OrderStatus.PENDING;
        this.previousStatus = OrderStatus.PENDING;
    }

    private void addItem(final OrderItem item) {
        this.items.add(item);
        this.totalAmount = this.totalAmount.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
    }

    public void changeStatus(final OrderStatus orderStatus) throws InvalidStatusException {
        if (OrderStatus.ON_HOLD.equals(this.status) && !this.previousStatus.equals(orderStatus)) {
            throw new InvalidStatusException("não pode mudar para o estado de " + this.status + " para " + orderStatus.name() + " antes volte para " + this.previousStatus);
        } else if (this.status.nextStatus().stream().anyMatch(s -> s.equals(orderStatus))) {
            this.previousStatus = this.status;
            this.status = orderStatus;
        } else {
            throw new InvalidStatusException("não pode mudar para o estado de " + this.status + " para " + orderStatus.name());
        }
    }

    public void writeOff() {
        this.items.forEach(OrderItem::writeOff);
    }

    public void stockEntry() {
        this.items.forEach(OrderItem::stockEntry);
    }

}
