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
import upp.la.model.Book;
import upp.la.model.User;
import upp.la.model.plagiarism.PlagiarismComplaintResponse;
import upp.la.repository.PlagiarismComplaintResponseRepository;
import upp.la.repository.UserRepository;
import upp.la.util.HashMapper;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/plagiarism")
public class PlagiarismController {

  @Autowired RuntimeService runtimeService;

  @Autowired TaskService taskService;

  @Autowired FormService formService;

  @Autowired
  PlagiarismComplaintResponseRepository plagiarismComplaintResponseRepository;
  @Autowired
  UserRepository userRepository;

  @RolesAllowed({"EDITOR", "ADMIN"})
  @GetMapping(path = "/decideIfPlagiarised", produces = "application/json")
  public @ResponseBody FormFieldsDto getDecideIfPlagiarisedForm() {
    Task task = taskService.createTaskQuery().taskName("Decide if plagiarised").list().get(0);

    TaskFormData tfd = formService.getTaskFormData(task.getId());

    List<FormField> properties = tfd.getFormFields();

    return new FormFieldsDto(task.getId(), "123", properties);
  }

  @RolesAllowed({"EDITOR", "ADMIN"})
  @GetMapping(path = "/reviewNotes", produces = "application/json")
  public @ResponseBody FormFieldsDto getReviewNotesForm() {
    Task task = taskService.createTaskQuery().taskName("Review notes").list().get(0);

    TaskFormData tfd = formService.getTaskFormData(task.getId());

    List<FormField> properties = tfd.getFormFields();

    return new FormFieldsDto(task.getId(), "123", properties);
  }

  @GetMapping(path = "/getComplainForm", produces = "application/json")
  public @ResponseBody
  FormFieldsDto getBookDetailsForm() {

    ProcessInstance pi = runtimeService.startProcessInstanceByKey("plagiarismCheckProcessId");

    List<Task> tasks = taskService.createTaskQuery().taskName("Plagiarism complaint").list();
    Task task = tasks.get(0);

    TaskFormData tfd = formService.getTaskFormData(task.getId());
    List<FormField> properties = tfd.getFormFields();
    for (FormField fp : properties) {
      System.out.println(fp.getId() + fp.getType());
    }

    return new FormFieldsDto(task.getId(), "123", properties);
  }

  @GetMapping(path = "/getEditorsForm", produces = "application/json")
  public @ResponseBody
  FormFieldsDto getEditorsForm() {

    List<Task> tasks = taskService.createTaskQuery().taskName("Choose editors").list();
    Task task = tasks.get(0);

    TaskFormData tfd = formService.getTaskFormData(task.getId());
    List<FormField> properties = tfd.getFormFields();
    for (FormField fp : properties) {
      System.out.println(fp.getId() + fp.getType());
    }

    return new FormFieldsDto(task.getId(), "123", properties);
  }

  @GetMapping(path = "/makeNotes", produces = "application/json")
  public @ResponseBody
  FormFieldsDto makeNotes() {

    List<Task> tasks = taskService.createTaskQuery().taskName("Make notes").list();
    Task task = tasks.get(0);

    TaskFormData tfd = formService.getTaskFormData(task.getId());
    List<FormField> properties = tfd.getFormFields();
    for (FormField fp : properties) {
      System.out.println(fp.getId() + fp.getType());
    }

    return new FormFieldsDto(task.getId(), "123", properties);
  }
  //Genericka forma za post formi
  @PostMapping(path = "/postFormFields", produces = "application/json")
  public @ResponseBody
  ResponseEntity<?> postForms(@RequestBody List<FormFieldDto> formFields,
                              @RequestParam("taskId") String taskId) {
    Task task = taskService.createTaskQuery().taskId(taskId).list().get(0);
    System.out.println("Naziv taska je " + task.getName());
    if(task.getName().equals("Choose editors")) {
      List<FormFieldDto> tmp = new ArrayList<>();
      String[] parts = formFields.get(0).getFieldValue().split(",");
      for(String s : parts) {
        FormFieldDto f = new FormFieldDto();
        f.setFieldId(formFields.get(0).getFieldId());
        f.setFieldValue(s);
        tmp.add(f);
      }

      formFields = tmp;
    }
    String processInstanceId = task.getProcessInstanceId();
    HashMap<String, Object> map = this.mapListToDto(formFields);

    //Setovacemo varijablu na naziv taska
    runtimeService.setVariable(processInstanceId,
            task.getName(),
            formFields);
    formService.submitTaskForm(taskId, map);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(path = "/getPlagiarismForEditor")
  public @ResponseBody
  ResponseEntity<List<PlagiarismComplaintResponse>> getBooksForWriter(@RequestParam("username") String username) {
    ArrayList<PlagiarismComplaintResponse> allForUser = new ArrayList<>();
    ArrayList<Book> ret = new ArrayList<>();
    User user = userRepository.findUserByUsername(username);
    allForUser = plagiarismComplaintResponseRepository.findAllByEditor(user);
    return new ResponseEntity<>(allForUser, HttpStatus.OK);
  }

  @GetMapping(path = "/getComplaintsResponsesComments")
  public @ResponseBody
  ResponseEntity<List<PlagiarismComplaintResponse>> getComplaintsResponsesComments(@RequestParam("id") String id) {
    return new ResponseEntity<>(plagiarismComplaintResponseRepository.findAllByPlagiarismComplaintId(Long.parseLong(id)), HttpStatus.OK);
  }

  private HashMap<String, Object> mapListToDto(List<FormFieldDto> list) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    for (FormFieldDto temp : list) {
      map.put(temp.getFieldId(), temp.getFieldValue());
    }

    return map;
  }
}
