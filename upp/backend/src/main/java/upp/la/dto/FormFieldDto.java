package upp.la.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import org.camunda.bpm.engine.form.FormField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormFieldDto implements Serializable {
    String fieldId;
    String fieldValue;

}
