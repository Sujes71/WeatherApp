Feature: Testing of the /aemet/mun/all endpoint
  Scenario: Successfully obtain list of municipalities
    Given that the municipalities service is available
    When a GET request is made to "/aemet/mun/all"
    Then a successful response is received with a list of municipalities
  Scenario: Get list of municipalities when empty
    Given that the municipalities service is available
    When a GET request is made to "/aemet/mun/all"
    Then a response is received with status 404 Not Found