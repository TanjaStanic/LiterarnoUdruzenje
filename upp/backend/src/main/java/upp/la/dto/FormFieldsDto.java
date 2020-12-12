package upp.la.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class FormFieldsDto {
    String taskId;
    String processInstanceId;
    List<FormField> formFields;

    public FormFieldsDto(
        String taskId, String processInstanceId, List<FormField> formFields) {
        super();
        this.taskId = taskId;
        this.processInstanceId = processInstanceId;
        this.formFields = formFields;
    }

    public FormFieldsDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

}
