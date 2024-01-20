package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.controller.dto.ImageDTO;
import br.com.stoom.store.controller.dto.ProductDTO;
import br.com.stoom.store.controller.pageconverter.PageConverter;
import br.com.stoom.store.controller.parsers.ImageParser;
import br.com.stoom.store.controller.parsers.Parser;
import br.com.stoom.store.exceptions.FileException;
import br.com.stoom.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductBO productBO;
    private final Parser<Product, ProductDTO> productParser;
    private final ImageParser imageParser;
    private final PageConverter<Product, ProductDTO> pageConverter;

    @GetMapping
    public Page<ProductDTO> findAll(final Pageable pageable) {
        return this.productBO.findAll(pageable).map(this.pageConverter);
    }

    @GetMapping("/inactivated")
    public Page<ProductDTO> findAllInactivated(final Pageable pageable) {
        return this.productBO.findAllInactivated(pageable).map(this.pageConverter);
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable final Long id) {
        return this.productParser.toDTO(this.productBO.getProduct(id));
    }

    @GetMapping("/brands/name/{name}")
    public Page<ProductDTO> findAllByBrand(@PathVariable final String name, final Pageable pageable) {
        return this.productBO.findAllByBrand(name, pageable).map(this.pageConverter);
    }

    @GetMapping("/categories/name/{name}")
    public Page<ProductDTO> findAllByCategory(@PathVariable final String name, final Pageable pageable) {
        return this.productBO.findAllByCategory(name, pageable).map(this.pageConverter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody final ProductDTO productDTO) {
        return this.productParser.toDTO(this.productBO.create(this.productParser.parse(productDTO)));
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable final Long id, @RequestBody final ProductDTO productDTO) {
        return this.productParser.toDTO(this.productBO.update(id, this.productParser.parse(productDTO)));
    }

    @PatchMapping("/{id}/activated/{flag}")
    public ProductDTO activated(@PathVariable final Long id, @PathVariable final Boolean flag) {
        return this.productParser.toDTO(this.productBO.activated(id, flag));
    }

    @PostMapping("/{id}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadImages(@PathVariable final Long id, @RequestParam final List<MultipartFile> files) {
        this.productBO.uploadImages(id, files);
    }

    @GetMapping("/{id}/images")
    public List<ImageDTO> getImages(@PathVariable final Long id) {
        return this.productBO.getImages(id).stream().map(image -> {
            try {
                return this.imageParser.toDTO(image);
            } catch (final IOException e) {
                throw new FileException(e.getMessage());
            }
        }).collect(Collectors.toList());


    }

    @DeleteMapping("/{id}/images/{image}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable final Long id, @PathVariable final Long image) {
        this.productBO.deleteImage(id, image);
    }
}
