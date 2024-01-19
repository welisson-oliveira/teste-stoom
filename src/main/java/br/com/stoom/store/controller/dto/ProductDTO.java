package br.com.stoom.store.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ProductDTO {
    private Long id;
    private String sku;
    private String name;
    private String category;
    private String brand;
    private BigDecimal price;
    private String description;
    private Long stockQuantity;
    private Boolean active;
    private List<ImageDTO> images;
}
