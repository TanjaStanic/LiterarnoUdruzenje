package upp.la.controller;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
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
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.service.internal.ReviewServiceInt;


@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired private RuntimeService runtimeService;
	@Autowired TaskService taskService;
	@Autowired ReviewServiceInt reviewService;
	
	@PostMapping(path="/submit-review", consumes = "application/json")
	public ResponseEntity<?> submitReview(@RequestBody List<FormFieldDto> formFields) throws EntityNotFound {
		
		RegistrationApplicationResponse response = reviewService.makeRegistrationApplicationResponse(formFields);
		
		
		List<Task> tasks = taskService.createTaskQuery().taskName("ReviewWriter").list();
	    Task task = tasks.get(0);

	    String processInstanceId = task.getProcessInstanceId();
		
		runtimeService.setVariable(processInstanceId,
                "registration_application",
                response.getRegistrationApplication());
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
