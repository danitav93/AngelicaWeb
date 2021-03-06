package daniele.tavernelli.angelica.utility.gestione;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;

import daniele.tavernelli.angelica.database.entity.Ruolo;
import daniele.tavernelli.angelica.database.entity.Utente;
import daniele.tavernelli.angelica.database.entity.ViewUtente;
import daniele.tavernelli.angelica.database.service.MessaggioService;
import daniele.tavernelli.angelica.database.service.RuoloService;
import daniele.tavernelli.angelica.database.service.UtenteService;
import daniele.tavernelli.angelica.database.service.ViewUtenteService;
import daniele.tavernelli.angelica.utility.Constants;
import daniele.tavernelli.angelica.utility.LoggedUserVerticalLayout;
import daniele.tavernelli.angelica.utility.LongNotification;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;
import daniele.tavernelli.angelica.utility.layout.ButtonForChat;
import daniele.tavernelli.angelica.vaadin.Ui;

/**
 * manage utenti
 * 
 * @author Daniele Tavernelli
 *
 */

@SpringComponent
@UIScope
public class GestioneUtenti implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;

	@Autowired
	private MessaggioService messaggioService;

	@Autowired
	private ViewUtenteService viewUtenteService;

	@Autowired
	private UserLogged userLogged;

	@Autowired
	Ui vaadinUI;

	@Autowired
	private BeanFactory beanFactory;

	Grid<ViewUtente> utenteGrid;

	private HorizontalLayout chatLayout;

	private List<Ruolo> ruoli;

	private ListDataProvider<ViewUtente> utenti;

	private Window utentiSubWindow;

	private Window addUtenteSubWindow;

	private Button saveUtenteButton;

	public HashMap<Integer, ButtonForChat> buttonForChatList;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void init() {

		ruoli = this.ruoloService.findAll();

		utenteGrid = new Grid<>(ViewUtente.class);

		utenteGrid.setWidth("100%");

		utenteGrid.getColumn("idUtente").setHidden(true);

		utenteGrid.getEditor().addSaveListener(
				event -> {

					String errore = checkUtente(event.getBean());
					if (errore == null
							&& this.viewUtenteService.update(event.getBean())) {
						log.info("utente update: idUtente_updated "+event.getBean().getIdUtente()+" utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());
						new LongNotification(
								"Aggiornamento avvenuto con successo!",
								Notification.Type.HUMANIZED_MESSAGE).show(Page
										.getCurrent());
						updateUtenteGridData();
					} else {
						if (errore == null) {
							errore = "Errore durante l'aggiornamento";
						}
						log.error("errore utente update: idUtente_updated "+event.getBean().getIdUtente()+" utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());
						new LongNotification(errore,
								Notification.Type.ERROR_MESSAGE).show(Page
										.getCurrent());
					}
				});

		gridUtenteBinding();

		utenteGrid
		.addColumn(
				utente -> "Rimuovi",
				new ButtonRenderer(
						clickEvent -> {
							ConfirmDialog.show(vaadinUI, "Attenzione!", "Sei sicuro di voler rimuovere l'elemento selezionato?",
									"SI", "NO", new ConfirmDialog.Listener() {
								private static final long serialVersionUID = 1L;

								public void onClose(ConfirmDialog dialog) {
									if (dialog.isConfirmed()) {
										try {
											removeUtente((ViewUtente) clickEvent
													.getItem());
											updateUtenteGridData();
											new LongNotification("Eliminazione avvenuta con successo",Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());;
											log.info(" utente removed: idUtente_removed "+((ViewUtente) clickEvent
													.getItem()).getIdUtente()+" utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());

										} catch (Exception e) {
											e.printStackTrace();
											new LongNotification("Errore durante l'eliminazione",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());;
											log.error("errore utente update: idUtente_updated "+((ViewUtente) clickEvent
													.getItem()).getIdUtente()+" utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());

										}
									} else {
										//do nothing
									}
								}
							});

						})).setId("remove").setWidthUndefined();

		utenteGrid
		.addColumn(
				utente -> "Messaggia",
				new ButtonRenderer(clickEvent -> {

					try {
						if (((ViewUtente) clickEvent.getItem()).getIdUtente()!=userLogged.getUtente().getIdUtente()) {
							addChatButton((ViewUtente) clickEvent.getItem());
						}
					} catch (Exception e) {
						e.printStackTrace();
						Notification.show("Attenzione!",
								"Errore durante l'eliminazione",
								Notification.Type.HUMANIZED_MESSAGE);
					}
				})).setId("chat").setWidthUndefined();

		utenteGrid.getEditor().setEnabled(true);

		utenteGrid.getColumn("idRuolo").setHidden(true);

		utenteGrid.setColumnOrder("username", "password", "nomeRuolo","descFunc",
				"remove");

		updateUtenteGridData();

		setUtenteSubWindow();

		setAddUtenteSubWindow();

		initChatLayout();

	}

	/**
	 * init bar of chat
	 */
	private void initChatLayout() {

		chatLayout = new HorizontalLayout();

	}

	public void addChatButton(ViewUtente item) {

		if (buttonForChatList == null) {
			buttonForChatList = new HashMap<Integer, ButtonForChat>();
		}

		if (!buttonForChatList.keySet().contains(item.getIdUtente())) {

			ButtonForChat buttonForChat = beanFactory.getBean(
					ButtonForChat.class, item);

			chatLayout.addComponent(buttonForChat);

			buttonForChatList.put(item.getIdUtente(), buttonForChat);

		}

		if (messaggioService.thereIsNewMessagge(userLogged.getUtente().getIdUtente(),item.getIdUtente())) {
			buttonForChatList.get(item.getIdUtente()).utenteName.setIcon(VaadinIcons.EXCLAMATION);
		} else {
			buttonForChatList.get(item.getIdUtente()).utenteName.setIcon(null);
		}

	}

	/**
	 * form to ad utente
	 */
	private void setAddUtenteSubWindow() {

		addUtenteSubWindow = new Window("Crea un utente");

		addUtenteSubWindow.setModal(true);

		VerticalLayout addUtenteForm = new VerticalLayout();

		TextField usernameTxtField = new TextField("Username");

		TextField passwordTxtField = new TextField("Password");

		ComboBox<Ruolo> ruoloCombo = new ComboBox<Ruolo>("Ruolo");

		ruoloCombo.setItems(ruoli);

		ruoloCombo.setItemCaptionGenerator(Ruolo::getNome);

		addUtenteForm.addComponent(usernameTxtField);

		addUtenteForm.addComponent(passwordTxtField);

		addUtenteForm.addComponent(ruoloCombo);

		addUtenteForm.setWidthUndefined();

		saveUtenteButton = new Button("Salva", VaadinIcons.CHECK);

		saveUtenteButton.addClickListener(clickEvent -> {
			try {

				ViewUtente viewUtente = new ViewUtente();
				viewUtente.setIdRuolo(ruoloCombo.getSelectedItem()
						.get().getIdRuolo());
				viewUtente.setUsername(usernameTxtField.getValue());
				viewUtente.setPassword(passwordTxtField.getValue());
				viewUtente.setNomeRuolo(ruoloCombo
						.getSelectedItem().get().getNome());
				
				String errore = checkUtente(viewUtente);

				if (errore == null) {

					Ruolo ruolo = new Ruolo();
					ruolo.setIdRuolo(ruoloCombo.getSelectedItem()
							.get().getIdRuolo());
					ruolo.setNome(ruoloCombo
							.getSelectedItem().get().getNome());
					
					Utente utente = new Utente();
					utente.setRuolo(ruolo);;
					utente.setUsername(usernameTxtField.getValue());
					utente.setPassword(passwordTxtField.getValue());
					
					
					utenteService.save(utente);
					
					addUtenteSubWindow.close();
					updateUtenteGridData();
					
					log.info("utente save: utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());

					new LongNotification("Salvataggio avvenuto con successo",
							Notification.Type.HUMANIZED_MESSAGE).show(Page
									.getCurrent());
					usernameTxtField.setValue("");
					passwordTxtField.setValue("");
					ruoloCombo.setSelectedItem(null);

				} else {
					new LongNotification(errore,
							Notification.Type.ERROR_MESSAGE).show(Page
									.getCurrent());
				}

			} catch (NoSuchElementException e) {
				new LongNotification("Selezionare un ruolo",
						Notification.Type.WARNING_MESSAGE).show(Page
								.getCurrent());
				return;
			} catch (Exception e) {
				e.printStackTrace();
				log.info("utente save: username="+userLogged.getUtente().getUsername());
				Notification.show("Error", "Errore durante il salvataggio",
						Notification.Type.HUMANIZED_MESSAGE);
			}
		});

		VerticalLayout layout = new VerticalLayout(addUtenteForm,
				saveUtenteButton);

		layout.setComponentAlignment(addUtenteForm, Alignment.MIDDLE_CENTER);

		layout.setComponentAlignment(saveUtenteButton, Alignment.MIDDLE_CENTER);

		addUtenteSubWindow.setWidth("30%");

		addUtenteSubWindow.setContent(layout);

		addUtenteSubWindow.center();

	}

	/**
	 * set window of utenti
	 */
	private void setUtenteSubWindow() {

		utentiSubWindow = new Window("Utenti");

		utentiSubWindow.setModal(true);

		Button aggiungiUtente = new Button("Aggiungi utente");

		aggiungiUtente.addClickListener(e-> {
			vaadinUI.addWindow(addUtenteSubWindow);
		});

		VerticalLayout layout = new VerticalLayout(utenteGrid,aggiungiUtente);

		layout.setComponentAlignment(utenteGrid, Alignment.MIDDLE_CENTER);

		utentiSubWindow.setWidth("90%");

		utentiSubWindow.setContent(layout);

		utentiSubWindow.center();

	}

	/**
	 * update data of grid
	 */
	private void updateUtenteGridData() {

		utenti = DataProvider.ofCollection(viewUtenteService.findAll());

		utenteGrid.setDataProvider(utenti);
	}

	/**
	 * remove from tab utente the associated bean
	 * 
	 * @param item
	 */
	private void removeUtente(ViewUtente item) {
		Utente utente = new Utente();
		utente.setIdUtente(item.getIdUtente());
		utenteService.remove(utente);
	}

	/**
	 * This method say if user's fields are ok
	 * 
	 * @param bean
	 */
	private String checkUtente(ViewUtente bean) {

		String errore = null;

		if (!usernameIsOk(bean.getUsername())) {
			errore = Constants.usernameRule;
		}

		if (!passwordIsOk(bean.getPassword())) {
			errore = Constants.passwordRule;
		}

		if (!ruoloIsOk(bean.getNomeRuolo())) {
			errore = Constants.ruoloRule;
		} else {
			updateBeanCodiRole(bean.getNomeRuolo(), bean);
		}

		return errore;
	}

	/**
	 * update id_ruolo of bean based on nome ruolo
	 * 
	 * @param nomeRuolo
	 */
	private void updateBeanCodiRole(String nomeRuolo, ViewUtente bean) {
		for (Ruolo ruolo : ruoli) {
			if (ruolo.getNome().equals(nomeRuolo)) {
				bean.setIdRuolo(ruolo.getIdRuolo());
				return;
			}
		}
	}

	/**
	 * rule of ruolo
	 * 
	 * @param nomeRuolo
	 * @return
	 */
	private boolean ruoloIsOk(String nomeRuolo) {
		for (Ruolo ruolo : ruoli) {
			if (ruolo.getNome().equals(nomeRuolo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * password rule
	 * 
	 * @param password
	 * @return
	 */
	private boolean passwordIsOk(String password) {
		return password.length() > 4;
	}

	/**
	 * rule of username
	 * 
	 * @param username
	 * @return
	 */
	private boolean usernameIsOk(String username) {
		return username.length() > 3;
	}

	/**
	 * binding fields of grid utente
	 */
	private void gridUtenteBinding() {

		Binder<ViewUtente> binder = utenteGrid.getEditor().getBinder();

		TextField usernameTxtField = new TextField();

		Binding<ViewUtente, String> usernameBinding = binder.bind(
				usernameTxtField, ViewUtente::getUsername,
				ViewUtente::setUsername);

		utenteGrid.getColumn("username").setEditorBinding(usernameBinding);

		TextField passwordTxtField = new TextField();

		Binding<ViewUtente, String> passwordBinding = binder.bind(
				passwordTxtField, ViewUtente::getPassword,
				ViewUtente::setPassword);

		utenteGrid.getColumn("password").setEditorBinding(passwordBinding);

		TextField roleTxtField = new TextField();

		Binding<ViewUtente, String> roleBinding = binder.bind(roleTxtField,
				ViewUtente::getNomeRuolo, ViewUtente::setNomeRuolo);

		utenteGrid.getColumn("nomeRuolo").setEditorBinding(roleBinding);

	}

	public Window getUtentiSubWindow() {
		return utentiSubWindow;
	}

	/**
	 * voce gestione utenti in menu bar
	 * 
	 * @param vaadinUI
	 * @param barmenu
	 */
	/*public void setGestioneUtentiMenuItem(MenuBar barmenu) {

		// gestione legenda
		MenuItem gestioneLegendaMenuItem = barmenu.addItem("Gestione Utenti",
				null);
		gestioneLegendaMenuItem.addItem("Aggiungi utente", VaadinIcons.PLUS,
				sI -> {
					vaadinUI.addWindow(addUtenteSubWindow);
				});

	}
	 */
	/**
	 * init the menu Bar buttons of the logged user
	 */
	public void initLoggedUserMenuBar(
			LoggedUserVerticalLayout loggedUserVerticalLayout) {

		loggedUserVerticalLayout.getUserItem().addItem("Esci",
				VaadinIcons.EXIT, sI -> {
					vaadinUI.logOut();
				});

	}



	public HorizontalLayout getChatLayout() {
		return chatLayout;
	}

	public void setChatLayout(HorizontalLayout chatLayout) {
		this.chatLayout = chatLayout;
	}

}
