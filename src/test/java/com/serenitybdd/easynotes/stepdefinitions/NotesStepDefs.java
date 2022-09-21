package com.serenitybdd.easynotes.stepdefinitions;

import com.serenitybdd.easynotes.model.Notes;
import com.serenitybdd.easynotes.testcontext.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import lombok.Data;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.RandomStringUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;

@Data
public class NotesStepDefs extends BaseTest {
    @Steps(shared = true)
    protected TestContext testContext;

    protected Notes curNotesRequest;
    protected Notes curNotesResponse;
    protected static String savedId;
    protected String savedTitle;
    protected String savedContent;


    @When("I GET all notes")
    public void getAllNotes() {
        setEndpointAs(testContext.getProperties().getProperty("GET_Endpoint"));
        testContext.setCurResponse(processGetRequest());
    }

    @Then("I validate the body of the GET All response")
    public void validateGetAllNotesResponseBody() {
        JsonPath jsonPathEvaluator = testContext.getCurResponse().getBody().jsonPath();
        List<Notes> curNote = jsonPathEvaluator.getList("", Notes.class);
        assertThat(curNote.get(0).getTitle(), equalTo("My first note"));
        assertThat(curNote.size(), greaterThan(0));

        for (int i = 0; i < curNote.size(); i++) {
            assertThat(curNote.get(0).getId(), equalTo("1"));
            assertThat(curNote.get(0).getTitle(), equalTo("My first note"));
            assertThat(curNote.get(0).getContent(), equalTo("My first content"));

            assertThat(curNote.get(1).getId(), equalTo("2"));
            assertThat(curNote.get(1).getTitle(), equalTo("My Second note"));
            assertThat(curNote.get(1).getContent(), equalTo("My Second content"));

        }
    }

    @When("^I create a new note with (.*) and (.*)$")
    public void createNewNote(String title, String content) {
        createValidNotes(title, content);
        setEndpointAs(testContext.getProperties().getProperty("POST_Endpoint"));
        super.processPostRequest();
        JsonPath jsonPathEvaluator = testContext.getCurResponse().getBody().jsonPath();
        curNotesResponse = jsonPathEvaluator.getObject("", Notes.class);
        testContext.setCurNoteId(curNotesResponse.getId());
        savedId = curNotesResponse.getId();
        testContext.setCurEndpoint(testContext.getCurEndpoint() + "/" + curNotesResponse.getId());
    }

    @Then("I validate the body of the newly created note response")
    public void validateNewlyCreatedNoteResponseBody() {
        assertThat(curNotesResponse.getId(), greaterThan("0"));
        assertThat(curNotesResponse.getTitle(), equalTo(savedTitle));
        assertThat(curNotesResponse.getContent(), equalTo(savedContent));
    }

    @Then("I GET the newly created note")
    public void getNewlyCreatedNote() {

        setEndpointAs(testContext.getProperties().getProperty("GET_Endpoint") + "/" + savedId);
        testContext.setCurResponse(processGetRequest());
        JsonPath jsonPathEvaluator = testContext.getCurResponse().getBody().jsonPath();
        curNotesResponse = jsonPathEvaluator.getObject("", Notes.class);
        assertThat(curNotesResponse.getId(), equalTo(savedId));
        assertThat(curNotesResponse.getTitle(), equalTo(savedTitle));
        assertThat(curNotesResponse.getContent(), equalTo(savedContent));

    }

    @Then("I validate the body of the GET response")
    public void validateGetResponseBody() {
        JsonPath jsonPathEvaluator = testContext.getCurResponse().getBody().jsonPath();
        curNotesResponse = jsonPathEvaluator.getObject("", Notes.class);

        assertThat(curNotesResponse.getId(), greaterThan("0"));
        assertThat(curNotesResponse.getTitle(), equalTo(curNotesRequest.getTitle()));
        assertThat(curNotesResponse.getContent(), equalTo(curNotesRequest.getContent()));
    }


    @Then("^I Update the note with (.*) to (.*)$")
    public void updateNote(String toBeUpdated, Integer attributeLength) {
        Notes curNotesNewRequest = new Notes();
        String generatedString = null;
        if (attributeLength >= 0) {
            generatedString = RandomStringUtils.random(attributeLength, true, true);
        }
        if (testContext.getCurResponse() != null) {
            JsonPath jsonPathEvaluator = testContext.getCurResponse().getBody().jsonPath();
            curNotesNewRequest = jsonPathEvaluator.getObject("", Notes.class);
            setEndpointAs(testContext.getProperties().getProperty("PUT_Endpoint") + curNotesNewRequest.getId());
        }
        switch (toBeUpdated) {
            case "title":
                curNotesNewRequest.setTitle(generatedString);
                break;
            case "content":
                curNotesNewRequest.setContent(generatedString);
                break;
            default:
        }
        testContext.getFilterableRequestSpecification().body(curNotesNewRequest);
        setCurNotesRequest(curNotesNewRequest);
        testContext.setCurResponse(super.processPutRequest());
    }

    @Then("I validate the Notes for the updated (.*)$")
    public void validateGetResponseBody(String updatedAttribute) {

        JsonPath jsonPathEvaluator = testContext.getCurResponse().getBody().jsonPath();
        curNotesResponse = jsonPathEvaluator.getObject("", Notes.class);

        switch (updatedAttribute) {
            case "title":
                assertThat(curNotesResponse.getTitle(), equalTo(curNotesRequest.getTitle()));
                break;
            case "content":
                assertThat(curNotesResponse.getContent(), equalTo(curNotesRequest.getContent()));
                break;
            default:
        }
    }

    @Then("I Delete the newly created note")
    public void deleteNote() {
        JsonPath jsonPathEvaluator = testContext.getCurResponse().getBody().jsonPath();
        curNotesResponse = jsonPathEvaluator.getObject("", Notes.class);
        setEndpointAs(testContext.getProperties().getProperty("DELETE_Endpoint") + curNotesResponse.getId());
        testContext.setCurResponse(super.processDeleteRequest());

    }

    private void createValidNotes(String title, String content) {

        curNotesRequest = new Notes();

        if (title.equalsIgnoreCase("Random Title")) {
            savedTitle = "New_" + RandomStringUtils.random(10, true, true);
        }
        if (content.equalsIgnoreCase("Random Content")) {
            savedContent = "New_" + RandomStringUtils.random(10, true, true);
        }

        curNotesRequest.setTitle(savedTitle);
        curNotesRequest.setContent(savedContent);
        testContext.getFilterableRequestSpecification().body(curNotesRequest);
    }
}
