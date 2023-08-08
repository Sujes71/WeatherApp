package com.springbootapp.weatherapp.it.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"html:target/cucumber-report.html",
                "json:target/cucumber-reports/cucumber.json"},
        features = "src/test/resources/features",
        glue = "com/springbootapp/weatherapp/it/step_definitions",
        dryRun = false, // true means that do not run my step definitions but only check if any step is missing snippet.
        tags = ""
)
public class CucumberIntegrationTest {

}
