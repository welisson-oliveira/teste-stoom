package br.com.stoom.store.repository;

import br.com.stoom.store.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> getByName(String category);

    Page<Category> findAllByActiveTrue(Pageable pageable);

    Page<Category> findAllByActiveFalse(Pageable pageable);
}
