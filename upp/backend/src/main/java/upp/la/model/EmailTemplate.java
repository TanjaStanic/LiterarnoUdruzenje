package upp.la.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upp.la.model.registration.ApplicationResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {

  public static String VERIFICATION_SUBJECT() {
    return "Verify account";
  }

  public static String VERIFICATION_MESSAGE(String token) {
    return "Please verify your account by clicking this link: " + token;
  }

  public static String REGISTRATION_APPLICATION_SUBJECT() {
    return "Registration application response";
  }

  public static String REGISTRATION_APPLICATION_MESSAGE(ApplicationResponse response) {
    return "Your registration application to Literary Association has been marked as: "
        + response.name()
        + ".";
  }

  public static String REGISTRATION_APPLICATION_DEADLINE_MESSAGE(Integer days) {
    return "You have: " + days.toString() + " to provide more material.";
  }

  public static String REGISTRATION_APPLICATION_REVIEW_SUBJECT() {
    return "Registration application review";
  }

  public static String REGISTRATION_APPLICATION_REVIEW_MESSAGE() {
    return "You have been assigned to review registration application material.";
  }

  public static String REGISTRATION_APPLICATION_PAYMENT_SUBJECT() {
    return "Registration application payment";
  }

  public static String REGISTRATION_APPLICATION_PAYMENT_MESSAGE(Integer days) {
    return "You have: " + days.toString() + " to make the registration payment.";
  }

  public static String REGISTRATION_APPLICATION_FAILED_SUBJECT() {
    return "Registration failed";
  }

  public static String REGISTRATION_APPLICATION_FAILED_MESSAGE() {
    return "You have failed to make the payment.";
  }

  private String address;

  private String subject;

  private String message;

  public EmailTemplate(String subject, String message) {
    this.subject = subject;
    this.message = message;
  }

  public static EmailTemplate VerificationEmail(String token) {
    return new EmailTemplate(
        EmailTemplate.VERIFICATION_SUBJECT(), EmailTemplate.VERIFICATION_MESSAGE(token));
  }

  public static EmailTemplate ApplicationResponseEmail(ApplicationResponse response) {
    return new EmailTemplate(
        EmailTemplate.REGISTRATION_APPLICATION_SUBJECT(),
        EmailTemplate.REGISTRATION_APPLICATION_MESSAGE(response));
  }

  public static EmailTemplate ApplicationResponseLackingMaterialEmail(Integer deadlineDays) {
    return new EmailTemplate(
        EmailTemplate.REGISTRATION_APPLICATION_SUBJECT(),
        EmailTemplate.REGISTRATION_APPLICATION_MESSAGE(ApplicationResponse.LACKING_MATERIAL)
            + EmailTemplate.REGISTRATION_APPLICATION_DEADLINE_MESSAGE(deadlineDays));
  }

  public static EmailTemplate RegistrationApplicationReviewInviteEmail() {
    return new EmailTemplate(
        EmailTemplate.REGISTRATION_APPLICATION_REVIEW_SUBJECT(),
        EmailTemplate.REGISTRATION_APPLICATION_REVIEW_MESSAGE());
  }

  public static EmailTemplate RegistrationApplicationPayment(Integer deadlineDays) {
    return new EmailTemplate(
        EmailTemplate.REGISTRATION_APPLICATION_PAYMENT_SUBJECT(),
        EmailTemplate.REGISTRATION_APPLICATION_PAYMENT_MESSAGE(deadlineDays));
  }

  public static EmailTemplate RegistrationApplicationPaymentFailed() {
    return new EmailTemplate(
        EmailTemplate.REGISTRATION_APPLICATION_FAILED_SUBJECT(),
        EmailTemplate.REGISTRATION_APPLICATION_FAILED_MESSAGE());
  }
}
