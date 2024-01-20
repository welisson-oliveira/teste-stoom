package br.com.stoom.store.business.orderstatus;

import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderStatus;

public interface OrderChangeStatusProcess {

    boolean canProcess(final OrderStatus status);

    void process(final OrderStatus status, final Order order, final OrderChangeStatusProcess next);

}
