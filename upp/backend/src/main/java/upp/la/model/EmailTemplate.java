package upp.la.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailTemplate {

  public static String VERIFY_SUBJECT() {
    return "Verify account";
  }

  public static String VERIFY_MESSAGE(String token) {
    return "Please verify your account by clicking this link: " + token;
  }

  private String address;

  private String subject;

  private String message;

  public EmailTemplate() {}
}
