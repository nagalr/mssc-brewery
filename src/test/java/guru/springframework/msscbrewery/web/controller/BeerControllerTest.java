package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ronnen on 22-Jul-2021
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerDto validBeer;

    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Beer1")
                .beerStyle("ALE")
                .upc(32423243234L)
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // reset the mock after each test-method run
        reset(beerService);
    }

    @Test
    void getBeerTest() throws Exception {

        // given
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        // when
        mockMvc.perform(get("/api/v1/beer/" + validBeer.getId().toString())
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.beerName", is(validBeer.getBeerName())))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andExpect(status().isOk());

        // then
        then(beerService).should(times(1)).getBeerById(any(UUID.class));
    }

    @Test
    void handlePostTest() throws Exception {

        String beerDtoAsJson = objectMapper.writeValueAsString(validBeer);

        // given
        given(beerService.saveBeer(any(BeerDto.class))).willReturn(validBeer);

        // when
        mockMvc.perform(post("/api/v1/beer/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(beerDtoAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())));

        // then
        then(beerService).should(times(1)).saveBeer(any(BeerDto.class));
    }

    @Test
    void handleUpdateTest() throws Exception {

        String beerDtoAsJson = objectMapper.writeValueAsString(validBeer);

        // given (none - void)

        // when
        mockMvc.perform(put("/api/v1/beer/" + validBeer.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(beerDtoAsJson))
                    .andExpect(status().isNoContent());

        // then
        then(beerService).should(times(1)).updateBeer(any(BeerDto.class), any(UUID.class));
    }
}