package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.controller.dto.BrandDTO;
import br.com.stoom.store.controller.pageconverter.PageConverter;
import br.com.stoom.store.controller.parsers.Parser;
import br.com.stoom.store.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandBO brandBO;
    private final Parser<Brand, BrandDTO> brandParser;
    private final PageConverter<Brand, BrandDTO> pageConverter;

    @GetMapping
    public Page<BrandDTO> findAll(final Pageable pageable) {
        return this.brandBO.findAll(pageable).map(this.pageConverter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandDTO create(@RequestBody final BrandDTO brandDTO) {
        return this.brandParser.toDTO(this.brandBO.create(this.brandParser.parse(brandDTO)));
    }

    @PutMapping("/{id}")
    public BrandDTO update(@PathVariable final Long id, @RequestBody final BrandDTO brandDTO) {
        return this.brandParser.toDTO(this.brandBO.update(id, this.brandParser.parse(brandDTO)));
    }

    @PatchMapping("/{id}/activated/{flag}")
    public BrandDTO activated(@PathVariable final Long id, @PathVariable final Boolean flag) {
        return this.brandParser.toDTO(this.brandBO.activated(id, flag));
    }
}
