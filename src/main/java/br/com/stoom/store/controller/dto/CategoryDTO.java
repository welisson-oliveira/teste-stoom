package br.com.stoom.store.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String skuCode;
    private Boolean active;

}
