package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IOrderBO;
import br.com.stoom.store.controller.dto.OrderDTO;
import br.com.stoom.store.controller.pageconverter.PageConverter;
import br.com.stoom.store.controller.parsers.Parser;
import br.com.stoom.store.exceptions.InvalidStatusException;
import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderBO orderBO;
    private final Parser<Order, OrderDTO> orderParser;
    private final PageConverter<Order, OrderDTO> pageConverter;

    @GetMapping
    public Page<OrderDTO> findAll(final Pageable pageable) {
        return this.orderBO.findAll(pageable).map(this.pageConverter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody final OrderDTO order) {
        return this.orderParser.toDTO(this.orderBO.create(this.orderParser.parse(order)));
    }

    @PatchMapping("/{id}/status/{status}")
    public OrderDTO changeStatus(@PathVariable final Long id, @PathVariable final OrderStatus status) throws InvalidStatusException {
        return this.orderParser.toDTO(this.orderBO.changeStatus(id, status));
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable final Long id) {
        return this.orderParser.toDTO(this.orderBO.getOrder(id));
    }

}
