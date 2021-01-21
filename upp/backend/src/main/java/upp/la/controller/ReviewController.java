package upp.la.controller;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
	@Autowired private FormService formService;
	@Autowired private TaskService taskService;
	
	@PostMapping(path="/submit-review", consumes = "application/json")
	public ResponseEntity<?> submitReview(@RequestBody List<FormFieldDto> formFields) throws EntityNotFound {
		
		User lecturer = null;
		try {
			lecturer = userRepository.findByUsername("lecturer456");
		}
		catch (EntityNotFoundException e) {
			throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
		}
		
		RegistrationApplicationResponse response = null;
		
		try {
			response = regAppResponseRepository.findOneByLecturer(lecturer);
		}
		catch (EntityNotFoundException e) {
			throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
		}
		reviewService.updateRegAppResponse(response, formFields);
			
		
		List<Task> tasks = taskService.createTaskQuery().taskName("ReviewWriter").list();
	    Task task = tasks.get(2);
	    HashMap<String, Object> map = mapListToDto(formFields);
		try {
			formService.submitTaskForm(task.getId(), map);
		}
		catch (Exception e){
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private HashMap<String, Object> mapListToDto(List<FormFieldDto> list) {
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    for (FormFieldDto temp : list) {
	      map.put(temp.getFieldId(), temp.getFieldValue());
	    }

	    return map;
	  }
	
}
