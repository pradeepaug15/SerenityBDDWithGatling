package com.serenitybdd.easynotes.stepdefinitions;

import com.serenitybdd.easynotes.testcontext.TestContext;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Steps;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
public class BaseTest {
    private static final String CONTENT_TYPE = "Content-Type";

    @Steps(shared = true)
    protected TestContext testContext;

    public ResponseOptions<Response> processGetRequest() {
        testContext.getFilterableRequestSpecification().log().uri();

        ResponseOptions<Response> curResponse = null;
        curResponse = testContext.getFilterableRequestSpecification().get(testContext.getCurEndpoint());
        testContext.setCurResponse(curResponse);
        return curResponse;
    }

    public ResponseOptions<Response> processGetRequest(String endpoint) {
        ResponseOptions<Response> curResponse = null;

        try {
            testContext.getFilterableRequestSpecification().log().uri();

            curResponse = testContext.getFilterableRequestSpecification().get(new URI(testContext.getBaseURI()) + endpoint);
        } catch (URISyntaxException e) {
            log.info("URI Syntax Exception occurred for ---> Reason ---> " + e.getMessage());
        }
        testContext.setCurResponse(curResponse);
        return curResponse;
    }

    public ResponseOptions<Response> processGetRequestWithPathRamaters(String endpoint, Map<String, String> pathPamaters) {

        ResponseOptions<Response> curResponse = null;

        curResponse = testContext.getFilterableRequestSpecification().get(endpoint, pathPamaters);
        testContext.setCurResponse(curResponse);

        return curResponse;
    }

    public ResponseOptions<Response> processPostRequest() {
        testContext.getFilterableRequestSpecification().log().headers();
        testContext.getFilterableRequestSpecification().log().uri();
        testContext.getFilterableRequestSpecification().log().body();

        ResponseOptions<Response> curResponse = null;
        curResponse = testContext.getFilterableRequestSpecification().post(testContext.getCurEndpoint());
        testContext.setCurResponse(curResponse);
        return curResponse;
    }

    public ResponseOptions<Response> processPostRequest(String endpoint) {
        testContext.getFilterableRequestSpecification().log().headers();
        testContext.getFilterableRequestSpecification().log().uri();
        testContext.getFilterableRequestSpecification().log().body();

        ResponseOptions<Response> curResponse = null;
        curResponse = testContext.getFilterableRequestSpecification().post(endpoint);
        testContext.setCurResponse(curResponse);
        return curResponse;
    }

    public ResponseOptions<Response> processPutRequest() {
        testContext.getFilterableRequestSpecification().log().headers();
        testContext.getFilterableRequestSpecification().log().uri();
        testContext.getFilterableRequestSpecification().log().body();

        ResponseOptions<Response> curResponse = null;
        curResponse = testContext.getFilterableRequestSpecification().put(testContext.getCurEndpoint());
        testContext.setCurResponse(curResponse);
        return curResponse;
    }

    public ResponseOptions<Response> processPutRequest(String endpoint) {
        testContext.getFilterableRequestSpecification().log().headers();
        testContext.getFilterableRequestSpecification().log().uri();
        testContext.getFilterableRequestSpecification().log().body();

        ResponseOptions<Response> curResponse = null;
        curResponse = testContext.getFilterableRequestSpecification().put(endpoint);
        testContext.setCurResponse(curResponse);
        return curResponse;
    }

    public ResponseOptions<Response> processDeleteRequest(String endpoint) {
        testContext.getFilterableRequestSpecification().log().headers();
        testContext.getFilterableRequestSpecification().log().uri();

        ResponseOptions<Response> curResponse = null;
        curResponse = testContext.getFilterableRequestSpecification().delete(endpoint);
        testContext.setCurResponse(curResponse);
        return curResponse;
    }

    public ResponseOptions<Response> processDeleteRequest() {
        testContext.getFilterableRequestSpecification().log().headers();
        testContext.getFilterableRequestSpecification().log().uri();

        ResponseOptions<Response> curResponse = null;
        curResponse = testContext.getFilterableRequestSpecification().delete(testContext.getCurEndpoint());
        testContext.setCurResponse(curResponse);
        return curResponse;
    }

    public void validRequestHeaders() {
        String contentType = "application/json";
        setRequestHeaders(contentType);
    }

    private void setRequestHeaders(String contentType) {
        if (contentType.isEmpty()) {
            testContext.getFilterableRequestSpecification().headers("Content-Type", contentType);
        }
    }

    public void setEndpointAs(String endpoint) {
        testContext.setCurEndpoint(endpoint);
        try {
            testContext.setCompleteURL(new URI(testContext.getBaseURI() + testContext.getCurEndpoint()));
        } catch (URISyntaxException e) {
            log.info("URI is not set appropriately ---->>  " + e.getReason());
        }
    }

}
