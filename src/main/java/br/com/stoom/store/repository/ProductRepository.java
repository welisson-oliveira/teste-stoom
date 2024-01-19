package br.com.stoom.store.repository;

import br.com.stoom.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT product FROM Product product JOIN product.brand brand WHERE brand.name = :name AND product.active = true")
    Page<Product> findByBrandName(String name, Pageable pageable);

    @Query("SELECT product FROM Product product JOIN product.category category WHERE category.name = :name AND product.active = true")
    Page<Product> findAllByCategory(String name, Pageable pageable);

    Page<Product> findAllByActiveTrue(Pageable pageable);
}