package upp.la.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import upp.la.dto.FormFieldDto;
import upp.la.dto.FormFieldsDto;
import upp.la.error.ErrorMessages;
import upp.la.model.User;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.exceptions.ValidationError;
import upp.la.model.registration.ApplicationResponse;
import upp.la.model.registration.RegistrationApplication;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.RegistrationApplicationResponseRepository;
import upp.la.repository.UserRepository;
import upp.la.service.SubmitPaymentService;
import upp.la.service.internal.ReviewServiceInt;


@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired ReviewServiceInt reviewService;
	@Autowired RegistrationApplicationResponseRepository regAppResponseRepository;
	@Autowired UserRepository userRepository;
	@Autowired RegistrationApplicationRepository registrationApplicationRepository;
	@Autowired private FormService formService;
	@Autowired private TaskService taskService;
	@Autowired private RuntimeService runtimeService;
	@Autowired private SubmitPaymentService submitPaymentService;

	@RolesAllowed({"LECTURER", "EDITOR", "ADMIN"})
	@PostMapping(path="/submit-review", consumes = "application/json")
	public ResponseEntity<?> submitReview(@RequestBody List<FormFieldDto> formFields,
										  @RequestParam("username") String username,
										  @RequestParam("taskId") String taskId) throws EntityNotFound {
		
		User lecturer = null;
		System.out.println(username);
		try {
			lecturer = userRepository.findByUsername(username);
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
			
	    HashMap<String, Object> map = mapListToDto(formFields);
		try {
			formService.submitTaskForm(taskId, map);
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

	@RolesAllowed({"LECTURER", "EDITOR", "ADMIN"})
	@GetMapping(path="/get-review-flag")
	public ResponseEntity<Boolean> flag(@RequestParam("username") String username) {
		boolean f = true;
		User user = userRepository.findUserByUsername(username);
		RegistrationApplication ra = registrationApplicationRepository.findOneByWriter(user);
		if(ra == null) {
			f = false;
		}
		return new ResponseEntity<>(f, HttpStatus.OK);
	}

	@RolesAllowed({"WRITER", "LECTURER", "EDITOR", "ADMIN"})
	@GetMapping(path = "/getMoreDocumentsField", produces = "application/json")
	public @ResponseBody
	FormFieldsDto getFields() {

		List<Task> tasks = taskService.createTaskQuery().taskName("Send more documents").list();
		if(!tasks.isEmpty()) {
			Task task = tasks.get(0);
			TaskFormData tfd = formService.getTaskFormData(task.getId());
			List<FormField> properties = tfd.getFormFields();
			return new FormFieldsDto(task.getId(), "456", properties);
		}else {
			List<FormField> properties = new ArrayList<>();
			return new FormFieldsDto("123", "456", properties);
		}
	}

	@RolesAllowed({"WRITER", "LECTURER", "EDITOR", "ADMIN"})
	@GetMapping(path = "/paymentFields", produces = "application/json")
	public @ResponseBody
	FormFieldsDto getFiledsPayment(@RequestParam("username") String username) {

		User user = userRepository.findUserByUsername(username);
		RegistrationApplication ra = registrationApplicationRepository.findOneByWriter(user);
		if(ra == null) {
			ArrayList<FormField> properties = new ArrayList<>();
			return new FormFieldsDto("123", "456", properties);
		}else {
			if(ra.getFinalResponse().equals(ApplicationResponse.APPROVED)) {
				Task task = taskService.createTaskQuery().taskName("Membership payment").list().get(0);
				TaskFormData tfd = formService.getTaskFormData(task.getId());
				List<FormField> properties = tfd.getFormFields();
				return new FormFieldsDto(task.getId(), "456", properties);
			} else {
				ArrayList<FormField> properties = new ArrayList<>();
				return new FormFieldsDto("123", "456", properties);
			}

		}
	}

	@RolesAllowed({"WRITER"})
	@PostMapping(path = "/postPayment", produces = "application/json")
	public @ResponseBody ResponseEntity<?> postPayment(@RequestBody List<FormFieldDto> formFields)
			throws ValidationError{

		boolean paymentOk = true;
		paymentOk = submitPaymentService.checkCardForm(formFields);
		if (!paymentOk) {
			throw new ValidationError(ErrorMessages.CARD_ERROR());
		}
		
		List<Task> tasks = taskService.createTaskQuery().taskName("Membership payment").list();
		Task task = tasks.get(0);

		String processInstanceId = task.getProcessInstanceId();
		HashMap<String, Object> map = this.mapListToDto(formFields);
		//Create variable "registration"
		runtimeService.setVariable(processInstanceId,
				"payment",
				formFields);
		formService.submitTaskForm(task.getId(), map);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
