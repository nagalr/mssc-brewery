package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ronnen on 22-Jul-2021
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    CustomerDto validCustomer;

    @BeforeEach
    void setUp() {
        validCustomer = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("The Customer")
                .build();
    }

    @AfterEach
    void tearDown() {
        reset(customerService);
    }

    @Test
    void getCustomerByIdTest() throws Exception {

        // given
        given(customerService.getCustomerById(any(UUID.class))).willReturn(validCustomer);

        // when
        mockMvc.perform(get("/api/v1/customer/" + validCustomer.getId().toString()))
                .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(validCustomer.getName())))
                .andExpect(status().isOk());

        // then
        then(customerService).should(times(1)).getCustomerById(any(UUID.class));
    }

    @Test
    void handlePostTest() throws Exception {

        String customerAsJson = objectMapper.writeValueAsString(validCustomer);

        // given
        given(customerService.saveCustomer(any(CustomerDto.class))).willReturn(validCustomer);

        // when
        mockMvc.perform(post("/api/v1/customer/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(customerAsJson))
                .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(validCustomer.getName())))
                .andExpect(status().isOk());

        // then
        then(customerService).should(times(1)).saveCustomer(any());
    }

    @Test
    void handlePutTest() throws Exception{

        String customerDtoAsJson = objectMapper.writeValueAsString(validCustomer);

        // given
        given(customerService.updateCustomer(any(UUID.class), any(CustomerDto.class))).willReturn(validCustomer);

        // when
        mockMvc.perform(put("/api/v1/customer/" + validCustomer.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(customerDtoAsJson))
                .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(validCustomer.getName())))
                .andExpect(status().isCreated());

        // then
        then(customerService).should(times(1)).updateCustomer(any(), any());
    }

    @Test
    void handleDeleteTest() throws Exception {
        // given - none, void

        // when
        mockMvc.perform(delete("/api/v1/customer/" + validCustomer.getId().toString()))
                        .andExpect(status().isNoContent());

        // then
        then(customerService).should(times(1)).deleteCustomer(any());
    }
}