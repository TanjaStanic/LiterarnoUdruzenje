package com.example.bankacquirer.controller;

import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.dto.ClientDto;
import com.example.bankacquirer.dto.NewClientDto;
import com.example.bankacquirer.repository.ClientRepository;
import com.example.bankacquirer.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;
    
    private ClientRepository clientRepository;

    

    public ClientController(ClientService clientService, ClientRepository clientRepository) {
		super();
		this.clientService = clientService;
		this.clientRepository = clientRepository;
	}

	@PostMapping
    public ResponseEntity<?> registerNewClient(@RequestBody NewClientDto newClientDto) {
        Client client = null;
        try {
            client = clientService.insert(newClientDto);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Email already in use.");
        }
        client = clientService.saveClient(client);
        ClientDto clientDto = new ClientDto(client.getMerchantID(), client.getMerchantPassword(), client.getEmail(), client.getName());
        return ResponseEntity.ok(clientDto);
    }
    
    @GetMapping("/deleteClient/{clientEmail}")
    public ResponseEntity<?> deleteClient(@PathVariable String clientEmail){
    	try {
    		Client client = clientRepository.findByEmail(clientEmail);
        	clientRepository.delete(client);

    	} catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("Cant find or delete client with email: " + clientEmail);
    	}
    	
    	return ResponseEntity.ok(null);
    }
}
