package daniele.tavernelli.angelica.utility;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import daniele.tavernelli.angelica.database.view.ViewUtente;

public class LoggedUserVerticalLayout extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	private MenuItem userItem;
	
	public LoggedUserVerticalLayout(ViewUtente utente) {
		
		
		
		MenuBar userImageMenu = new MenuBar();
		
		userItem = userImageMenu.addItem(utente.getUsername(), null,null);
		
		userItem.setIcon(VaadinIcons.USER);
						
		Label ruolo = new Label(utente.getNome_ruolo());

		addComponent(userImageMenu);
		addComponent(ruolo);		
		
		setComponentAlignment(userImageMenu,Alignment.MIDDLE_CENTER);
		setComponentAlignment(ruolo,Alignment.MIDDLE_CENTER);

		
		setWidthUndefined();

	}

	public MenuItem getUserItem() {
		return userItem;
	}

	



	

	
	
}
