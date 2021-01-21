package upp.la.service.internal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.registration.ApplicationResponse;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.RegistrationApplicationResponseRepository;

@Service
public class ReviewServiceInt{
	
	@Autowired
	RegistrationApplicationResponseRepository regAppResponseRepository;
			
	public void updateRegAppResponse(RegistrationApplicationResponse response,List<FormFieldDto> reviews){
				
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

	}
	


	
}
