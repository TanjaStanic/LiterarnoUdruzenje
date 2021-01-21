package upp.la.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.la.dto.FormFieldDto;
import upp.la.error.ErrorMessages;
import upp.la.model.User;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.RegistrationApplicationResponseRepository;
import upp.la.repository.UserRepository;
import upp.la.service.internal.ReviewServiceInt;


@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired ReviewServiceInt reviewService;
	@Autowired RegistrationApplicationResponseRepository regAppResponseRepository;
	@Autowired UserRepository userRepository;
	
	@PostMapping(path="/submit-review", consumes = "application/json")
	public ResponseEntity<?> submitReview(@RequestBody List<FormFieldDto> formFields) throws EntityNotFound {
		
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		User lecturer = userRepository.findByUsername(authenticate.getName());
		RegistrationApplicationResponse response = null;
		
		try {
			response = regAppResponseRepository.findOneByLecturer(lecturer);
		}
		catch (EntityNotFoundException e) {
			throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
		}
		
		reviewService.updateRegAppResponse(response, formFields);
			
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
