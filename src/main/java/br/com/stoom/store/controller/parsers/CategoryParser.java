package br.com.stoom.store.controller.parsers;

import br.com.stoom.store.controller.dto.CategoryDTO;
import br.com.stoom.store.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryParser extends Parser<Category, CategoryDTO> {
    @Override
    public Category parse(final CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getName(), categoryDTO.getSkuCode());
    }

    @Override
    public CategoryDTO toDTO(final Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getSkuCode(), category.getActive());
    }
}
