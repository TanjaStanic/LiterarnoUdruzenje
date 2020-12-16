package upp.la.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;

import java.util.List;

@Service
public class RegistrationService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormFieldDto> registration =
            (List<FormFieldDto>) execution.getVariable("registration");
        User user = identityService.newUser("");
        upp.la.model.User userModel = new upp.la.model.User();

        for (FormFieldDto formField : registration) {

            if (formField.getFieldId().equals("firstNameId")) {
                user.setFirstName(formField.getFieldValue());
                userModel.setFirstName(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("lastNameId")) {
                user.setLastName(formField.getFieldValue());
                userModel.setLastName(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("emailId")) {
                user.setEmail(formField.getFieldValue());
                userModel.setEmail(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("passwordId")) {
                //izvrsiti hesovanje i ostalo
                user.setPassword(formField.getFieldValue());
                userModel.setPassword(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("cityId")) {
                userModel.setCity(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("stateId")) {
                userModel.setState(formField.getFieldValue());
            }

        }

        identityService.saveUser(user);
        //napraviti repository za cuvanje usera u bazu
    }
}
