package br.com.stoom.store.controller;

import br.com.stoom.store.AbstractTestConfig;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest extends AbstractTestConfig {

    @Test
    @Sql("classpath:db/order/init-values.sql")
    void shouldGetAllOrders() throws Exception {
        this.mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/order/get-all-response.json"), true))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/init-values.sql")
    void shouldGetOrderById() throws Exception {
        this.mockMvc.perform(get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/order/get-by-id.json"), true))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/init-values.sql")
    void shouldCreateANewOrder() throws Exception {
        final String order = this.readFileAsString("src/test/resources/files/input/order/insert.json");

        this.mockMvc.perform(post("/orders")
                        .content(order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/order/insert-response.json")));

    }

    @Test
    @Sql("classpath:db/order/pending-status.sql")
    void shouldChangeStatusFromPendingToProcessing() throws Exception {
        this.mockMvc.perform(patch("/orders/1/status/PROCESSING")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("PROCESSING")))
                .andExpect(jsonPath("$.previousStatus", CoreMatchers.equalTo("PENDING")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/processing-status.sql")
    void shouldChangeStatusFromProcessingToConfirmed() throws Exception {
        this.mockMvc.perform(patch("/orders/1/status/CONFIRMED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("CONFIRMED")))
                .andExpect(jsonPath("$.previousStatus", CoreMatchers.equalTo("PROCESSING")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/confirmed-status.sql")
    void shouldChangeStatusFromConfirmedToShipped() throws Exception {
        this.mockMvc.perform(patch("/orders/1/status/SHIPPED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("SHIPPED")))
                .andExpect(jsonPath("$.previousStatus", CoreMatchers.equalTo("CONFIRMED")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/shipped-status.sql")
    void shouldChangeStatusFromShippedToDelivered() throws Exception {
        this.mockMvc.perform(patch("/orders/1/status/DELIVERED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("DELIVERED")))
                .andExpect(jsonPath("$.previousStatus", CoreMatchers.equalTo("SHIPPED")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/delivered-status.sql")
    void shouldChangeStatusFromDeliveredToReturned() throws Exception {
        this.mockMvc.perform(patch("/orders/1/status/RETURNED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("RETURNED")))
                .andExpect(jsonPath("$.previousStatus", CoreMatchers.equalTo("DELIVERED")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/delivered-status.sql")
    void shouldNotChangeStatusFromDeliveredToCanceled() throws Exception {
        this.mockMvc.perform(patch("/orders/1/status/CANCELLED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"errorCode\":400,\"message\":\"não pode mudar para o estado de DELIVERED para CANCELLED\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("classpath:db/order/delivered-status.sql")
    void shouldCancelACreatedOrder() throws Exception {

        final String order = this.readFileAsString("src/test/resources/files/input/order/insert.json");

        this.mockMvc.perform(post("/orders")
                        .content(order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(this.readFileAsString("src/test/resources/files/output/order/insert-response.json")));

        this.mockMvc.perform(get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stockQuantity", CoreMatchers.equalTo(8)))
                .andExpect(status().isOk());

        this.mockMvc.perform(patch("/orders/2/status/CANCELLED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("CANCELLED")))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stockQuantity", CoreMatchers.equalTo(10)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/pending-status.sql")
    void shouldChangeStatusFromPendingToOnHoldAndBack() throws Exception {
        this.mockMvc.perform(patch("/orders/1/status/ON_HOLD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("ON_HOLD")))
                .andExpect(jsonPath("$.previousStatus", CoreMatchers.equalTo("PENDING")))
                .andExpect(status().isOk());

        this.mockMvc.perform(patch("/orders/1/status/PENDING")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("PENDING")))
                .andExpect(jsonPath("$.previousStatus", CoreMatchers.equalTo("ON_HOLD")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:db/order/pending-status.sql")
    void shouldNotChangeStatusFromOnHoldToProcessing() throws Exception {
        this.mockMvc.perform(patch("/orders/1/status/ON_HOLD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", CoreMatchers.equalTo("ON_HOLD")))
                .andExpect(jsonPath("$.previousStatus", CoreMatchers.equalTo("PENDING")))
                .andExpect(status().isOk());

        this.mockMvc.perform(patch("/orders/1/status/PROCESSING")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"errorCode\":400,\"message\":\"não pode mudar para o estado de ON_HOLD para PROCESSING antes volte para PENDING\"}"))
                .andExpect(status().isBadRequest());
    }

}
