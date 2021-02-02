package upp.la.util;

import upp.la.dto.FormFieldDto;

import java.util.HashMap;
import java.util.List;

public class HashMapper {

  public static HashMap<String, Object> listToMap(List<FormFieldDto> list) {
    HashMap<String, Object> map = new HashMap<String, Object>();

    for (FormFieldDto temp : list) {
      map.put(temp.getFieldId(), temp.getFieldValue());
    }
    return map;
  }
}
