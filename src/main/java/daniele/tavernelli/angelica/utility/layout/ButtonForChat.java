package daniele.tavernelli.angelica.utility.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

import daniele.tavernelli.angelica.database.service.MessaggioService;
import daniele.tavernelli.angelica.database.view.ViewUtente;
import daniele.tavernelli.angelica.utility.gestione.GestioneChat;
import daniele.tavernelli.angelica.utility.gestione.GestioneUtenti;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;
import daniele.tavernelli.angelica.vaadin.Ui;

/**
 * class for layout that opens chat
 * @author Daniele Tavernelli
 *
 */
@SpringComponent
@Scope("prototype")
@Lazy(value = true)
public class ButtonForChat extends HorizontalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	GestioneUtenti gestioneUtenti;

	@Autowired
	GestioneChat chatWindow;
	
	@Autowired
	MessaggioService messaggioService;
	
	@Autowired
	UserLogged userLogged;

	@Autowired
	Ui ui;

	private Button closeButton = new Button(VaadinIcons.CLOSE);
	private ViewUtente other;
	public Button utenteName;

	public ButtonForChat(ViewUtente other) {

		this.other=other;

		utenteName = new Button(other.getUsername());

		utenteName.addClickListener(e -> {
			if (!chatWindow.isAttached()) {
				ui.addWindow(chatWindow);
			} 
			chatWindow.updateChatGridData(other);
			chatWindow.setVisible(true);
			chatWindow.messaggiGrid.scrollToEnd();
			utenteName.setIcon(null);
			
		});

		setSpacing(false);

		closeButton.addClickListener(e -> {

			gestioneUtenti.getChatLayout().removeComponent(this);
			gestioneUtenti.buttonForChatList.remove(other.getId_utente());

		});


		addComponent(utenteName);
		addComponent(closeButton);

	}

	public ViewUtente getUtente() {
		return other;
	}

	public void setUtente(ViewUtente utente) {
		this.other = utente;
	}



}
