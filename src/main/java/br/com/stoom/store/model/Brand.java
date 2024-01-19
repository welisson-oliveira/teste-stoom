package br.com.stoom.store.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "brand")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_sequence")
    @SequenceGenerator(name = "brand_sequence", sequenceName = "BRAND_SEQ")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String skuCode;

    @OneToMany(mappedBy = "brand")
    private final List<Product> products = new ArrayList<>();

    @Column(nullable = false)
    private Boolean active;

    public Brand(final String name, final String skuCode) {
        this.name = name;
        this.skuCode = skuCode;
        this.active = true;
    }

    public void update(final Brand brand) {
        this.name = brand.getName();
        this.skuCode = brand.getSkuCode();
    }

    public void activated(final Boolean flag) {
        this.active = flag;
    }
}
