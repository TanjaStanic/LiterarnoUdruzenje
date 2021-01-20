package upp.la.service.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import upp.la.dto.FormFieldDto;
import upp.la.model.User;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.registration.ApplicationResponse;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.RegistrationApplicationResponseRepository;
import upp.la.repository.UserRepository;

@Service
public class ReviewServiceInt {

	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	RegistrationApplicationResponseServiceInt regAppResponseServiceInt;
	
	@Autowired
	RegistrationApplicationResponseRepository regAppResponseRepository;
	
	@Autowired
	RegistrationApplicationRepository appRegRepository;
	
	public RegistrationApplicationResponse makeRegistrationApplicationResponse(List<FormFieldDto> reviews) throws EntityNotFound {
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		User lecturer = userRepository.findByUsername(authenticate.getName());
		
		RegistrationApplicationResponse response = regAppResponseServiceInt.createNew(1L, lecturer.getId());
		
		for (FormFieldDto r : reviews) {
			if (r.getFieldId().equals("comment_id")) {
				response.setComment(r.getFieldValue());
			}	
			if (r.getFieldId().equals("review_id")) {
				if (r.getFieldValue().equals("accept_id")) {
					response.setResponse(ApplicationResponse.APPROVED);
				}
				else if (r.getFieldValue().equals("reject_id")) {
					response.setResponse(ApplicationResponse.NOT_APPROVED);
				}
				else if (r.getFieldValue().equals("more_documents_id")){
					response.setResponse(ApplicationResponse.LACKING_MATERIAL);
				}
			}
		}
		
		response = regAppResponseRepository.save(response);
		return response;

	}

	
}
