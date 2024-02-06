package de.rieckpil.repository.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class WebClientRepository {

  @Autowired
  private WebClient webClient;

  public UserData fetchUserDataById(Long id) {
    return webClient
      .get()
      .uri("http://localhost:8080/users/" + id)
      .retrieve()
      .bodyToMono(UserData.class)
      .block();
  }

  public UserData fetchUserDataById2(Long id) {
    ResponseEntity<UserData> responseEntity = webClient
      .get()
      .uri("http://localhost:8080/users/" + id)
      .retrieve()
      .toEntity(UserData.class)
      .block();

    if(!responseEntity.getStatusCode().is2xxSuccessful()){
      throw new RuntimeException("Something went wrong");
    }

    List<String> customHeaders = responseEntity.getHeaders().get("MY_CUSTOM_HEADER");
    if(customHeaders.isEmpty()) {
      throw new RuntimeException("Something went wrong");
    }

    return responseEntity.getBody();
  }

  public UserData fetchUserDataById3(Long id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.set("X-COM-PERSIST", "NO");
    headers.set("X-COM-LOCATION", "USA");

    Optional<UserData> userData = webClient
      .get()
      .uri("http://localhost:8080/users/" + id)
      .headers(httpHeaders -> httpHeaders.addAll(headers))
      .retrieve()
      .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
      .bodyToMono(UserData.class)
      .blockOptional(Duration.of(1, ChronoUnit.SECONDS));

    return userData.orElseThrow(RuntimeException::new);
  }

  public void updateUserData(UserData userData) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.set("X-COM-PERSIST", "NO");
    headers.set("X-COM-LOCATION", "USA");

    ResponseEntity<Void> response = webClient
      .put()
      .body(userData, UserData.class)
      .headers(httpHeaders -> httpHeaders.addAll(headers))
      .retrieve()
      .toBodilessEntity()
      .block();

    if(!response.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Something went wrong");
    }
  }
}
