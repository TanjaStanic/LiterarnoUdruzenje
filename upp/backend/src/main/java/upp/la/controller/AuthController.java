package upp.la.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import upp.la.config.security.JwtTokenUtils;
import upp.la.error.ErrorMessages;
import upp.la.model.exceptions.AuthorizationError;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.auth.JwtAuthenticationRequest;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.repository.UserRepository;
import upp.la.service.internal.RegistrationServiceInt;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

  @Lazy @Autowired AuthenticationManager authenticationManager;

  @Autowired JwtTokenUtils jwtTokenUtils;

  @Autowired UserRepository userRepository;

  @Autowired RegistrationServiceInt registrationServiceInt;

  @PostMapping(path = "/login")
  public ResponseEntity<?> login(@RequestBody @Valid JwtAuthenticationRequest request)
      throws AuthorizationError {
    try {
      Authentication authenticate =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  request.getUsername(), request.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authenticate);

      Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

      User user = userRepository.findByUsername(loggedInUser.getName());

      return new ResponseEntity<>(
          jwtTokenUtils.generateToken(user.getUsername()), HttpStatus.ACCEPTED);
    } catch (Exception ex) {
      throw new AuthorizationError(ErrorMessages.AUTH_ERROR());
    }
  }

  @Validated
  @GetMapping(value = "/verify-account")
  public @ResponseBody ResponseEntity<?> verifyAccount(
      @RequestParam("token") @NotNull String confirmationToken) throws EntityNotFound {
    registrationServiceInt.verifyAccount(confirmationToken);

    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  // ONLY FOR AUTH TEST
  @GetMapping(value = "/test-register")
  public ResponseEntity<?> createNew() throws IOException {

    User test1 =
        new User(
            2L,
            "uname123",
            "123456",
            "name1",
            "lname2",
            "email@email.com",
            "asd",
            "asdf",
            true,
            null,
            null,
            Role.READER);
    registrationServiceInt.registerNew(test1);

    return new ResponseEntity<>("OKAY", HttpStatus.CREATED);
  }

  @GetMapping(value = "/getUserByName")
  public ResponseEntity<User> getByName(@RequestParam(value = "username", required = true) String username) {
    User user = userRepository.findUserByUsername(username);
    if(user != null) {
      System.out.println(user.getRole());
      return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }
}
