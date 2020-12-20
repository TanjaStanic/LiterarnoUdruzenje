package com.example.bankms.controller;

import java.util.List;

import com.example.bankms.domain.Client;
import com.example.bankms.repository.ClientRepository;
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

    @Autowired
    private ClientRepository clientRepository;
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<String> testRouting(){
        /*Client client1 = new Client("merchantid", "merchantpass", "test@gmail.com", "Pero Peric");
        Client client2 = new Client("merchantid2", "merchantpass2", "test2@gmail.com", "Marko Markovic");
        clientRepository.save(client1);
        clientRepository.save(client2);*/

        return ResponseEntity.ok().body("I am bank-ms");
    }

    @GetMapping("/test")
    public ResponseEntity<String> testBankAcquirer(){
        String fromBankAcq = restTemplate.getForObject("https://localhost:8445", String.class);
        System.out.println("Bank MS");
        fromBankAcq+=", BankAQ responded";
        return ResponseEntity.ok().body(fromBankAcq);
    }


    @GetMapping("/test/luservice")
    public ResponseEntity<String> test() {
        String response = restTemplate.getForObject("https://localhost:8447/testLuService", String.class);
        return ResponseEntity.ok(response + "  I am bank ms");

    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

}
