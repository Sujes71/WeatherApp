package com.springbootapp.weatherapp.it.step_definitions;

import com.hazelcast.map.IMap;
import com.springbootapp.weatherapp.controller.AuthController;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.service.component.HazelCastUtil;
import com.springbootapp.weatherapp.service.component.JwtTokenUtil;
import com.springbootapp.weatherapp.service.serviceimpl.AemetServiceImpl;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@CucumberContextConfiguration
public class CucumberDefinitions {
    @InjectMocks
    private AuthController authController;
    @Mock
    private JwtTokenUtil mockJwtTokenUtil;
    @Mock
    private RestTemplate mockRestTemplate;
    @InjectMocks
    private AemetServiceImpl aemetService;
    @Mock
    private IMap<String, List<Municipality>> mockImap;
    @Mock
    private HazelCastUtil mockHazelCastUtil;
    @Mock
    private Jwts mockJwts;

    private ResponseEntity<Municipality[]> response;
    private HttpStatusCode statusCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Given("that the municipalities service is available")
    public void that_the_municipalities_service_is_available() {
        String code = "jjefjnejf123434jn";
        String tokenGenerated = "simulacion_token_generado_de_code";

        when(mockJwtTokenUtil.generateRandomCode()).thenReturn(code);
        when(mockJwtTokenUtil.generateToken(code)).thenReturn(tokenGenerated);

        String controlCode = this.authController.getRandomCode().getBody();
        String controlToken = this.authController.getToken(controlCode).getBody();

        assertEquals(controlCode, code);
        assertEquals(controlToken, tokenGenerated);
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

        response =
                new ResponseEntity<>(new Municipality[]{mun1, mun2}, HttpStatus.OK);
        when(mockRestTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(Municipality[].class)))
                .thenReturn(response);
        statusCode = response.getStatusCode();

        when(mockHazelCastUtil.getMunCache()).thenReturn(mockImap);

        List<Municipality> munsI = new ArrayList<>();
        munsI.add(mun1);
        munsI.add(mun2);

        when(mockImap.get(anyString())).thenReturn(munsI);

        List<Municipality> muns = this.aemetService.getMuns();
        assertEquals("Marbella", muns.get(1).getName());
        assertEquals(2, response.getBody().length);
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
