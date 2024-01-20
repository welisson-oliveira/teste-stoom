package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.exceptions.InvalidStatusException;
import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderBO {
    Order create(Order order);

    Page<Order> findAll(Pageable pageable);

    Order getOrder(Long id);

    Order changeStatus(Long id, OrderStatus status) throws InvalidStatusException;
}
