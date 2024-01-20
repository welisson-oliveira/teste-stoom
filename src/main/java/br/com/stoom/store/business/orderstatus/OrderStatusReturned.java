package br.com.stoom.store.business.orderstatus;

import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderStatus;

public class OrderStatusReturned implements OrderChangeStatusProcess {
    @Override
    public boolean canProcess(final OrderStatuses orderStatuses) {
        return OrderStatus.RETURNED.equals(orderStatuses.getStatus());
    }

    @Override
    public void process(final OrderStatus status, final Order order, final OrderChangeStatusProcess next) {
        if (this.canProcess(new OrderStatuses(status, order.getPreviousStatus()))) {
            order.stockEntry();
        }
    }
}
