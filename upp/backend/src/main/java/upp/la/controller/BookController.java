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
import upp.la.model.BookComments;
import upp.la.model.User;
import upp.la.repository.BookRepository;
import upp.la.repository.UserRepository;

import javax.annotation.security.RolesAllowed;
import java.text.Normalizer;
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
    @Autowired
    BookRepository bookRepository;


    @RolesAllowed({"WRITER", "ADMIN"})
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

    @RolesAllowed({"EDITOR", "ADMIN"})
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

    @RolesAllowed({"WRITER", "ADMIN"})
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

    @GetMapping(path = "/acceptManuscriptAndsendToBetaReadersForm", produces = "application/json")
    @RolesAllowed({"EDITOR", "ADMIN"})
    public @ResponseBody
    FormFieldsDto sendToBetaReadersForm() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Accept manuscript and send to beta readers?").list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }

    @GetMapping(path = "/betaReadersForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto betaReadersForm() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Select beta readers").list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }

    @RolesAllowed({"BETA_READER", "ADMIN"})
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

    @GetMapping(path = "/improveManuscriptForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto improveManuscriptForm() {

        ArrayList<FormField> ret = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().taskName("Improve manuscript").list();
        if(tasks.isEmpty()) {
            return new FormFieldsDto("000", "123", ret);
        } else {
            Task task = tasks.get(0);

            TaskFormData tfd = formService.getTaskFormData(task.getId());
            List<FormField> properties = tfd.getFormFields();
            for (FormField fp : properties) {
                System.out.println(fp.getId() + fp.getType());
            }

            return new FormFieldsDto(task.getId(), "123", properties);
        }
    }

    @GetMapping(path = "/needMoreWorkForm", produces = "application/json")
    public @ResponseBody
    FormFieldsDto needMoreWorkForm() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Needs more work?").list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }

    @GetMapping(path = "/lecturerNotesTypos", produces = "application/json")
    public @ResponseBody
    FormFieldsDto lecturerNotesTypos() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Lecturer notes typos").list();
        Task task = tasks.get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), "123", properties);
    }

    @GetMapping(path = "/editorHasSuggestions", produces = "application/json")
    public @ResponseBody
    FormFieldsDto editorHasSuggestions() {

        List<Task> tasks = taskService.createTaskQuery().taskName("Editor has suggestions?").list();
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
            System.out.println("parts.size je " + parts.length);
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

    @GetMapping(path = "/booksForInitialReviews")
    public @ResponseBody
    ResponseEntity<List<Book>> booksForInitalReviews(@RequestParam("username") String username) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Book> ret = new ArrayList<>();
        User user = userRepository.findUserByUsername(username);
        books = bookRepository.findAllByEditor(user);
        for(Book b : books) {
            if(b.getDocument() == null) {
                ret.add(b);
            }
        }

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(path = "/getBooksForWriter")
    public @ResponseBody
    ResponseEntity<List<Book>> getBooksForWriter(@RequestParam("username") String username) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Book> ret = new ArrayList<>();
        User user = userRepository.findUserByUsername(username);
        for(Book b : user.getBooks()) {
            if(b.isAccepted()) {
                ret.add(b);
            }
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(path = "/getBooksForReview")
    public @ResponseBody
    ResponseEntity<List<Book>> getBooksForReview(@RequestParam("username") String username) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Book> ret = new ArrayList<>();
        User user = userRepository.findUserByUsername(username);
        books = bookRepository.findAllByEditor(user);
        for(Book b : books) {
            if(b.getDocument() != null) {
                ret.add(b);
            }
        }

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(path = "/getBooksForBetaReader")
    public @ResponseBody
    ResponseEntity<List<Book>> getBooksForBetaReader(@RequestParam("username") String username) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Book> ret = new ArrayList<>();
        User user = userRepository.findUserByUsername(username);
        books = bookRepository.findAll();
        for(Book b : books) {
          if(b.getComments() != null) {
              for(BookComments bookComments : b.getComments()) {
                  if(bookComments.getBetaReader().getUsername().equals(username)) {
                      ret.add(b);
                  }
              }
          }
        }

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(path = "/getBooksForLecturer")
    public @ResponseBody
    ResponseEntity<List<Book>> getBooksForLecturer(@RequestParam("username") String username) {
        ArrayList<Book> books = new ArrayList<>();
        User user = userRepository.findUserByUsername(username);
        books = bookRepository.findAllByLecturer(user);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


}
