package com.gatling.easynotes.configs;

public class GatlingTestConfig {
        public static final String BASE_URL = "http://localhost:8080/";
        public static final int REQUEST_PER_SECOND = 20;
        public static final long DURATION_MIN = 1;

        public static final String CONTENT_TYPE = "Content-Type";
        public static final String JSON = "application/json";
        public static final String USER_AGENT = "Gatling/Performance Test";
}
