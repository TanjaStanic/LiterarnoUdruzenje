package upp.la.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormFieldDto {
    String fieldId;
    String fieldValue;

    public FormFieldDto(String fieldId, String fieldValue) {
        super();
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
    }



}
