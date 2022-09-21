package com.serenitybdd.easynotes.runners;

import com.serenitybdd.easynotes.stepdefinitions.BaseTest;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.annotations.UsePersistantStepLibraries;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@RunWith(CucumberWithSerenity.class)
@UsePersistantStepLibraries
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.serenitybdd.easynotes.stepdefinitions"},
        tags = "@smoke",
        plugin = {"pretty", "summary"}
)
public class CucumberTestsIT extends BaseTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public static final SpringMethodRule springMethodRule = new SpringMethodRule();
}
