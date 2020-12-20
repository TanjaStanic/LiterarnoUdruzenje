package upp.la.dto;

import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.form.FormField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
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
}
