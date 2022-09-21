package com.gatling.easynotes.configs;

import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static com.gatling.easynotes.configs.GatlingTestConfig.*;

public class Protocol {
    public HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl(BASE_URL)
            .acceptHeader(JSON)
            .userAgentHeader(USER_AGENT);
}
