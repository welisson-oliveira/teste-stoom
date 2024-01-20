package br.com.stoom.store.config;

import br.com.stoom.store.controller.dto.BrandDTO;
import br.com.stoom.store.controller.dto.CategoryDTO;
import br.com.stoom.store.controller.dto.OrderDTO;
import br.com.stoom.store.controller.dto.ProductDTO;
import br.com.stoom.store.controller.parsers.Parser;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Order;
import br.com.stoom.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean
    public Map<Class, Parser> getParser(
            @Qualifier("productParser") final Parser<Product, ProductDTO> productParser,
            @Qualifier("categoryParser") final Parser<Category, CategoryDTO> categoryParser,
            @Qualifier("brandParser") final Parser<Brand, BrandDTO> brandParser,
            @Qualifier("orderParser") final Parser<Order, OrderDTO> orderParser
    ) {
        final Map<Class, Parser> parsers = new HashMap<>();

        parsers.put(Product.class, productParser);
        parsers.put(Category.class, categoryParser);
        parsers.put(Brand.class, brandParser);
        parsers.put(Order.class, orderParser);

        return parsers;
    }
}
