package rs.ac.uns.ftn.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigurationController {

    @Value("${message:Hello default}")
    private String message;

    @GetMapping("message")
    public ResponseEntity<String> getMessage(){
        return ResponseEntity.ok().body(this.message);
    }
}
