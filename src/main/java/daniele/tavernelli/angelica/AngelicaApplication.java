package daniele.tavernelli.angelica;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;

import daniele.tavernelli.angelica.utility.automathic.AutomathicTaskExecutor;

@SpringBootApplication
public class AngelicaApplication extends SpringBootServletInitializer {
	
	@Autowired
	private AutomathicTaskExecutor automathicTaskExecutor;
	
	private static AutomathicTaskExecutor staticAutomathicTaskExecutor;
	
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AngelicaApplication.class);
    }
	
	@PostConstruct
    public void init() {
		AngelicaApplication.staticAutomathicTaskExecutor = automathicTaskExecutor;
    }

	
	public static void main(String[] args) {
				
		SpringApplication.run(AngelicaApplication.class, args);
		
		//start the automatic actions
		staticAutomathicTaskExecutor.startExecutionAt(0, 0, 0);
		
		
	}
}
