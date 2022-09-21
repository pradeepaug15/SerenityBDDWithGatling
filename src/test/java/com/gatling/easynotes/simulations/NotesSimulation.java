package com.gatling.easynotes.simulations;

import com.gatling.easynotes.configs.Protocol;
import com.gatling.easynotes.notescenarios.NotesScenarios;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static com.gatling.easynotes.configs.GatlingTestConfig.DURATION_MIN;
import static com.gatling.easynotes.configs.GatlingTestConfig.REQUEST_PER_SECOND;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;

public class NotesSimulation extends Simulation {
    public NotesSimulation() {
        HttpProtocolBuilder httpProtocol = new Protocol().httpProtocol;
        ScenarioBuilder createNotes = new NotesScenarios().createNotes;
        ScenarioBuilder updateNotes = new NotesScenarios().updateNotes;
        ScenarioBuilder getNotes = new NotesScenarios().getNotes;
        ScenarioBuilder deleteNotes = new NotesScenarios().deleteNotes;

        this.setUp(

                        createNotes.injectOpen(rampUsers(REQUEST_PER_SECOND)
                                .during(Duration.ofSeconds(DURATION_MIN))),

                        updateNotes.injectOpen(rampUsers(REQUEST_PER_SECOND)
                                .during(Duration.ofSeconds(DURATION_MIN))),

                        getNotes.injectOpen(rampUsers(REQUEST_PER_SECOND)
                                .during(Duration.ofSeconds(DURATION_MIN))),

                        deleteNotes.injectOpen(rampUsers(REQUEST_PER_SECOND)
                                .during(Duration.ofSeconds(DURATION_MIN))))

                .protocols(httpProtocol)
                .assertions(global().failedRequests().count().is(0L));
    }
}
