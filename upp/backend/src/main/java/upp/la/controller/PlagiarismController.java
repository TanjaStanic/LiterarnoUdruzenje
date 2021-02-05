package upp.la.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upp.la.dto.FormFieldsDto;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/plagiarism")
public class PlagiarismController {

  @Autowired RuntimeService runtimeService;

  @Autowired TaskService taskService;

  @Autowired FormService formService;

  @RolesAllowed({"EDITOR", "ADMIN"})
  @GetMapping(path = "/decideIfPlagiarised", produces = "application/json")
  public @ResponseBody FormFieldsDto getDecideIfPlagiarisedForm() {
    Task task = taskService.createTaskQuery().taskName("Decide if plagiarised").singleResult();

    TaskFormData tfd = formService.getTaskFormData(task.getId());

    List<FormField> properties = tfd.getFormFields();

    return new FormFieldsDto(task.getId(), "123", properties);
  }

  @RolesAllowed({"EDITOR", "ADMIN"})
  @GetMapping(path = "/reviewNotes", produces = "application/json")
  public @ResponseBody FormFieldsDto getReviewNotesForm() {
    Task task = taskService.createTaskQuery().taskName("Review notes").singleResult();

    TaskFormData tfd = formService.getTaskFormData(task.getId());

    List<FormField> properties = tfd.getFormFields();

    return new FormFieldsDto(task.getId(), "123", properties);
  }
}
