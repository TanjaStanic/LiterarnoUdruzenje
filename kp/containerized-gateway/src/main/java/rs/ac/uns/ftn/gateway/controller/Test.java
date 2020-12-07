package rs.ac.uns.ftn.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<String> test(){
        String response = restTemplate.getForObject("https://localhost:8447/testLuService", String.class);
        return ResponseEntity.ok( response + "I am gateway");
    }

}
