package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBrandBO {
    Page<Brand> findAll(Pageable pageable);

    Brand create(Brand brand);

    Brand update(Long id, Brand brand);

    Brand activated(Long id, Boolean flag);

    Brand getByName(String brand);

    Page<Brand> findAllInactivated(Pageable pageable);
}
