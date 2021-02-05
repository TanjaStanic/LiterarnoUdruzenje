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
import upp.la.util.HashMapper;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/camunda-controller")
public class CamundaController {

  @Autowired FormService formService;

  @Autowired TaskService taskService;

  @Autowired RuntimeService runtimeService;

  @RolesAllowed({"READER", "BETA_READER", "WRITER", "LECTURER", "EDITOR", "ADMIN", "WRITER_FILES"})
  @PostMapping(path = "/postForms", produces = "application/json")
  public @ResponseBody ResponseEntity<?> submitForm(
      @RequestBody List<FormFieldDto> formFields, @RequestParam("taskId") String taskId) {

    HashMap<String, Object> map = HashMapper.listToMap(formFields);

    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

    String processInstanceId = task.getProcessInstanceId();

    runtimeService.setVariable(processInstanceId, task.getName(), formFields);

    formService.submitTaskForm(taskId, map);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(path = "/getForms", produces = "application/json")
  public @ResponseBody FormFieldsDto getBookDetailsForm(@RequestParam("taskName") String taskName) {
    Task task = taskService.createTaskQuery().taskName(taskName).singleResult();

    TaskFormData tfd = formService.getTaskFormData(task.getId());

    List<FormField> properties = tfd.getFormFields();

    return new FormFieldsDto(task.getId(), "123", properties);
  }
}
