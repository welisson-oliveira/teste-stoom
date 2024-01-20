package br.com.stoom.store.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum OrderStatus {
    PENDING {
        @Override
        public List<OrderStatus> nextStatus() {
            return Arrays.asList(OrderStatus.PROCESSING, OrderStatus.ON_HOLD, OrderStatus.CANCELLED);
        }
    },
    PROCESSING {
        @Override
        public List<OrderStatus> nextStatus() {
            return Arrays.asList(OrderStatus.CONFIRMED, OrderStatus.ON_HOLD, OrderStatus.CANCELLED);
        }
    },
    CONFIRMED {
        @Override
        public List<OrderStatus> nextStatus() {
            return Arrays.asList(OrderStatus.SHIPPED, OrderStatus.ON_HOLD, OrderStatus.CANCELLED);
        }
    },
    SHIPPED {
        @Override
        public List<OrderStatus> nextStatus() {
            return Arrays.asList(OrderStatus.DELIVERED, OrderStatus.ON_HOLD, OrderStatus.CANCELLED);
        }
    },
    DELIVERED {
        @Override
        public List<OrderStatus> nextStatus() {
            return Collections.singletonList(OrderStatus.RETURNED);
        }
    },
    CANCELLED {
        @Override
        public List<OrderStatus> nextStatus() {
            return Collections.emptyList();
        }
    },
    RETURNED {
        @Override
        public List<OrderStatus> nextStatus() {
            return Collections.emptyList();
        }
    },
    ON_HOLD {
        @Override
        public List<OrderStatus> nextStatus() {
            return Arrays.asList(PENDING, PROCESSING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, RETURNED);
        }
    };

    public abstract List<OrderStatus> nextStatus();

}
