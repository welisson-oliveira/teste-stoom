package br.com.stoom.store.business.orderstatus;

import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusPending implements OrderChangeStatusProcess {
    @Override
    public boolean canProcess(final OrderStatuses orderStatuses) {
        return OrderStatus.PENDING.equals(orderStatuses.getStatus()) && !OrderStatus.ON_HOLD.equals(orderStatuses.getPreviousStatus());
    }

    @Override
    public void process(final OrderStatus status, final Order order, final OrderChangeStatusProcess next) {
        if (this.canProcess(new OrderStatuses(status, order.getPreviousStatus()))) {
            order.writeOff();
        } else {
            next.process(status, order, new OrderStatusReturned());
        }

    }
}
