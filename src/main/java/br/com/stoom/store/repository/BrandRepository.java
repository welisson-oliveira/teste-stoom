package br.com.stoom.store.repository;

import br.com.stoom.store.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand getByName(String brand);

    Page<Brand> findAllByActiveTrue(Pageable pageable);
}
