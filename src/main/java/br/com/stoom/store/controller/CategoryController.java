package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.controller.dto.CategoryDTO;
import br.com.stoom.store.controller.pageconverter.PageConverter;
import br.com.stoom.store.controller.parsers.Parser;
import br.com.stoom.store.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryBO categoryBO;
    private final Parser<Category, CategoryDTO> categoryParser;
    private final PageConverter<Category, CategoryDTO> pageConverter;

    @GetMapping
    public Page<CategoryDTO> findAll(final Pageable pageable) {
        return this.categoryBO.findAll(pageable).map(this.pageConverter);
    }

    @GetMapping("/inactivated")
    public Page<CategoryDTO> findAllInactivated(final Pageable pageable) {
        return this.categoryBO.findAllInactivated(pageable).map(this.pageConverter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO create(@RequestBody final CategoryDTO categoryDTO) {
        return this.categoryParser.toDTO(this.categoryBO.create(this.categoryParser.parse(categoryDTO)));
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable final Long id, @RequestBody final CategoryDTO categoryDTO) {
        return this.categoryParser.toDTO(this.categoryBO.update(id, this.categoryParser.parse(categoryDTO)));
    }

    @PatchMapping("/{id}/activated/{flag}")
    public CategoryDTO activated(@PathVariable final Long id, @PathVariable final Boolean flag) {
        return this.categoryParser.toDTO(this.categoryBO.activated(id, flag));
    }

}
