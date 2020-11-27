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

    @GetMapping("/hello")
    public ResponseEntity<String> test(){
        System.out.println("Pogodio");
        return ResponseEntity.ok().body("Hello to you too!");
        
    }
    @GetMapping("/testGateway")
    public ResponseEntity<String> testComunication(){
        
        restTemplate = new RestTemplate();
        String fromLuService = restTemplate.getForObject("http://localhost:8447/testLuService", String.class);
		System.out.println(fromLuService);
		fromLuService+=", Gateway Test-done";
        return ResponseEntity.ok().body(fromLuService);
    }

}
