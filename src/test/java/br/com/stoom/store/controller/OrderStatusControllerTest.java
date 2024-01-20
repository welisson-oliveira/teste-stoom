package br.com.stoom.store.controller;

import br.com.stoom.store.AbstractTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderStatusControllerTest extends AbstractTestConfig {

    @Test
    void shouldGetAlOrderStatuses() throws Exception {
        this.mockMvc.perform(get("/order-statuses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"PENDING\",\"PROCESSING\",\"CONFIRMED\",\"SHIPPED\",\"DELIVERED\",\"CANCELLED\",\"RETURNED\",\"ON_HOLD\"]"));
    }


}
