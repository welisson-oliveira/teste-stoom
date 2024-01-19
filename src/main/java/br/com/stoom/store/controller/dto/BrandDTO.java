package br.com.stoom.store.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class BrandDTO {

    private Long id;
    private String name;
    private String skuCode;
    private Boolean active;

}
