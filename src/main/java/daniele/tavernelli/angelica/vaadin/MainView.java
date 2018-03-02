package daniele.tavernelli.angelica.vaadin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nextfour.datetimelabel.DateTimeLabel;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import daniele.tavernelli.angelica.database.service.CollocazioneService;
import daniele.tavernelli.angelica.database.service.LegendaService;
import daniele.tavernelli.angelica.database.service.MessaggioService;
import daniele.tavernelli.angelica.database.service.RuoloService;
import daniele.tavernelli.angelica.database.service.UtenteService;
import daniele.tavernelli.angelica.database.service.ViewUtenteService;
import daniele.tavernelli.angelica.utility.LoggedUserVerticalLayout;
import daniele.tavernelli.angelica.utility.gestione.GestioneCollocazioni;
import daniele.tavernelli.angelica.utility.gestione.GestioneLegenda;
import daniele.tavernelli.angelica.utility.gestione.GestioneUtenti;
import daniele.tavernelli.angelica.utility.gestione.GestioneVisibilitàEPermessi;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;


/**
 * UI class for main viw,
 * shows grid of collocazioni
 * @author Daniele Tavernelli
 *
 */
@Theme("mytheme")
@SpringView(name = MainView.NAME,ui={Ui.class})
@UIScope
public class MainView extends VerticalLayout implements View {

	public static final String NAME = "MainUI";

	private static final long serialVersionUID = 1L;

	//services
	@Autowired
	CollocazioneService collocazioneService;

	@Autowired
	LegendaService legendaService;

	@Autowired
	UtenteService utenteService;

	@Autowired
	RuoloService ruoloService;

	@Autowired
	MessaggioService messaggioService;

	@Autowired
	ViewUtenteService viewUtenteService;

	//data
	@Autowired
	UserLogged user;

	//gestioni
	@Autowired
	private GestioneCollocazioni  gestioneCollocazioni;

	@Autowired
	private GestioneLegenda gestioneLegenda;

	@Autowired
	private GestioneUtenti gestioneUtenti;

	@Autowired
	private GestioneVisibilitàEPermessi gestioneVisibilita;

	//ui
	public MenuBar barmenu;
	private LoggedUserVerticalLayout loggedUserVerticalLayout;
	public Button openLegenda;
	public Button openUtenti;
	private HorizontalLayout buttonsLayout;
	private HorizontalLayout titleLayout;
	private Label title;





	/*
	 * when entering in view
	 */
	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		//set layout elements
		setLayout();

		updateChatLayout();
		//init visibilità a seconda del ruolo
		gestioneVisibilita.intoMain(this);

	}


	/**
	 * updateChatLayout and add button if new message arrived
	 */
	private void updateChatLayout() {
		try{
			List<Integer> utentiMessaggiNonLetti = messaggioService.getUtentiMessaggiNonLetti(user.getUtente().getIdUtente());
			for (Integer id:utentiMessaggiNonLetti) {
				gestioneUtenti.addChatButton(viewUtenteService.findByIdUtente(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * layout features
	 */
	private void setLayout() {

		setLoggedUserVerticalLayout();

		setTitleLayuot();

		setMenuBar();

		setMenuLoggedUser();

		setButtons();



		addComponent(titleLayout);
		addComponent(barmenu);
		addComponent(gestioneCollocazioni.getCollocazioneGrid());
		addComponent(buttonsLayout);
		addComponent(gestioneUtenti.getChatLayout());

		setComponentAlignment(gestioneUtenti.getChatLayout(), Alignment.MIDDLE_RIGHT);

		//layout.setComponentAlignment(loggedUserVerticalLayout, Alignment.TOP_RIGHT);


	}




	/**
	 * init the buttons of the menu of the logged user
	 */
	private void setMenuLoggedUser() {

		gestioneUtenti.initLoggedUserMenuBar(loggedUserVerticalLayout);

	}


	/**
	 * init layout of logged user
	 */
	private void setLoggedUserVerticalLayout() {

		loggedUserVerticalLayout = new LoggedUserVerticalLayout(user.getViewUtente());

	}


	/**
	 * set buttons under grid buttons 
	 */
	private void setButtons() {

		openLegenda = new Button("Legenda",VaadinIcons.NOTEBOOK);

		openLegenda.addClickListener(cE -> getUI().addWindow(gestioneLegenda.getLegendaSubWindow()));

		openUtenti = new Button("Utenti",VaadinIcons.USERS);

		openUtenti.addClickListener(cE -> getUI().addWindow(gestioneUtenti.getUtentiSubWindow()));

		buttonsLayout=new HorizontalLayout(openLegenda,openUtenti);

	}











	/**
	 * set menu bar
	 */
	private void setMenuBar() {

		barmenu = new MenuBar();

		gestioneCollocazioni.setGestioneCollocazioniMenuItem(barmenu);

		//gestioneLegenda.setGestioneLegendaMenuItem(barmenu);

		//gestioneUtenti.setGestioneUtentiMenuItem(barmenu);


	}


	/**
	 * set title features
	 */
	private void setTitleLayuot() {

		title = new Label("<font size=\"20\">Biblioteca Angelica</font>", ContentMode.HTML);

		DateTimeLabel dateTimeLabel = new DateTimeLabel(1000,"EEE MMM d HH:mm:ss yyyy");

		titleLayout = new HorizontalLayout(dateTimeLabel,title,loggedUserVerticalLayout);

		titleLayout.setWidth("100%");

		titleLayout.setComponentAlignment(dateTimeLabel, Alignment.MIDDLE_LEFT);

		titleLayout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

		titleLayout.setComponentAlignment(loggedUserVerticalLayout, Alignment.MIDDLE_RIGHT);

		Page.getCurrent().setTitle("Biblioteca Angelica");

	}


}
