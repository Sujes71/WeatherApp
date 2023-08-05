package com.springbootapp.weatherapp.mock;

import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.service.serviceimpl.AemetServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClimaAppTestsMock {

    @InjectMocks
    private AemetServiceImpl aemetService;
    @Mock
    private RestTemplate restTemplate;

    @Test
    void testGetNameMunById() {
        Municipality mockMun = new Municipality();
        mockMun.setName("Abajub");
        mockMun.setId("id44001");
        ResponseEntity<Municipality[]> responseEntity = new ResponseEntity<>(new Municipality[]{mockMun}, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(Municipality[].class)))
                .thenReturn(responseEntity);

        Municipality mun = aemetService.getMunById("id44001");

        assertEquals("Abajub", mun.getName());
    }
}
