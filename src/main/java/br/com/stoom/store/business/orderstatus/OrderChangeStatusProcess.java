package br.com.stoom.store.business.orderstatus;

import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderStatus;

public interface OrderChangeStatusProcess {

    boolean canProcess(OrderStatuses orderStatuses);

    void process(final OrderStatus status, final Order order, final OrderChangeStatusProcess next);

}
