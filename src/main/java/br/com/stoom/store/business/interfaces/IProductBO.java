package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Image;
import br.com.stoom.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductBO {

    Page<Product> findAll(Pageable pageable);

    Product create(Product product);

    Product update(Long id, Product product);

    Product activated(Long id, Boolean flag);

    Page<Product> findAllByBrand(final String name, final Pageable pageable);

    Page<Product> findAllByCategory(String name, Pageable pageable);

    void uploadImages(Long id, List<MultipartFile> files);

    List<Image> getImages(Long id);

    void deleteImage(Long id, Long image);
}
