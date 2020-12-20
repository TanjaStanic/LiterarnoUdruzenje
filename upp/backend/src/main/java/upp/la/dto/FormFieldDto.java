package upp.la.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class FormFieldDto implements Serializable {
    String fieldId;
    String fieldValue;

}
