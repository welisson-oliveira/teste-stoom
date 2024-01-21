package br.com.stoom.store.controller.parsers;

import br.com.stoom.store.controller.dto.BrandDTO;
import br.com.stoom.store.model.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandParser extends Parser<Brand, BrandDTO> {
    @Override
    public Brand parse(final BrandDTO brandDTO) {
        return new Brand(this.validate(brandDTO.getName(), "Nome"), this.validate(brandDTO.getSkuCode(), "Código SKU"));
    }

    @Override
    public BrandDTO toDTO(final Brand brand) {
        return new BrandDTO(brand.getId(), brand.getName(), brand.getSkuCode(), brand.getActive());
    }
}
