package br.com.stoom.store.controller;

import br.com.stoom.store.AbstractTestConfig;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest extends AbstractTestConfig {

    @Test
    @Sql("classpath:db/product/init-values.sql")
    void shouldGetAllProducts() throws Exception {
        this.mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/product/get-all-response.json")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/product/init-many-products.sql")
    void shouldReturnTheFirstPageOfProducts() throws Exception {
        this.mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("size", "3")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/product/get-all-paging.json")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/product/init-many-categories.sql")
    void shouldReturnAllFromACategory() throws Exception {

        this.mockMvc.perform(get("/products/categories/name/CAMISA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/product/many-categories-response.json"), true))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/product/init-many-brands.sql")
    void shouldReturnAllFromABrand() throws Exception {

        this.mockMvc.perform(get("/products/brands/name/PUMA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/product/many-brands-response.json"), true))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/product/init-values.sql")
    void shouldCreateANewProduct() throws Exception {
        final String product = this.readFileAsString("src/test/resources/files/input/product/insert.json");

        this.mockMvc.perform(post("/products")
                        .content(product)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/product/insert-response.json"), true));
    }

    @Test
    @Sql("classpath:db/product/init-values.sql")
    void shouldUpdateAProduct() throws Exception {
        final String product = this.readFileAsString("src/test/resources/files/input/product/update.json");

        this.mockMvc.perform(put("/products/1")
                        .content(product)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/product/update-response.json"), true));
    }

    @Test
    @Sql("classpath:db/product/init-values.sql")
    void shouldReactiveAProduct() throws Exception {

        this.mockMvc.perform(patch("/products/1/activated/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(false)));

        this.mockMvc.perform(patch("/products/1/activated/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(true)));
    }

    @Test
    @Sql("classpath:db/product/init-values.sql")
    void shouldInactiveAProduct() throws Exception {
        this.mockMvc.perform(patch("/products/1/activated/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(false)));
    }

    @Test
    @Sql("classpath:db/product/init-values.sql")
    void productNotFound() throws Exception {
        this.mockMvc.perform(patch("/products/10/inactivated/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("classpath:db/product/init-values.sql")
    void shouldUploadImages() throws Exception {
        final ClassPathResource spring = new ClassPathResource("/images/spring.png");
        final ClassPathResource spring1 = new ClassPathResource("/images/spring-1.png");
        final ClassPathResource spring2 = new ClassPathResource("/images/spring-2.png");


        final MockMultipartFile file = new MockMultipartFile("files", spring.getFilename(), MediaType.IMAGE_PNG_VALUE, Files.readAllBytes(spring.getFile().toPath()));
        final MockMultipartFile file1 = new MockMultipartFile("files", spring1.getFilename(), MediaType.IMAGE_PNG_VALUE, Files.readAllBytes(spring1.getFile().toPath()));
        final MockMultipartFile file2 = new MockMultipartFile("files", spring2.getFilename(), MediaType.IMAGE_PNG_VALUE, Files.readAllBytes(spring2.getFile().toPath()));


        this.mockMvc.perform(multipart("/products/1/images")
                        .file(file)
                        .file(file1)
                        .file(file2)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/products/1/images"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$[1].id", CoreMatchers.equalTo(2)))
                .andExpect(jsonPath("$[2].id", CoreMatchers.equalTo(3)))
                .andExpect(jsonPath("$[0].path", CoreMatchers.equalTo("src/test/resources/images/uploaded")))
                .andExpect(jsonPath("$[1].path", CoreMatchers.equalTo("src/test/resources/images/uploaded")))
                .andExpect(jsonPath("$[2].path", CoreMatchers.equalTo("src/test/resources/images/uploaded")))
                .andExpect(jsonPath("$[0].product", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$[1].product", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$[2].product", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$[0].image", CoreMatchers.equalTo(this.readFileAsString("src/test/resources/files/output/product/uploaded-file-1.txt"))))
                .andExpect(jsonPath("$[1].image", CoreMatchers.equalTo(this.readFileAsString("src/test/resources/files/output/product/uploaded-file-2.txt"))))
                .andExpect(jsonPath("$[2].image", CoreMatchers.equalTo(this.readFileAsString("src/test/resources/files/output/product/uploaded-file-3.txt"))));
    }

    @Test
    @Sql("classpath:db/product/init-values.sql")
    void shouldDeleteImage() throws Exception {
        final ClassPathResource spring = new ClassPathResource("/images/spring.png");


        final MockMultipartFile file = new MockMultipartFile("files", spring.getFilename(), MediaType.IMAGE_PNG_VALUE, Files.readAllBytes(spring.getFile().toPath()));


        this.mockMvc.perform(multipart("/products/1/images")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/products/1/images"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.equalTo(1)));

        this.mockMvc.perform(delete("/products/1/images/1"))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/products/1/images"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}
