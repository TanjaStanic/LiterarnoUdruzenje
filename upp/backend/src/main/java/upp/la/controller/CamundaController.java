package upp.la.controller;

import org.camunda.bpm.engine.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upp.la.dto.FormFieldDto;
import upp.la.util.HashMapper;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/camunda-controller")
public class CamundaController {

  @Autowired FormService formService;

  @PostMapping(path = "/task/form", produces = "application/json")
  public @ResponseBody ResponseEntity<?> submitForm(
      @RequestBody List<FormFieldDto> formFields,
      @RequestParam("username") String username,
      @RequestParam("taskId") String taskId) {

    HashMap<String, Object> map = HashMapper.listToMap(formFields);

    formService.submitTaskForm(taskId, map);

    return ResponseEntity.ok("Ok");
  }
}
