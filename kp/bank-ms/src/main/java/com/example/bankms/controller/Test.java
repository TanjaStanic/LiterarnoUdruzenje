package com.example.bankms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test {
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<String> testRouting(){
        return ResponseEntity.ok().body("I am bank-ms");
    }

    @GetMapping("/helloBank")
    public ResponseEntity<String> test(){
        System.out.println("Pogodio banku");
        return ResponseEntity.ok().body("Hello from bank mc!");
    }
    @GetMapping("/testBankMS")
    public ResponseEntity<String> testComunication(){
        
    	 restTemplate = new RestTemplate();
         String fromLuService = restTemplate.getForObject("http://localhost:8447/testLuService", String.class);
 		 System.out.println(fromLuService);
 		 fromLuService+=", Gateway Test-done";
         return ResponseEntity.ok().body(fromLuService);
    }
   
    @RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}


}
