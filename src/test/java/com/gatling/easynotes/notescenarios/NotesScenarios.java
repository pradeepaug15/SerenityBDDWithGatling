package com.gatling.easynotes.notescenarios;

import io.cucumber.java.hu.De;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.gatling.easynotes.configs.GatlingTestConfig.CONTENT_TYPE;
import static com.gatling.easynotes.configs.GatlingTestConfig.JSON;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class NotesScenarios {
    private String GET_NOTES = "api/notes";
    private String POST_NOTES = "api/notes/";
    private String PUT_NOTES = "api/notes/";
    private String DELETE_NOTES = "api/notes/";

    private Iterator<Map<String, Object>> feeder =
            Stream.generate((Supplier<Map<String, Object>>) ()
                    -> Collections.singletonMap("title", UUID.randomUUID().toString())).iterator();

    public ScenarioBuilder createNotes = CoreDsl.scenario("Load Test Creating notes")
            .feed(feeder)
            .exec(http("create-Notes-request")
                    .post(POST_NOTES)
                    .header(CONTENT_TYPE, JSON)
                    .body(StringBody("{ \"title\": \"${title}\" , \"content\": \"Gatling test\"}"))
                    .check(status().is(200))
                    .check(jsonPath("$.id").ofString().exists())
                    .check(jsonPath("$.title").ofString().exists())
                    .check(jsonPath("$.content").ofString().is("Gatling test"))
                    .check(jsonPath("$.createdAt").ofString().exists())
                    .check(jsonPath("$.updatedAt").ofString().exists())
            );

    public ScenarioBuilder updateNotes = CoreDsl.scenario("Load Test update notes")
            .during(10).on(
                    feed(Stream.generate((Supplier<Map<String, Object>>) ()
                            -> Collections.singletonMap("id", new Random().nextInt(20))).iterator()).feed(feeder)

                            .exec(http("update-note-request")
                                    .get(PUT_NOTES + "/${id}")
                                    .header(CONTENT_TYPE, JSON)
                                    .body(StringBody("{ \"title\": \"${title}\" , \"content\": \"Gatling Updated test\"}"))
                                    .check(status().is(200))
                                    .check(jsonPath("$.id").ofString().exists())
                                    .check(jsonPath("$.title").ofString().exists())
                                    .check(jsonPath("$.content").ofString().exists())
                                    .check(jsonPath("$.createdAt").ofString().exists())
                                    .check(jsonPath("$.updatedAt").ofString().exists()))
            );

    public ScenarioBuilder getNotes = CoreDsl.scenario("Load Test get notes")
            .during(10).on(
                    feed(Stream.generate((Supplier<Map<String, Object>>) ()
                            -> Collections.singletonMap("id", new Random().nextInt(20))).iterator())

                            .exec(http("get-note-request")
                                    .get(GET_NOTES + "/${id}")
                                    .header(CONTENT_TYPE, JSON)
                                    .check(status().is(200))
                                    .check(jsonPath("$.id").ofString().exists())
                                    .check(jsonPath("$.title").ofString().exists())
                                    .check(jsonPath("$.content").ofString().exists())
                                    .check(jsonPath("$.createdAt").ofString().exists())
                                    .check(jsonPath("$.updatedAt").ofString().exists()))
            );

    public ScenarioBuilder deleteNotes = CoreDsl.scenario("Load Test delete notes")
            .during(10).on(
                    feed(Stream.generate((Supplier<Map<String, Object>>) ()
                            -> Collections.singletonMap("id", new Random().nextInt(20))).iterator())

                            .exec(http("delete-note-request")
                                    .get(DELETE_NOTES + "/${id}")
                                    .header(CONTENT_TYPE, JSON)
                                    .check(status().is(200)))
            );
}
