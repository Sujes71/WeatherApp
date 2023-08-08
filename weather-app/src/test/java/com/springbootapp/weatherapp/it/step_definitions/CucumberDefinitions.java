package com.springbootapp.weatherapp.it.step_definitions;

import com.springbootapp.weatherapp.controller.AuthController;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.service.component.JwtTokenUtil;
import com.springbootapp.weatherapp.service.serviceimpl.AemetServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CucumberDefinitions {
    @InjectMocks
    private AuthController authController;
    @Mock
    private JwtTokenUtil mockJwtTokenUtil;
    @Mock
    private Jwts mockJwts;
    @Mock
    private RestTemplate mockRestTemplate;
    @InjectMocks
    private AemetServiceImpl aemetService;

    private ResponseEntity<Municipality[]> response;
    private HttpStatusCode statusCode;
    private HttpEntity<?> requestEntity;
    private HttpHeaders headers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Given("that the municipalities service is available")
    public void that_the_municipalities_service_is_available() {
        String code = "jjefjnejf123434jn";
        String tokenGenerated = "simulacion_token_generado_de_code";

        when(mockJwtTokenUtil.generateRandomCode()).thenReturn(code);
        when(mockJwts.builder()
                .setSubject(code)
                .setIssuedAt(any())
                .setExpiration(any())
                .signWith(SignatureAlgorithm.HS512, anyString().getBytes())
                .compact()).thenReturn(tokenGenerated);

        String controlCode = String.valueOf(this.authController.getRandomCode());
        String controlToken = String.valueOf(this.authController.getToken(controlCode));

        assertEquals(controlCode, code);
        assertEquals(controlToken, tokenGenerated);

        headers = new HttpHeaders();
        headers.setBearerAuth(tokenGenerated);
        requestEntity = new HttpEntity<>(headers);
    }

    @When("a GET request is made to {string}")
    public void a_GET_request_is_made_to(String endpoint) {
        String url = String.format("http://localhost:8080%s", endpoint);
        Municipality mun1 = new Municipality();
        mun1.setId("id44001");
        mun1.setName("Ababuj");

        Municipality mun2 = new Municipality();
        mun2.setId("id23432");
        mun2.setName("Marbella");

        ResponseEntity<Municipality[]> responseEntity =
                new ResponseEntity<>(new Municipality[]{mun1, mun2}, HttpStatus.OK);
        when(mockRestTemplate.exchange(
                url,
                eq(HttpMethod.GET),
                requestEntity,
                eq(Municipality[].class)))
                .thenReturn(responseEntity);
        statusCode = responseEntity.getStatusCode();

        String name = this.aemetService.getMuns().get(1).getName();
        assertEquals("Marbella", name);
        assertEquals(2, responseEntity.getBody().length);
    }

    @Then("a successful response is received with a list of municipalities")
    public void a_successful_response_is_received_with_a_list_of_municipalities() {
        assertEquals(HttpStatus.OK, statusCode);
        assertNotNull(response);
        assertNotEquals(0, response.getBody().length);
    }

    @Then("a response is received with status {int} Not Found")
    public void a_response_is_received_with_status_not_found(Integer notFound) {
        assertNotEquals(this.statusCode.value(), (int) notFound);
    }
}
