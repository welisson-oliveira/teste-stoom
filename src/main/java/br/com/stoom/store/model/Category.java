package br.com.stoom.store.model;

import br.com.stoom.store.exceptions.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        this.name = this.validate(name, "Nome");
        this.skuCode = this.validate(skuCode, "Código SKU");
        this.active = true;
    }

    private String validate(final String txt, final String propertyName) {
        if (Objects.isNull(txt) || txt.trim().isEmpty()) {
            throw new BadRequestException("Informe o " + propertyName);
        }
        return txt.trim();
    }

    public void update(final Category category) {
        this.name = this.validate(category.getName(), "Nome");
        this.skuCode = this.validate(category.getSkuCode(), "Código SKU");
    }

    public void activated(final Boolean flag) {
        this.active = flag;
    }
}
