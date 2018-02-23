package daniele.tavernelli.angelica.vaadin;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import daniele.tavernelli.angelica.utility.LongNotification;
import daniele.tavernelli.angelica.utility.gestione.GestioneVisibilitàEPermessi;
import daniele.tavernelli.angelica.utility.gestione.logIn.Authentication;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;

@SpringView(name = VaadinLogInView.NAME,ui={Ui.class})
@UIScope
public class VaadinLogInView extends VerticalLayout implements View{
	

	@Autowired 
	Authentication authentication;
	
	@Autowired
	GestioneVisibilitàEPermessi gestioneVisibilità;
	
	@Autowired
	UserLogged userLogged;
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "LOG_IN";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	          

	@PostConstruct
	void init() {
		
		Panel panel = new Panel("Login");
		
		panel.setSizeUndefined();
		
		addComponent(panel);

		FormLayout content = new FormLayout();
		
		TextField username = new TextField("Username");
		
		content.addComponent(username);
		
		PasswordField password = new PasswordField("Password");
		
		content.addComponent(password);
		
		Button accedi = new Button("Accedi");
		
		accedi.setClickShortcut(KeyCode.ENTER);
		
		accedi.addClickListener(new ClickListener() {
		
		    private static final long serialVersionUID = 1L;
		
		    @Override
		
		    public void buttonClick(ClickEvent event) {
		
		    	String errore = authentication.authenticate(username.getValue(), password.getValue());
		    	
		        if(errore==null){
		        	
		            gestioneVisibilità.fromLogInToMain();

		 
		            getUI().getNavigator().navigateTo( MainView.NAME);
		            
		            log.info("login succeded: utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());
						
		        }else{
		
		            new LongNotification(errore, Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
		            log.info("login failed: username= " + username.getValue());

		
		        }
		
		    }
		
		     
		
		});
		
		content.addComponent(accedi);
		
		content.setSizeUndefined();
		
		content.setMargin(true);
		
		panel.setContent(content);
		
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		setSizeFull();
		
	}
	
	
}
