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
import upp.la.model.User;
import upp.la.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    FormService formService;
    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/getBookDetailsForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getBookDetailsForm() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("bookPublishingProcessId");

        List<Task> tasks = taskService.createTaskQuery().taskName("Book details form").list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }

    @GetMapping(path = "/initialBookReviewForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto initialBookReviewForm() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Initial book review").list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }

    @GetMapping(path = "/submitManuscriptForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto submitManuscriptForm() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Submit manuscript").list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }

    @GetMapping(path = "/sendToBetaReadersForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto sendToBetaReadersForm() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Send to beta readers?").list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }

    @GetMapping(path = "/BetaReaderCommentsForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto BetaReaderCommentsForm() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Beta reader comments").list();
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
        if(task.getName().equals("Select beta readers")) {
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

    private HashMap<String, Object> mapListToDto(List<FormFieldDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormFieldDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

}
