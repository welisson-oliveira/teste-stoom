package br.com.stoom.store.controller.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemDTO {
    private Long id;
    private ProductDTO product;
    private Long quantity;
    private BigDecimal price;

    public Long getProductId() {
        return this.product.getId();
    }
}
