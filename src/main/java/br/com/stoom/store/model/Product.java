package br.com.stoom.store.model;

import br.com.stoom.store.exceptions.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "PRODUCT_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "sku")
    private String sku;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long stockQuantity;

    @Column(nullable = false)
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product")
    private List<Image> images = new ArrayList<>();

    public Product(final String name, final Category category, final Brand brand, final BigDecimal price, final String description, final Long stockQuantity) {
        this.sku = category.getSkuCode() + "-" + brand.getSkuCode();
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.active = true;
    }

    public String getCategoryName() {
        return Objects.isNull(this.category) ? "" : this.category.getName();
    }

    public String getBrandName() {
        return Objects.isNull(this.brand) ? "" : this.brand.getName();
    }

    public void update(final Product product) {
        this.sku = product.getSku();
        this.name = product.getName();
        this.category = product.getCategory();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.stockQuantity = product.getStockQuantity();
    }

    public void activated(final Boolean flag) {
        this.active = flag;
    }

    public void addImage(final List<Image> images) {
        this.images.addAll(images);
    }

    public Image getImage(final Long image) {
        return this.images.stream().filter(img -> img.getId().equals(image)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Imagem Não encontrada"));
    }

    public void removeImage(final Long image) {
        this.images.remove(this.getImage(image));
    }
}