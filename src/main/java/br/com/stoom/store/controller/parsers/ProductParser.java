package br.com.stoom.store.controller.parsers;

import br.com.stoom.store.controller.dto.ProductDTO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductParser extends Parser<Product, ProductDTO> {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ImageParser imageParser;

    @Override
    public Product parse(final ProductDTO product) {
        final Category category = this.categoryRepository.getByName(product.getCategory());
        final Brand brand = this.brandRepository.getByName(product.getBrand());

        return new Product(product.getName(), category, brand, product.getPrice(), product.getDescription(), product.getStockQuantity());
    }

    @Override
    public ProductDTO toDTO(final Product product) {
        return new ProductDTO(product.getId(), product.getSku(), product.getName(), product.getCategoryName(), product.getBrandName(),
                product.getPrice(), product.getDescription(), product.getStockQuantity(), product.getActive(), this.imageParser.toDTOs(product.getImages()));
    }
}
