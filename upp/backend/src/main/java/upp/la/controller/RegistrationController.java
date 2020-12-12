package upp.la.controller;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import upp.la.dto.FormFieldDto;


@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	FormService formService;
	
	@PostMapping(path = "/post/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> post(@RequestBody List<FormFieldDto> formFields, @PathVariable String taskId) {
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("registration_process");
		HashMap<String, Object> map = this.mapListToDto(formFields);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();	
		String processInstanceId = task.getProcessInstanceId();
		
		//Create variable "registration"
		runtimeService.setVariable(processInstanceId, "registration", formFields);
		formService.submitTaskForm(taskId, map);
				
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	private HashMap<String, Object> mapListToDto(List<FormFieldDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormFieldDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
}
