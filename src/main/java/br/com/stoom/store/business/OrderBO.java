package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IOrderBO;
import br.com.stoom.store.business.orderstatus.OrderChangeStatusProcess;
import br.com.stoom.store.business.orderstatus.OrderStatusCanceled;
import br.com.stoom.store.exceptions.InvalidStatusException;
import br.com.stoom.store.exceptions.ResourceNotFoundException;
import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderStatus;
import br.com.stoom.store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderBO implements IOrderBO {

    private final OrderRepository orderRepository;
    private final OrderChangeStatusProcess orderChangeStatusProcess;

    @Override
    public Order create(final Order order) {
        this.orderChangeStatusProcess.process(OrderStatus.PENDING, order, new OrderStatusCanceled());
        return this.orderRepository.save(order);
    }

    @Override
    public Page<Order> findAll(final Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

    @Override
    public Order getOrder(final Long id) {
        return this.orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido nao encontrado"));
    }

    @Override
    public Order changeStatus(final Long id, final OrderStatus status) throws InvalidStatusException {
        final Order order = this.getOrder(id);

        order.changeStatus(status);
        this.orderChangeStatusProcess.process(status, order, new OrderStatusCanceled());

        return this.orderRepository.save(order);
    }
}
