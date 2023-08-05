Feature: Prueba del endpoint /municipios
  Scenario: Obtener lista de municipios exitosamente
    Given that the municipalities service is available
    When a GET request is made to "/muns"
    Then a successful response is received with a list of municipalities
  Scenario: Obtener lista de municipios cuando está vacía
    Given that the municipalities service is available
    When a GET request is made to "/muns"
    Then a response is received with status 404 Not Found