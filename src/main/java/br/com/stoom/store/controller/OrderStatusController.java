package br.com.stoom.store.controller;

import br.com.stoom.store.model.OrderStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-statuses")
public class OrderStatusController {

    @GetMapping
    public OrderStatus[] getAllOrderStatuses() {
        return OrderStatus.values();
    }

}
