package br.com.stoom.store.business.orderstatus;

import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderStatus;

public class OrderStatusCanceled implements OrderChangeStatusProcess {
    @Override
    public boolean canProcess(final OrderStatus status) {
        return OrderStatus.CANCELLED.equals(status);
    }

    @Override
    public void process(final OrderStatus status, final Order order, final OrderChangeStatusProcess next) {
        if (this.canProcess(status)) {
            order.stockEntry();
        } else {
            next.process(status, order, new OrderStatusReturned());
        }
    }
}
