package com.fujitsu.fujitsutrialtask.controller;


import com.fujitsu.fujitsutrialtask.service.DeliveryFeeService;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.DeliveryFeeException;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.WeatherConditionException;
import org.hibernate.QueryParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(DeliveryFeeController.class)
class RestControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    DeliveryFeeService deliveryFeeService;
    private final int badRequest = HttpStatus.BAD_REQUEST.value();
    private final int ok = HttpStatus.OK.value();
    @Test
    void testGetDeliveryFeeNoTimestamp() throws Exception {
        given(deliveryFeeService.getDeliveryFee("tallinn", "scooter", null))
                .willReturn(3.5f);

        MockHttpServletResponse response = mvc.perform(
                        get("/api/deliveryfee?city=tallinn&vehicle=scooter")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(ok, response.getStatus());
        Assertions.assertEquals("3.5", response.getContentAsString());
    }

    @Test
    void testGetDeliveryFeeWithTimestamp() throws Exception {
        given(deliveryFeeService.getDeliveryFee("tallinn", "scooter", "1711124333"))
                .willReturn(4.5f);

        MockHttpServletResponse response = mvc.perform(
                        get("/api/deliveryfee?city=tallinn&vehicle=scooter&timestamp=1711124333")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(ok, response.getStatus());
        Assertions.assertEquals("4.5", response.getContentAsString());
    }

    @Test
    void testDeliveryFeeException() throws Exception {
        given(deliveryFeeService.getDeliveryFee("tallinn", "scooter", "1"))
                .willThrow(DeliveryFeeException.class);

        MockHttpServletResponse response = mvc.perform(
                        get("/api/deliveryfee?city=tallinn&vehicle=scooter&timestamp=1")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(badRequest, response.getStatus());
    }

    @Test
    void testWeatherConditionException() throws Exception {
        given(deliveryFeeService.getDeliveryFee("tallinn", "scooter", "2"))
                .willThrow(WeatherConditionException.class);

        MockHttpServletResponse response = mvc.perform(
                        get("/api/deliveryfee?city=tallinn&vehicle=scooter&timestamp=2")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(badRequest, response.getStatus());
    }

    @Test
    void testQueryParameterException() throws Exception {
        given(deliveryFeeService.getDeliveryFee("paris", "tractor", null))
                .willThrow(QueryParameterException.class);

        MockHttpServletResponse response = mvc.perform(
                        get("/api/deliveryfee?city=paris&vehicle=tractor")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(badRequest, response.getStatus());
    }
}
