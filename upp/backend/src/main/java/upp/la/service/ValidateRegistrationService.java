package upp.la.service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import upp.la.dto.FormFieldDto;
import upp.la.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidateRegistrationService implements JavaDelegate{

	//for check string
	private Pattern regexPattern;
    private Matcher regexMatcher;
    
    @Autowired
    private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		boolean validationOk;
		
		List<FormFieldDto> formFields = 
        		(List<FormFieldDto>) execution.getVariable("registration");
		
		validationOk = checkRegistrationForm(formFields);
		
		
		if (validationOk) {
			execution.setVariable("validationOk", true);
		}
		else {
			execution.setVariable("validationOk", false);
		}
		
	}

	public boolean checkRegistrationForm(List<FormFieldDto> formFields) {
		//boolean validationOk = true;
		
		for (FormFieldDto f : formFields) {
			if (f.getFieldId().equals("firstNameId")) {
				if (!(nameValidation(f.getFieldValue()))) {
					return false;
				}
			}
			if (f.getFieldId().equals("lastNameId")) {
				if (!(nameValidation(f.getFieldValue()))) {
					return false;
				}
			}
			if (f.getFieldId().equals("cityId")) {
				if (!(nameValidation(f.getFieldValue()))) {
					return false;
				}
			}
			if (f.getFieldId().equals("countryId")) {
				if (!(nameValidation(f.getFieldValue()))) {
					return false;
				}
			}
			if (f.getFieldId().equals("emailId")) {
				if (!(emailValidation(f.getFieldValue()))) {
					return false;
				}
			}
			
			//check if password is empty
			if (f.getFieldId().equals("passwordId")) {
				if (f.getFieldValue().trim().isEmpty()) {
					return false;
				}
			}
			
			//chek if username is not empty or not unique
			if (f.getFieldId().equals("userNameId")) {
				if (f.getFieldValue().trim().isEmpty()) {
					return false;
				}
				if (userRepository.existsByUserName(f.getFieldValue())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean nameValidation(String name) {
		
		//This method validate the name and return false if the name has nothing or has numbers or special characters
		regexPattern = Pattern.compile("^[a-zA-Z\\\\s]+");
		regexMatcher = regexPattern.matcher(name);
		
		return regexMatcher.matches();
	}
	
	
	
	public boolean emailValidation(String email) {
		regexPattern = Pattern.compile("^.+@.+\\..+$");
		regexMatcher = regexPattern.matcher(email);
		
		return regexMatcher.matches();
	}
}
