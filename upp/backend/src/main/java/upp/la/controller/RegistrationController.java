package upp.la.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import upp.la.dto.FormFieldDto;
import upp.la.dto.FormFieldsDto;
import upp.la.error.ErrorMessages;
import upp.la.exceptions.DuplicateEntity;
import upp.la.exceptions.EntityNotFound;
import upp.la.model.Genre;
import upp.la.service.GenreService;
import upp.la.exceptions.ValidationError;
import upp.la.service.ValidateRegistrationService;
import upp.la.service.internal.RegistrationServiceInt;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

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
    @Autowired
    GenreService genreService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    RegistrationServiceInt registrationServiceInt;

    //ONLY FOR AUTH TEST
    @GetMapping(value = "/test-auth")
    @RolesAllowed({"READER", "WRITER"}) //Name of the allowed role
    public @ResponseBody ResponseEntity<?> testAuth() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/post", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> post(
        @RequestBody List<FormFieldDto> formFields) throws ValidationError {

        boolean validationOk = true;
        validationOk = validationService.checkRegistrationForm(formFields);

        if(!validationOk) {
            throw new ValidationError(ErrorMessages.VALIDATION_ERROR());
        }
        System.out.println("val:" + validationOk);

        List<Task> tasks = taskService.createTaskQuery()
                .taskName("Registration")
                .list();
        Task task = tasks.get(0);

        String processInstanceId = task.getProcessInstanceId();

        String genres = formFields.get(8).getFieldValue();
        String[] parts = genres.split(",");
        List<FormFieldDto> dto_genres = new ArrayList<FormFieldDto>();
        for(String s : parts) {
            FormFieldDto f = new FormFieldDto();
            f.setFieldId(formFields.get(8).getFieldId());
            f.setFieldValue(s);
            dto_genres.add(f);
        }
        formFields.remove(formFields.size()- 1);
        formFields.addAll(dto_genres);
        HashMap<String, Object> map = this.mapListToDto(formFields);
        //Create variable "registration"
        runtimeService.setVariable(processInstanceId,
                                   "registration",
                                   formFields);

        formService.submitTaskForm(task.getId(), map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody FormFieldsDto get() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("registration_process");

        List<Task> tasks = taskService.createTaskQuery()
                .taskName("Registration")
                .list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }
    @GetMapping(path = "/getGenresForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getGenresForms() {

        Task task  = taskService.createTaskQuery().taskName("Registration").list().get(0);
        Task task1 = taskService.createTaskQuery().taskName("Registration reader").list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task1.getId());
        List<FormField> properties = tfd.getFormFields();
        properties.add(formService.getTaskFormData(task.getId()).getFormFields().get(8));
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task1.getId(), "123", properties);
    }

    @PostMapping(path = "/betaNo", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> BetaNo(
        @RequestBody List<FormFieldDto> formFields) {

        HashMap<String, Object> map = this.mapListToDto(formFields);
        System.out.println(map.toString());
        List<Task> tasks = taskService.createTaskQuery()
                                      .taskName("Registration reader")
                                      .list();
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

        String genres = formFields.get(1).getFieldValue();
        String[] parts = genres.split(",");

        for(String s : parts) {
            FormFieldDto f = new FormFieldDto();
            f.setFieldId(formFields.get(1).getFieldId());
            f.setFieldValue(s);
           dto_genres.add(f);
        }

        HashMap<String, Object> map_betaYes = this.mapListToDto(dto_betaYes);
        HashMap<String, Object> map_genres = this.mapListToDto(dto_genres);

        List<Task> tasks = taskService.createTaskQuery().taskName("Registration reader").list();
        Task task = tasks.get(0);
        System.out.println(task.getName());

        // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId,
                                   "betaYes_registration",
                                   dto_betaYes);
        formService.submitTaskForm(task.getId(), map_betaYes);


        tasks = taskService.createTaskQuery().taskName("Genres for beta reader").list();
        task = tasks.get(0);
        System.out.println(task.getName());
        // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        processInstanceId = task.getProcessInstanceId();

        System.out.println(task.getId() + " ovo je task id");
        System.out.println(map_genres.toString() + " ovo je mapa");
        runtimeService.setVariable(processInstanceId,
                "betaYes_registration_genres",
                dto_genres);
        formService.submitTaskForm(task.getId(), map_genres);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @Validated
    @GetMapping(value = "/verify-account")
    public @ResponseBody ResponseEntity<?> verifyAccount(
        @RequestParam @NotNull String confirmationToken, 
        @RequestParam String processId) throws EntityNotFound {
      
    	runtimeService.setVariable(processId, "validation_token", confirmationToken);
    	MessageCorrelationResult result = runtimeService.createMessageCorrelation("recieve_verification_email")
                .processInstanceId(processId).correlateWithResult();

      return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    
    private HashMap<String, Object> mapListToDto(List<FormFieldDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormFieldDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
