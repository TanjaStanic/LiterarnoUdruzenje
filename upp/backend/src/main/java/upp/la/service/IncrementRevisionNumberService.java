package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class IncrementRevisionNumberService implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		int ctr = (int) execution.getVariable("COUNTER");
        ctr++;
        execution.setVariable("COUNTER", ctr);
		
	}

}
