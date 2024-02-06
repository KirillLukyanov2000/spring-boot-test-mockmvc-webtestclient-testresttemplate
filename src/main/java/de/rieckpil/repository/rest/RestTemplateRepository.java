package de.rieckpil.repository.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Repository
public class RestTemplateRepository {
  @Autowired
  private RestTemplate restTemplate;

  public UserData fetchUserDataById(Long id) {
    ResponseEntity<UserData> responseEntity = restTemplate.getForEntity("http://localhost:8080/users/" + id, UserData.class);
    return responseEntity.getBody();
  }
  public UserData fetchUserDataById2(Long id) {
    return restTemplate.getForObject("http://localhost:8080/users/" + id, UserData.class);
  }

  public UserData fetchUserDataById3(Long id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.set("X-COM-PERSIST", "NO");
    headers.set("X-COM-LOCATION", "USA");

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ResponseEntity<UserData> responseEntity = restTemplate.exchange("http://localhost:8080/users/" + id, HttpMethod.GET, requestEntity, UserData.class);
    if(!responseEntity.getStatusCode().is2xxSuccessful()){
      throw new RuntimeException("Something went wrong");
    }

    return responseEntity.getBody();
  }

  public void updateUserData(UserData userData) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.set("X-COM-PERSIST", "NO");
    headers.set("X-COM-LOCATION", "USA");

    HttpEntity<UserData> requestUpdate = new HttpEntity<>(userData, headers);

    ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8080/users/" + userData.getId(), HttpMethod.PUT, requestUpdate, Void.class);
    if(!responseEntity.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Something went wrong");
    }
  }

}
