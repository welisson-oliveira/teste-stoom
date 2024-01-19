package br.com.stoom.store.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "CATEGORY_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String skuCode;

    @OneToMany(mappedBy = "category")
    private final List<Product> products = new ArrayList<>();

    @Column(nullable = false)
    private Boolean active;

    public Category(final String name, final String skuCode) {
        this.name = name;
        this.skuCode = skuCode;
        this.active = true;
    }

    public void update(final Category category) {
        this.name = category.getName();
        this.skuCode = category.getSkuCode();
    }

    public void activated(final Boolean flag) {
        this.active = flag;
    }
}
