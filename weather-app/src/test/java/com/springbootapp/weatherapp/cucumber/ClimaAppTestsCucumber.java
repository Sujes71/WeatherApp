package com.springbootapp.weatherapp.cucumber;

import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.service.component.JwtTokenUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ClimaAppTestsCucumber {
    private String secret;
    private Long expiration;
    private RestTemplate restTemplate;
    private JwtTokenUtil jwtTokenUtil;
    private ResponseEntity<Municipality[]> response;
    private HttpStatusCode statusCode;
    private HttpEntity<?> requestEntity;

    @Given("that the municipalities service is available")
    public void that_the_municipalities_service_is_available() {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("src/test/resources/test.properties")) {
            prop.load(input);
            secret = prop.getProperty("jwt.secret");
            expiration = Long.parseLong(prop.getProperty("jwt.expiration"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        restTemplate = new RestTemplate();
        jwtTokenUtil = new JwtTokenUtil();

        jwtTokenUtil.setSecret(secret);
        jwtTokenUtil.setExpiration(expiration);

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(jwtTokenUtil.generateToken(jwtTokenUtil.generateRandomCode()));
        requestEntity = new HttpEntity<>(headers);
    }

    @When("a GET request is made to {string}")
    public void a_GET_request_is_made_to(String endpoint) {
        String url = String.format("http://localhost:8080%s", endpoint);
        response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Municipality[].class);
        statusCode = response.getStatusCode();
    }

    @Then("a successful response is received with a list of municipalities")
    public void a_successful_response_is_received_with_a_list_of_municipalities() {
        assertEquals(HttpStatus.OK, statusCode);
        assertNotNull(response.getBody());
        assertNotEquals(response.getBody().length, 0);
    }

    @Then("a response is received with status {int} Not Found")
    public void a_response_is_received_with_status_not_found(Integer notFound) {
        assertNotEquals(this.statusCode.value(), (int) notFound);
    }
}
