package br.com.stoom.store.controller;

import br.com.stoom.store.AbstractTestConfig;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BrandControllerTest extends AbstractTestConfig {

    @Test
    @Sql("classpath:db/brand/init-values.sql")
    void shouldGetAllBrands() throws Exception {
        this.mockMvc.perform(get("/brands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/brand/all-active-brands.json")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateANewBrand() throws Exception {
        final String brand = this.readFileAsString("src/test/resources/files/input/brand/insert.json");

        this.mockMvc.perform(post("/brands")
                        .content(brand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/brand/insert-response.json")));
    }

    @Test
    @Sql("classpath:db/brand/init-values.sql")
    void shouldUpdateABrand() throws Exception {
        final String brand = this.readFileAsString("src/test/resources/files/input/brand/update.json");

        this.mockMvc.perform(put("/brands/1")
                        .content(brand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/brand/update-response.json")));
    }

    @Test
    @Sql("classpath:db/brand/init-values.sql")
    void shouldReactiveABrand() throws Exception {

        this.mockMvc.perform(patch("/brands/1/activated/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(false)));

        this.mockMvc.perform(patch("/brands/1/activated/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(true)));
    }

    @Test
    @Sql("classpath:db/brand/init-values.sql")
    void shouldInactiveABrand() throws Exception {
        this.mockMvc.perform(patch("/brands/1/activated/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(false)));
    }
}
