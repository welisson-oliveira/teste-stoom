package br.com.stoom.store.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_sequence")
    @SequenceGenerator(name = "image_sequence", sequenceName = "IMAGE_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String path;

    private Long product;

    public String getAbsolutePath() {
        return this.path + "/" + this.name;
    }
}
