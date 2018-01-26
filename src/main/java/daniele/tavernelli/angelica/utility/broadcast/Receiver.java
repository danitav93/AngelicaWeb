package daniele.tavernelli.angelica.utility.broadcast;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import daniele.tavernelli.angelica.database.service.ViewUtenteService;
import daniele.tavernelli.angelica.database.view.ViewUtente;
import daniele.tavernelli.angelica.utility.Constants;
import daniele.tavernelli.angelica.utility.gestione.GestioneChat;
import daniele.tavernelli.angelica.utility.gestione.GestioneUtenti;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;

@SpringComponent
@UIScope
public class Receiver implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	GestioneChat chatWindow;

	@Autowired
	GestioneUtenti gestioneUtenti;

	@Autowired
	UserLogged userLogged;

	@Autowired
	ViewUtenteService viewUtenteService;

	public void receiveMessage(String message) {

		try {

			String tipoMessaggio = message.substring(0, 1);

			switch(tipoMessaggio) {

			case Constants.TIPO_CHAT: {

				String[] messageSplitted = message.split("#");

				long id_mittente = Long.parseLong(messageSplitted[1]);

				long id_destinatario = Long.parseLong(messageSplitted[2]);

				if (id_destinatario==userLogged.getUtente().getId_utente()) {
					notificaNuovoMessaggioRicevuto(id_mittente);
				}

				break;

			}


			}

		} catch (Exception e) {
			e.printStackTrace();
		}



	}

	private void notificaNuovoMessaggioRicevuto(long id_mittente) {

		
		if (chatWindow.isVisible() && chatWindow.other!=null) {
			chatWindow.updateChatGridData(chatWindow.other);
		}
	
		List<ViewUtente> viewUtenteList= viewUtenteService.findByIdUtente(id_mittente);
		gestioneUtenti.addChatButton(viewUtenteList.get(0));

	}




}