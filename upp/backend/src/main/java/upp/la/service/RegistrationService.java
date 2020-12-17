package upp.la.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Genre;
import upp.la.model.Role;
import upp.la.repository.GenreRepository;
import upp.la.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RegistrationService implements JavaDelegate {

    @Autowired
    IdentityService identityService;
    
    @Autowired
	UserRepository userRepository;

    @Autowired
    GenreRepository genreRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormFieldDto> registration =
            (List<FormFieldDto>) execution.getVariable("registration");
        List<FormFieldDto> betaNo = (List<FormFieldDto>) execution.getVariable("betaNo_registration");
        List<FormFieldDto> betaYes = (List<FormFieldDto>) execution.getVariable("betaYes_registration");
        List<FormFieldDto> betaYes_genres = (List<FormFieldDto>) execution.getVariable("betaYes_registration_genres");


        String s = "";
        for (FormFieldDto f: registration) {
           if(f.getFieldId().equals("userNameId")) {
               s = f.getFieldValue();
           }
        }
        System.out.println(s);
        User user = identityService.newUser(s);
        upp.la.model.User userModel = new upp.la.model.User();

        for (FormFieldDto formField : registration) {
        	if (formField.getFieldId().equals("userNameId")) {
        		//Staviti da je id userName ili ne?
        		user.setId(formField.getFieldValue());
                userModel.setUserName(formField.getFieldValue());
            }
        	if (formField.getFieldId().equals("passwordId")) {
                //izvrsiti hesovanje i ostalo
                user.setPassword(formField.getFieldValue());
                userModel.setPassword(formField.getFieldValue());
            }
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
            if (formField.getFieldId().equals("cityId")) {
                userModel.setCity(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("countryId")) {
                userModel.setCountry(formField.getFieldValue());
            }
            //diskutovati setovanje beta citaoca i zanrova
            if (formField.getFieldId().equals("roleId")) {
            	
            	if (formField.getFieldValue().equals("value_1")) {
            		userModel.setRole(Role.READER);

            	}
            	else if (formField.getFieldValue().equals("value_2")) {
            		userModel.setRole(Role.WRITER);
            	}
            	
            }


        }

        if(betaYes != null && betaYes_genres != null) {
            userModel.setRole(Role.BETA_READER);
            Collection<Genre> tmp = new ArrayList<>();
            for(FormFieldDto f: betaYes_genres) {
                tmp.add(genreRepository.findGenreById(Long.parseLong(f.getFieldValue())));
            }

            userModel.setGenres(tmp);

        }

        identityService.saveUser(user);
        userRepository.save(userModel);
    }
}
