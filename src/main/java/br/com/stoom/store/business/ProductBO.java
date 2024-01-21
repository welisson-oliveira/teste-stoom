package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.exceptions.FileException;
import br.com.stoom.store.exceptions.ResourceNotFoundException;
import br.com.stoom.store.model.Image;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductBO implements IProductBO {

    private final ProductRepository productRepository;
    private final ImageManager imageManager;

    @Value("${file.dir}")
    private String directory;

    @Override
    public Page<Product> findAll(final Pageable pageable) {
        return this.productRepository.findAllByActiveTrue(pageable);
    }

    @Override
    public Page<Product> findAllInactivated(final Pageable pageable) {
        return this.productRepository.findAllByActiveFalse(pageable);
    }

    @Override
    public Product create(final Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product update(final Long id, final Product product) {
        final Product productDB = this.getProduct(id);
        productDB.update(product);

        return this.productRepository.save(productDB);
    }

    @Override
    public Product activated(final Long id, final Boolean flag) {
        final Product product = this.getProduct(id);
        product.activated(flag);
        return this.productRepository.save(product);
    }

    @Override
    public Page<Product> findAllByBrand(final String name, final Pageable pageable) {
        return this.productRepository.findByBrandName(name, pageable);
    }

    @Override
    public Page<Product> findAllByCategory(final String name, final Pageable pageable) {
        return this.productRepository.findAllByCategory(name, pageable);
    }


    @Override
    public void uploadImages(final Long id, final List<MultipartFile> files) {

        final Product product = this.getProduct(id);
        final List<Image> images = new ArrayList<>();

        files.forEach(file -> {
            try {
                images.add(new Image(null, this.imageManager.upload(this.directory, file), this.directory, id));
            } catch (final IOException e) {
                throw new FileException("Erro ao tentar fazer upload da imagem " + e.getMessage());
            }
        });
        product.addImage(images);
        this.productRepository.save(product);
    }

    @Override
    public List<Image> getImages(final Long id) {
        final Product product = this.getProduct(id);

        return product.getImages();
    }

    @Override
    @Transactional
    public void deleteImage(final Long id, final Long image) {
        final Product product = this.getProduct(id);
        this.imageManager.delete(product.getImage(image));
        product.removeImage(image);

        this.productRepository.save(product);
    }


    @Override
    public Product getProduct(final Long id) {
        return this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));
    }

}
