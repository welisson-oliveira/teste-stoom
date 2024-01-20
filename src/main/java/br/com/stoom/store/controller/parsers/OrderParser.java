package br.com.stoom.store.controller.parsers;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.controller.dto.OrderDTO;
import br.com.stoom.store.controller.dto.OrderItemDTO;
import br.com.stoom.store.controller.dto.ProductDTO;
import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderItem;
import br.com.stoom.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderParser extends Parser<Order, OrderDTO> {

    private final IProductBO productBO;
    private final Parser<Product, ProductDTO> productDTO;

    @Override
    public Order parse(final OrderDTO orderDTO) {
        final List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                .map(item -> new OrderItem(this.productBO.getProduct(item.getProductId()), item.getQuantity()))
                .collect(Collectors.toList());

        return new Order(orderItems);
    }

    @Override
    public OrderDTO toDTO(final Order order) {
        return new OrderDTO(
                order.getId(),
                order.getItems().stream().map(item -> {
                    final ProductDTO product = this.productDTO.toDTO(item.getProduct());
                    return new OrderItemDTO(item.getId(), product, item.getQuantity(), item.getPrice());
                }).collect(Collectors.toList()),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus());
    }
}
