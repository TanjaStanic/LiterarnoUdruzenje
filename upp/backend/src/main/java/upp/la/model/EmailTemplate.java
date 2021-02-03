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
    return "Your appliation is accespted. You have: "
        + days.toString()
        + " to make the registration payment.";
  }

  public static String REGISTRATION_APPLICATION_FAILED_SUBJECT() {
    return "Registration failed";
  }

  public static String REGISTRATION_APPLICATION_NOT_ACCEPTED() {
    return "Your registration is not accepted.";
  }

  public static String REGISTRATION_APPLICATION_FAILED_MESSAGE() {
    return "You have failed to make the payment.";
  }

  public static String PUBLISHING_DECLINED_BEFORE_MANUSCRIPT() {
    return "Book declined";
  }

  public static String PUBLISHING_DECLINED_BEFORE_MANUSCRIPT_MESSAGE(String reason) {
    return "Your book publishing request has been declined."
        + "Reason: " + reason + ".";
  }

  public static String PUBLISHING_NOTIFY_WRITER_EXPIRED() {
    return "Your time to provide a manuscript has expired";
  }

  public static String PUBLISHING_NOTIFY_WRITER_EXPIRED_MESSAGE() {
    return "Your time to provide a manuscript for your book publishing request has expired."
      + "Please try again.";
  }

  public static String PLAGIARISM_COMPLAINT_NOTIFY_CHIEF_EDITOR() {
    return "New plagiarism complaint";
  }

  public static String PLAGIARISM_COMPLAINT_NOTIFY_CHIEF_EDITOR_MESSAGE() {
    return "There is a new plagiarism complaint ready for a review.";
  }

  public static String PLAGIARISM_NOTIFY_WRITER_DECISION() {
    return "Your plagiarism complaint has been processed";
  }

  public static String PLAGIARISM_NOTIFY_WRITER_DECISION_MESSAGE(String decision) {
    return "The plagiarism complaint has been reviewed. "
        + "The complaint has been marked as: " + decision + ".";
  }

  public static String PLAGIARISM_COMPLAINT_NOTIFY_EDITORS() {
	    return "New plagiarism complaint to review";
  }

  public static String PLAGIARISM_COMPLAINT_NOTIFY_EDITORS_MESSAGE() {
	    return "There is a new plagiarism complaint ready for a review.";
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

  public static EmailTemplate RegistrationApplicationNotAccepted() {
    return new EmailTemplate(
        EmailTemplate.REGISTRATION_APPLICATION_FAILED_SUBJECT(),
        EmailTemplate.REGISTRATION_APPLICATION_NOT_ACCEPTED());
  }

  public static EmailTemplate PublishingDeclinedBeforeManuscript(String reason) {
    return new EmailTemplate(
        EmailTemplate.PUBLISHING_DECLINED_BEFORE_MANUSCRIPT(),
        EmailTemplate.PUBLISHING_DECLINED_BEFORE_MANUSCRIPT_MESSAGE(reason));
  }

  public static EmailTemplate PublishingNotifyWriterExpired() {
    return new EmailTemplate(
        EmailTemplate.PUBLISHING_NOTIFY_WRITER_EXPIRED(),
        EmailTemplate.PUBLISHING_NOTIFY_WRITER_EXPIRED_MESSAGE());
  }

  public static EmailTemplate PlagiarismComplaintNotifyChiefEditor() {
    return new EmailTemplate(
        EmailTemplate.PLAGIARISM_COMPLAINT_NOTIFY_CHIEF_EDITOR(),
        EmailTemplate.PLAGIARISM_COMPLAINT_NOTIFY_CHIEF_EDITOR_MESSAGE());
  }

  public static EmailTemplate PlagiarismNotifyWriterDecision(String decision) {
    return new EmailTemplate(
        EmailTemplate.PLAGIARISM_NOTIFY_WRITER_DECISION(),
        EmailTemplate.PLAGIARISM_NOTIFY_WRITER_DECISION_MESSAGE(decision));
  }
  
  public static EmailTemplate PlagiarismComplaintNotifyEditors(Integer deadlineDays) {
	    return new EmailTemplate(
	        EmailTemplate.PLAGIARISM_COMPLAINT_NOTIFY_EDITORS(),
	        EmailTemplate.PLAGIARISM_COMPLAINT_NOTIFY_EDITORS_MESSAGE()
	            + EmailTemplate.REGISTRATION_APPLICATION_DEADLINE_MESSAGE(deadlineDays));
	  }
}
