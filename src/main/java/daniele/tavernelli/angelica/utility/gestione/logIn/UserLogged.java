package daniele.tavernelli.angelica.utility.gestione.logIn;


import java.io.Serializable;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

import daniele.tavernelli.angelica.database.entity.Utente;
import daniele.tavernelli.angelica.database.entity.ViewUtente;

/**
 * class representig user logged bean
 * @author Daniele Tavernelli
 *
 */
@SpringComponent
@VaadinSessionScope
public class UserLogged implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Utente utente;
	
	private ViewUtente viewUtente;

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public ViewUtente getViewUtente() {
		return viewUtente;
	}

	public void setViewUtente(ViewUtente viewUtente) {
		this.viewUtente = viewUtente;
	}
	
	
	
}
