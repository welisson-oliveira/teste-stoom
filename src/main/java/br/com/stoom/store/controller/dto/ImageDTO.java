package br.com.stoom.store.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageDTO {

    private Long id;
    private String name;
    private String path;
    private Long product;
    private byte[] image;

}
