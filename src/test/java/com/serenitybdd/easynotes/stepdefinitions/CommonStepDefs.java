package com.serenitybdd.easynotes.stepdefinitions;

import com.serenitybdd.easynotes.testcontext.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CommonStepDefs extends BaseTest {
    @Steps(shared = true)
    protected TestContext testContext;

    @Given("the request headers are valid")
    public void validRequestHeaders() {
        super.validRequestHeaders();
    }

    @Then("I verify the status code as {int}")
    public void verifyStatusCode(Integer statusCode) {
        testContext.getCurResponse().getBody().prettyPrint();
        assertThat(testContext.getCurResponse().getStatusCode(), equalTo(statusCode));
    }

}
