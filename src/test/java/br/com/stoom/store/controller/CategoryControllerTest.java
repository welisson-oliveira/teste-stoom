package br.com.stoom.store.controller;

import br.com.stoom.store.AbstractTestConfig;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest extends AbstractTestConfig {

    @Test
    @Sql("classpath:db/category/init-values.sql")
    void shouldGetAllCategories() throws Exception {
        this.mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/category/all-active-categories.json")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/category/init-values.sql")
    void shouldGetAllInactiveCategories() throws Exception {
        this.mockMvc.perform(get("/categories/inactivated")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()", CoreMatchers.equalTo(1)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateANewCategory() throws Exception {
        final String category = this.readFileAsString("src/test/resources/files/input/category/insert.json");

        this.mockMvc.perform(post("/categories")
                        .content(category)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/category/insert-response.json")));
    }

    @Test
    @Sql("classpath:db/category/init-values.sql")
    void shouldUpdateACategory() throws Exception {
        final String category = this.readFileAsString("src/test/resources/files/input/category/update.json");

        this.mockMvc.perform(put("/categories/1")
                        .content(category)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/category/update-response.json")));
    }

    @Test
    @Sql("classpath:db/category/init-values.sql")
    void shouldReactiveACategory() throws Exception {

        this.mockMvc.perform(patch("/categories/1/activated/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(false)));

        this.mockMvc.perform(patch("/categories/1/activated/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(true)));
    }

    @Test
    @Sql("classpath:db/category/init-values.sql")
    void shouldInactiveACategory() throws Exception {
        this.mockMvc.perform(patch("/categories/1/activated/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", CoreMatchers.equalTo(false)));
    }
}
