package br.com.stoom.store.controller.parsers;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.controller.dto.ProductDTO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductParser extends Parser<Product, ProductDTO> {

    private final ICategoryBO categoryBO;
    private final IBrandBO brandBO;
    private final ImageParser imageParser;

    @Override
    public Product parse(final ProductDTO product) {
        final Category category = this.categoryBO.getByName(product.getCategory());
        final Brand brand = this.brandBO.getByName(product.getBrand());

        return new Product(this.validate(product.getName(), "Nome"), category, brand, product.getPrice(), this.validate(product.getDescription(), "Descrição"), product.getStockQuantity());
    }

    @Override
    public ProductDTO toDTO(final Product product) {
        return new ProductDTO(product.getId(), product.getSku(), product.getName(), product.getCategoryName(), product.getBrandName(),
                product.getPrice(), product.getDescription(), product.getStockQuantity(), product.getActive(), this.imageParser.toDTOs(product.getImages()));
    }
}
