package com.example.spring_6_rest_mvc.controller;

import com.example.spring_6_rest_mvc.config.SpringSecurityConfig;
import com.example.spring_6_rest_mvc.dto.BeerDTO;
import com.example.spring_6_rest_mvc.exception.NotFoundException;
import com.example.spring_6_rest_mvc.model.BeerStyle;
import com.example.spring_6_rest_mvc.service.BeerService;
import com.example.spring_6_rest_mvc.service.impl.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.example.spring_6_rest_mvc.controller.BeerControllerTestIT.jwtRequestPostProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
@Import(SpringSecurityConfig.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    String username= "user1";
    String password= "password";

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    public static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRequestPostProcessor =
            jwt().jwt(jwt -> {
                jwt.claims(claims -> {
                            claims.put("scope", "message-read");
                            claims.put("scope", "message-write");
                        })
                        .subject("messaging-client")
                        .notBefore(Instant.now().minusSeconds(5l));
            });

    @Test
    void testCreateNewBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(
                null, null, false, 1, 25)
                .getContent().get(0);

        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers
                (null, null, false, 1, 25)
                .getContent().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
//                        .with(httpBasic(username, password))
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }


    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(
                null, null, false, 1, 25)
                .getContent().get(0);

        beer.setBeerStyle(BeerStyle.PORTER);

        given(beerService.updateBeer(any(), any())).willReturn(Optional.of(beer));

//        put(BeerController.BEER_PATH+ "/" +beer.getId()) or put(BeerController.BEER_PATH_ID, beer.getId())
        mockMvc.perform(put(BeerController.BEER_PATH+ "/" +beer.getId())
//                        .with(httpBasic(username, password))
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeer(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false, 1, 25)
                .getContent().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Beer Name");

//        patch(BeerController.BEER_PATH+ "/" +beer.getId()) or patch(BeerController.BEER_PATH_ID, beer.getId())
        mockMvc.perform(patch(BeerController.BEER_PATH+ "/" +beer.getId())
//                        .with(httpBasic(username, password))
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false, 1, 25)
                .getContent().get(0);

        given(beerService.deleteBeer(any())).willReturn(true);

//        delete(BeerController.BEER_PATH+ "/" +beer.getId()) or delete(BeerController.BEER_PATH_ID, beer.getId())
        mockMvc.perform(delete(BeerController.BEER_PATH+ "/" +beer.getId())
//                        .with(httpBasic(username, password))
                        .with(jwtRequestPostProcessor)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //capture arguments
        verify(beerService).deleteBeer(uuidArgumentCaptor.capture());


        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void getBeerById() throws Exception {
        BeerDTO testBeer = beerServiceImpl.listBeers(
                null, null, false, 1, 25)
                .getContent().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(get(BeerController.BEER_PATH+ "/" +testBeer.getId().toString())
//                        .with(httpBasic(username, password))
                        .with(jwtRequestPostProcessor)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void listBeers() throws Exception {
        given(beerService.listBeers(null, null, null, null, null)).willReturn(
                beerServiceImpl.listBeers(null, null, false, 1, 25));

        mockMvc.perform(get(BeerController.BEER_PATH)
//                        .with(httpBasic(username, password))
                        .with(jwtRequestPostProcessor)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(3)));
    }

    @Test
    void getBeerByIdNotFound() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(BeerController.BEER_BY_ID, UUID.randomUUID()).with(jwtRequestPostProcessor))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {

        BeerDTO beerDTO = BeerDTO.builder().build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(
                beerServiceImpl.listBeers(null, null, false, 1, 25)
                        .getContent().get(1));

        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
//                        .with(httpBasic(username, password))
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(6)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateBeerBlankName() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false, 1, 25)
                .getContent().get(0);
        beer.setBeerName("");
        given(beerService.updateBeer(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH+ "/" +beer.getId())
//                        .with(httpBasic(username, password))
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)));

    }
}