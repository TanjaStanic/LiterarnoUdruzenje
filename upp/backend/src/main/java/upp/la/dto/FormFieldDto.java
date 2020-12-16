package upp.la.dto;

import java.io.Serializable;

public class FormFieldDto implements Serializable {
    String fieldId;
    String fieldValue;

    public FormFieldDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FormFieldDto(String fieldId, String fieldValue) {
        super();
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

}
