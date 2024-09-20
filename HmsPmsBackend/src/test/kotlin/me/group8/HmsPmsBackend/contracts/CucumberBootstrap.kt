package me.group8.HmsPmsBackend.contracts

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.*

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("me.group8.HmsPmsBackend.contracts")
@ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "me.group8.HmsPmsBackend.contracts"
)
class CucumberBootstrap {
}