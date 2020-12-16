package upp.la.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upp.la.dto.FormFieldDto;
import upp.la.dto.FormFieldsDto;
import upp.la.exceptions.DuplicateEntity;
import upp.la.service.ValidateRegistrationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    FormService formService;
    @Autowired 
    ValidateRegistrationService validationService;


    @PostMapping(path = "/post", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> post(
        @RequestBody List<FormFieldDto> formFields) throws DuplicateEntity {

        boolean validationOk = true;
        validationOk = validationService.checkRegistrationForm(formFields);

        if(validationOk = false) {
            throw new DuplicateEntity("Validation failed");
        }

        ProcessInstance pi =
            runtimeService.startProcessInstanceByKey("registration_process");
        HashMap<String, Object> map = this.mapListToDto(formFields);
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        //Create variable "registration"
        runtimeService.setVariable(processInstanceId,
                                   "registration",
                                   formFields);
        formService.submitTaskForm(task.getId(), map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /*
    @PostMapping(path = "/validateData/{taskId}", produces = "application/json")
    public @ResponseBody
    boolean validateData(
        @RequestBody List<FormFieldDto> formFields,
        @PathVariable String taskId){
    	
    	boolean validationOk = true;
    	validationOk = validationService.checkRegistrationForm(formFields);
    	
    	return validationOk;
    	
    }
   */

    @PostMapping(path = "/betaNo", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> BetaNo(
            @RequestBody List<FormFieldDto> formFields) {

        HashMap<String, Object> map = this.mapListToDto(formFields);
        System.out.println(map.toString());
        List<Task> tasks = taskService.createTaskQuery().taskName("Registration reader").list();
        Task task = tasks.get(0);
        System.out.println(task.getName());
        // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        //Create variable "registration"
        runtimeService.setVariable(processInstanceId,
                "betaNo_registration",
                formFields);
        formService.submitTaskForm(task.getId(), map);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/betaYes", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> BetaYes(
            @RequestBody List<FormFieldDto> formFields) {
        List<FormFieldDto> dto_betaYes = new ArrayList<FormFieldDto>();
        dto_betaYes.add(formFields.get(0));
        List<FormFieldDto> dto_genres = new ArrayList<FormFieldDto>();
        dto_genres.add(formFields.get(1));
        HashMap<String, Object> map_betaYes = this.mapListToDto(dto_betaYes);
        HashMap<String, Object> map_genres = this.mapListToDto(dto_genres);

        // System.out.println(map_genres.toString());

        List<Task> tasks = taskService.createTaskQuery().taskName("Registration reader").list();
        Task task = tasks.get(0);
        System.out.println(task.getName());
        // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        //Create variable "registration"
        runtimeService.setVariable(processInstanceId,
                "betaYes_registration",
                dto_betaYes);
        formService.submitTaskForm(task.getId(), map_betaYes);

        /*
        tasks = taskService.createTaskQuery().taskName("Genres for beta reader").list();
        task = tasks.get(0);
        System.out.println(task.getName());
        // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        processInstanceId = task.getProcessInstanceId();

        //Create variable "registration"
        runtimeService.setVariable(processInstanceId,
                "betaYes_registration_genres",
                dto_genres);
        formService.submitTaskForm(task.getId(), map_genres); */

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
