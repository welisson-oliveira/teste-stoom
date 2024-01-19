package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.exceptions.ResourceNotFoundException;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryBO implements ICategoryBO {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(final Pageable pageable) {
        return this.categoryRepository.findAllByActiveTrue(pageable);
    }

    @Override
    public Category create(final Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category update(final Long id, final Category category) {
        final Category categoryDB = this.getCategory(id);
        categoryDB.update(category);
        return this.categoryRepository.save(categoryDB);
    }

    @Override
    public Category activated(final Long id, final Boolean flag) {
        final Category categoryDB = this.getCategory(id);

        categoryDB.activated(flag);

        return this.categoryRepository.save(categoryDB);
    }

    private Category getCategory(final Long id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada!"));
    }
}
