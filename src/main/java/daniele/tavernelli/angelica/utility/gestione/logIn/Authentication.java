package daniele.tavernelli.angelica.utility.gestione.logIn;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

import daniele.tavernelli.angelica.database.entity.Utente;
import daniele.tavernelli.angelica.database.service.UtenteService;
import daniele.tavernelli.angelica.database.service.ViewUtenteService;
import daniele.tavernelli.angelica.database.view.ViewUtente;

@SpringComponent
@VaadinSessionScope
public class Authentication implements Serializable{

	private static final long serialVersionUID = 1031192294282960539L;

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private ViewUtenteService viewUtenteService;
	
	@Autowired
	private UserLogged utenteLoggato;
	
	
	public String authenticate(String username, String password) {
		
		String errore=null;
		
		Utente utente=null;
		
		ViewUtente viewUtente = null;
		
		
		try {
			
		List<Utente> list = utenteService.findUtenteByUserName(username);
		
		
		if (list!=null && !list.isEmpty()) {
			
			utente=list.get(0) ;
			
			viewUtente = viewUtenteService.findByIdUtente(utente.getId_utente()).get(0);
			
			if (!utente.getPassword().equals(password)) {
				errore = "Credenziali errate";
			}
			
		} else {
			errore = "Credenziali errate";
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
			errore= "Errore durante la richiesta";
		}
		
		if (errore!=null) {
			utente=null;
			viewUtente=null;
		}
		utenteLoggato.setUtente(utente);
		utenteLoggato.setViewUtente(viewUtente);
		return errore;
		
	}
	
}
