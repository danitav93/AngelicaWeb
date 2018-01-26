package daniele.tavernelli.angelica.utility.automathic;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;

import daniele.tavernelli.angelica.database.service.MessaggioService;

@SpringComponent
public class DeleteMessageAutomathicTask implements AutomathicExecution {

	@Autowired
	MessaggioService messaggioService;
	
	@Override
	public void execute() {
		
		try {
			messaggioService.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
