package daniele.tavernelli.angelica.utility.gestione;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.MenuBar.MenuItem;

import daniele.tavernelli.angelica.utility.Constants;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;
import daniele.tavernelli.angelica.vaadin.MainView;
import daniele.tavernelli.angelica.vaadin.Ui;


@SpringComponent
@UIScope
public class GestioneVisibilitàEPermessi implements Serializable{

	private static final long serialVersionUID = -4056217600060492044L;

	@Autowired
	Ui logInUI;

	@Autowired
	GestioneCollocazioni gestioneCollocazioni;
	
	@Autowired
	GestioneLegenda gestioneLegenda;
	
	@Autowired
	GestioneUtenti gestioneUtenti;

	@Autowired
	UserLogged userLogged;
	

	public void fromLogInToMain() {

		logInUI.logInButton.setVisible(false); 



	}

	public void intoMain(MainView mainView) {

		long ruolo = userLogged.getUtente().getRuolo().getIdRuolo();

		switch ((int)ruolo) {

		case Constants.UTENTE_ESTERNO: {
			gestioneCollocazioni.getCollocazioneGrid().setVisible(false);
			mainView.barmenu.setVisible(false);
			mainView.openLegenda.setVisible(false);
			gestioneUtenti.utenteGrid.getEditor().setEnabled(false);
			gestioneUtenti.utenteGrid.getColumn("remove").setHidden(true);
			gestioneUtenti.utenteGrid.getColumn("password").setHidden(true);
			break;
		}
		
		case Constants.UTENTE_INTERNO: {
			for (MenuItem item:mainView.barmenu.getItems()) {
				if (item.getText().equalsIgnoreCase("Gestione utenti")) {
					item.setVisible(false);
				}
			}
			gestioneUtenti.utenteGrid.getEditor().setEnabled(false);
			gestioneUtenti.utenteGrid.getColumn("password").setHidden(true);
			gestioneCollocazioni.getCollocazioneGrid().getColumn("remove").setHidden(true);
			gestioneUtenti.utenteGrid.getColumn("remove").setHidden(true);
			gestioneLegenda.legendaGrid.getColumn("remove").setHidden(true);
			break;
		}
		
		case Constants.SUPERVISORE: {
			gestioneUtenti.utenteGrid.getEditor().setEnabled(false);
			gestioneUtenti.utenteGrid.getColumn("password").setHidden(true);
			break;

		}

		}

	}

}
