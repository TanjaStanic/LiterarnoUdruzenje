package com.example.bankms.controller;

import com.example.bankms.domain.Client;
import com.example.bankms.dto.RegisterClientDTO;
import com.example.bankms.service.ClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/register-url/{email}/{clientId}")
    public @ResponseBody
    ResponseEntity<String> getRegistrationUrl(@PathVariable String email, @PathVariable String clientId) {
        return ResponseEntity.ok(MessageFormat.format("https://localhost:8441/register/{0}/{1}", email, clientId));
    }

    @GetMapping("/register/{email}/{clientId}")
    public String registerForm(Model model, @PathVariable String email, @PathVariable String clientId) {
        RegisterClientDTO registerClientDTO = new RegisterClientDTO();
        registerClientDTO.setEmail(email);
        registerClientDTO.setPcClientId(clientId);
        model.addAttribute("registrationDTO", registerClientDTO);
        return "registration";
    }

    @PostMapping("/register")
    public Object register(@Valid @ModelAttribute("registrationDTO") RegisterClientDTO registerClientDTO, Model model) {
        if (clientService.insert(
                new Client(registerClientDTO.getEmail(), registerClientDTO.getMerchantID(), registerClientDTO.getMerchantPassword(),
                        Long.parseLong(registerClientDTO.getPcClientId()))) != null) {

            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", MessageFormat.format("https://localhost:8762/api/pc_info/view/select-payment-methods/{0}", registerClientDTO.getPcClientId()));
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

        } else {
            model.addAttribute("registrationDTO", registerClientDTO);
            model.addAttribute("error", "Something went wrong, please try again.");
            return "registration";

        }

    }

}
