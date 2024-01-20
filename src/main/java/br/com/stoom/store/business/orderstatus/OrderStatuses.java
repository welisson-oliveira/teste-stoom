package br.com.stoom.store.business.orderstatus;

import br.com.stoom.store.model.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderStatuses {
    private final OrderStatus status;
    private final OrderStatus previousStatus;
}
