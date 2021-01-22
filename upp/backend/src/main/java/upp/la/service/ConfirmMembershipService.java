package upp.la.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.dto.FormFieldDto;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.model.registration.RegistrationApplication;
import upp.la.repository.UserRepository;

@Service
public class ConfirmMembershipService implements JavaDelegate {

	@Autowired
	UserRepository userRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<FormFieldDto> files =(List<FormFieldDto>) execution.getVariable("files");
		User writer = userRepository.findUserByUsername(files.get(1).getFieldValue());

		writer.setConfirmed(true);
		writer.setRole(Role.WRITER);
		userRepository.save(writer);

	}
	
}
