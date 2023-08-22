package com.rigoberto.console;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

public abstract class KeycloakTestContainer {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    protected String authServerUri;
    @Value("${keycloak.client-id}")
    protected String clientId;

    static final KeycloakContainer keycloakContainer;

    static {
        keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:20.0.3").withRealmImportFile("keycloak/config/console6p1-realm.json");
        keycloakContainer.start();
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloakContainer.getAuthServerUrl() + "/realms/console6p1");
        registry.add("keycloak.auth-server-url", keycloakContainer::getAuthServerUrl);
    }

    protected String accessToken;

    protected String getBearerToken(String userName, String password) throws URISyntaxException {
        if(accessToken != null) {
            return accessToken;
        }

        URI authorizationURI = new URIBuilder(authServerUri + "/protocol/openid-connect/token").build();
        WebClient webclient = WebClient.builder().build();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("grant_type", Collections.singletonList("password"));
        formData.put("client_id", Collections.singletonList(clientId));
        formData.put("username", Collections.singletonList(userName));
        formData.put("password", Collections.singletonList(password));

        String result = webclient.post()
                .uri(authorizationURI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JacksonJsonParser jsonParser = new JacksonJsonParser();

        accessToken = "Bearer " + jsonParser.parseMap(result).get("access_token").toString();

        return accessToken;
    }
}
