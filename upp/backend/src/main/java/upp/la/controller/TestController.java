package upp.la.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;


    @RequestMapping(method = RequestMethod.GET)
    public void sayHelloWorldGET() {
        ProcessInstance pi =
            runtimeService.startProcessInstanceByKey("Process_1");


    }

}
