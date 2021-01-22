package upp.la.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upp.la.model.User;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.RegistrationApplicationResponseRepository;
import upp.la.repository.UserRepository;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/rap", produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed({"LECTURER"})
public class RegistrationApplicationResponseController {

    @Autowired
    RegistrationApplicationResponseRepository registrationApplicationResponseRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/getByUsername")
    public ResponseEntity<List<RegistrationApplicationResponse>> allForLector(@RequestParam("username") String username) {
        User user = userRepository.findUserByUsername(username);
        System.out.println(registrationApplicationResponseRepository.findAll());
        return new ResponseEntity<>(registrationApplicationResponseRepository.findAll(), HttpStatus.OK);
    }
}
