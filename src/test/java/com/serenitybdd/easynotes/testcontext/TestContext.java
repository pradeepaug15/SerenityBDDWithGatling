package com.serenitybdd.easynotes.testcontext;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Data
public class TestContext {
    private Properties properties = new Properties();
    private String baseURI;
    private String curEndpoint;
    private String curNoteId;
    private URI completeURL;
    private FilterableRequestSpecification filterableRequestSpecification;
    private ResponseOptions<Response> curResponse;
    private Map<String, String> pathParameters;

   public TestContext() throws IOException{

        InputStream inputStream = null;
        RequestSpecBuilder specBuilder = new RequestSpecBuilder();
        try{
            inputStream = new FileInputStream("src/test/resources/testConfig/config.properties");
            properties.load(inputStream);
        } catch (IOException e){
            log.info(e.getMessage());
        } finally {
            if (inputStream != null){
                inputStream.close();
            }
        }

        baseURI = properties.getProperty("baseURI");
        specBuilder.setBaseUri(baseURI);
        specBuilder.setContentType(ContentType.JSON);
        RequestSpecification requestSpecification = specBuilder.build();
        filterableRequestSpecification = (FilterableRequestSpecification) SerenityRest.given().spec(requestSpecification);
        pathParameters = new HashMap<>();
    }

    public void addPathParameters(String pathParameterName, String pathParameterValue){
        pathParameters.put(pathParameterName, pathParameterValue);
    }
}
