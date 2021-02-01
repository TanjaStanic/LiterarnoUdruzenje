package upp.la.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upp.la.dto.FormFieldDto;
import upp.la.dto.FormFieldsDto;
import upp.la.error.ErrorMessages;
import upp.la.model.Document;
import upp.la.model.exceptions.FileError;
import upp.la.service.internal.DocumentServiceInt;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping(value = "/files")
public class FileUploadController {

  @Autowired DocumentServiceInt documentServiceInt;

  @Autowired TaskService taskService;


  String files_string = "";

  @Autowired
  FormService formService;

  @Autowired RuntimeService runtimeService;

  @PostMapping("/upload")
  public ResponseEntity<?> uploadToLocalFileSystem(@RequestParam("file") MultipartFile file)
      throws FileError {
    String fileDownloadUri = processFile(file);

    return ResponseEntity.ok(fileDownloadUri);
  }

  @PostMapping("/upload-document")
  public ResponseEntity<?> uploadDocumentToLocalFileSystem(@RequestParam("file") MultipartFile file)
      throws FileError {
    String fileDownloadUri = processFile(file);

    Document saved = documentServiceInt.createNew(fileDownloadUri);

    return ResponseEntity.ok(saved.getId().toString());
  }

  @PostMapping("/multi-upload")
  public ResponseEntity<?> multiUpload(@RequestParam("files") MultipartFile[] files) {
    List<Object> fileDownloadUrls = new ArrayList<>();
    files_string = "";
    Arrays.stream(files)
        .forEach(
            file -> {
              try {
                fileDownloadUrls.add(uploadToLocalFileSystem(file).getBody());
                String fileDownloadUri = processFile(file);
                Document saved = documentServiceInt.createNew(fileDownloadUri);
              } catch (FileError fileError) {
                fileError.printStackTrace();
              }
            });
    return ResponseEntity.ok(fileDownloadUrls);
  }

  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<?> downloadFileFromLocal(@PathVariable String fileName) throws FileError {
    Path path = Paths.get(System.getProperty("user.home") + File.separator + fileName);
    Resource resource = null;
    try {
      resource = new UrlResource(path.toUri());
    } catch (MalformedURLException e) {
      throw new FileError(ErrorMessages.FILE_ERROR());
    }
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }

  @GetMapping(value = "/zip-download", produces = "application/zip")
  public void zipDownload(@RequestParam List<String> name, HttpServletResponse response)
      throws IOException, FileError {
    ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

    for (String fileName : name) {
      FileSystemResource resource =
          new FileSystemResource(System.getProperty("user.home") + File.separator + fileName);

      if (resource.getFilename() == null) {
        throw new FileError(ErrorMessages.FILE_ERROR());
      }

      ZipEntry zipEntry = new ZipEntry(resource.getFilename());
      zipEntry.setSize(resource.contentLength());
      zipOut.putNextEntry(zipEntry);
      StreamUtils.copy(resource.getInputStream(), zipOut);
      zipOut.closeEntry();
    }
    zipOut.finish();
    zipOut.close();
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Files.zip\"");
  }

  private String processFile(@RequestParam("file") MultipartFile file) throws FileError {
    if (file.getOriginalFilename() == null) {
      throw new FileError(ErrorMessages.FILE_ERROR());
    }

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    Path path = Paths.get(System.getProperty("user.home") + File.separator + fileName);
    try {
      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
      throw new FileError(ErrorMessages.FILE_ERROR());
    }
    return ServletUriComponentsBuilder.fromCurrentContextPath()
                                  .path("/files/download/")
                                  .path(fileName)
                                  .toUriString();
  }

  private HashMap<String, Object> mapListToDto(List<FormFieldDto> list) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    for (FormFieldDto temp : list) {
      map.put(temp.getFieldId(), temp.getFieldValue());
    }

    return map;
  }

  @PostMapping(path = "/filesFields", produces = "application/json")
  public @ResponseBody ResponseEntity<?> post(@RequestBody List<FormFieldDto> formFields,
                                              @RequestParam("taskId") String taskId) {
    Task task = taskService.createTaskQuery().taskId(taskId).list().get(0);
    System.out.println("Naziv taska je " + task.getName());
    System.out.println(taskId);
    String processInstanceId = task.getProcessInstanceId();
    HashMap<String, Object> map = this.mapListToDto(formFields);

    runtimeService.setVariable(processInstanceId,
            "files",
            formFields);
    formService.submitTaskForm(taskId, map);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(path = "/getReviewForm", produces = "application/json")
  public @ResponseBody
  FormFieldsDto getReviewForm() {

    Task task = taskService.createTaskQuery().taskName("ReviewWriter").list().get(0);
    TaskFormData tfd = formService.getTaskFormData(task.getId());
    List<FormField> properties = tfd.getFormFields();
    return new FormFieldsDto(task.getId(), "456", properties);
  }

}
