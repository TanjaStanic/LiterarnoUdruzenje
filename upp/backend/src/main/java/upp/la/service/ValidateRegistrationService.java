package upp.la.service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import upp.la.dto.FormFieldDto;

public class ValidateRegistrationService implements JavaDelegate{

	//for check string
	private Pattern regexPattern;
    private Matcher regexMatcher;
	
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
		boolean validationOk = true;
		
		//implementirati provjeru
		return validationOk;
	}
	
	/*public boolean nameValidation(String name) {
		
		return null;
	}
	
	public boolean emailValidation(String email) {
		return null;
	}*/
}
