package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryBO {
    Page<Category> findAll(Pageable pageable);

    Category create(Category parse);

    Category update(Long id, Category parse);

    Category activated(Long id, Boolean flag);


    Category getByName(String category);
}
