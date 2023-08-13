package com.springbootapp.weatherapp.it.runners;

import org.junit.jupiter.api.Tag;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
@Tag("cucumber")
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/springbootapp/weatherapp/it")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.springbootapp.weatherapp.it")
public class CucumberIT {

}
