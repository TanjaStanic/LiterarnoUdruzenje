package upp.la.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import upp.la.dto.FormFieldDto;
import upp.la.dto.FormFieldsDto;

@RestController
@RequestMapping("/plagiarism")
public class PlagiarismCheckController {
	@Autowired
    private RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    FormService formService;
    
	@GetMapping(path = "/getComplainForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getComplainForm() {

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

	@PostMapping(path = "/postFormFields", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> postForms(@RequestBody List<FormFieldDto> formFields,
                           @RequestParam("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).list().get(0);
        System.out.println("Naziv taska je " + task.getName());
       if(task.getName().equals("Make chosen editors list")) {
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
