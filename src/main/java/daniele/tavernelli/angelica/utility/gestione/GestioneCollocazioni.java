package daniele.tavernelli.angelica.utility.gestione;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import daniele.tavernelli.angelica.database.entity.Collocazione;
import daniele.tavernelli.angelica.database.service.CollocazioneService;
import daniele.tavernelli.angelica.utility.LongNotification;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;

/**
 * this class manages collocazioni
 * @author Daniele Tavernelli
 *
 */
@SpringComponent
@UIScope
public class GestioneCollocazioni implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private CollocazioneService serviceCollocazione;

	@Autowired
	private UI vaadinUI;

	@Autowired
	private UserLogged userLogged;



	private Grid<Collocazione> collocazioneGrid;

	private HeaderRow filterRow;

	private ListDataProvider<Collocazione> collocazioni;

	private Window addCollocazioneSubWindow;

	private Button saveCollocazioneButton;

	private Binder<Collocazione> addCollocazioneBinder;



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() {

		collocazioneGrid = new Grid<Collocazione>(Collocazione.class);

		collocazioneGrid.setWidth("100%");

		collocazioneGrid.setHeightByRows(6);

		collocazioneGrid.setColumnOrder("collocazione","piano","stanza","denominazione","note");

		collocazioneGrid.getColumn("id_collocazione").setHidden(true);

		collocazioneGrid.getEditor().addSaveListener(event -> {
			try {
				serviceCollocazione.update(event.getBean());
				log.info("Collocazione updated: id_collocazione"+event.getBean().getId_collocazione()+" utente_id="+ userLogged.getUtente().getId_utente()+" username="+userLogged.getUtente().getUsername());

			} catch (Exception e) {
				log.error("error Collocazione updated: id_collocazione "+event.getBean().getId_collocazione()+" utente_id="+ userLogged.getUtente().getId_utente()+" username="+userLogged.getUtente().getUsername(),e);
				new LongNotification("Errore durante l'aggiornamento",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());;
			}

		});

		gridCollocazioneBinding();

		setupFilters();

		collocazioneGrid.addColumn( collocazione -> "Rimuovi",
				new ButtonRenderer(clickEvent -> {
					ConfirmDialog.show(vaadinUI, "Attenzione!", "Sei sicuro di voler rimuovere l'elemento selezionato?",
							"SI", "NO", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								try {
									serviceCollocazione.remove((Collocazione)clickEvent.getItem());
									updateCollocazioneGridData();
									log.info("Collocazione deleted: id_collocazione"+((Collocazione)clickEvent.getItem()).getId_collocazione()+" utente_id="+ userLogged.getUtente().getId_utente()+" username="+userLogged.getUtente().getUsername());

								} catch (Exception e) {
									log.error("Errore collocazione deleted: id_collocazione"+((Collocazione)clickEvent.getItem()).getId_collocazione()+" utente_id="+ userLogged.getUtente().getId_utente()+" username="+userLogged.getUtente().getUsername(),e);
									Notification.show("Attenzione!","Errore durante l'eliminazione",Notification.Type.HUMANIZED_MESSAGE);
								}
							} else {

							}
						}
					});

				})).setId("remove").setWidthUndefined();

		collocazioneGrid.getEditor().setEnabled(true);

		updateCollocazioneGridData();

		setAddCollocazioneSubWindow();

	}

	/**
	 * binding grid rows with entity
	 */
	private  void gridCollocazioneBinding() {

		TextField collocazione = new TextField();
		TextField piano = new TextField();
		TextField stanza = new TextField();
		TextField denominazione = new TextField();
		TextField note = new TextField();

		Binder<Collocazione> binder = collocazioneGrid.getEditor().getBinder();

		Binding<Collocazione, String> collocazioneBinding = binder.bind(
				collocazione, Collocazione::getCollocazione, Collocazione::setCollocazione);

		collocazioneGrid.getColumn("collocazione").setEditorBinding(collocazioneBinding);

		Binding<Collocazione, String> pianoBinding = binder.bind(
				piano, Collocazione::getPiano, Collocazione::setPiano);

		collocazioneGrid.getColumn("piano").setEditorBinding(pianoBinding);

		Binding<Collocazione, String> stanzaBinding = binder.bind(
				stanza, Collocazione::getStanza, Collocazione::setStanza);

		collocazioneGrid.getColumn("stanza").setEditorBinding(stanzaBinding);

		Binding<Collocazione, String> denominazioneBinding = binder.bind(
				denominazione, Collocazione::getDenominazione, Collocazione::setDenominazione);

		collocazioneGrid.getColumn("denominazione").setEditorBinding(denominazioneBinding);

		Binding<Collocazione, String> noteBinding = binder.bind(
				note, Collocazione::getNote, Collocazione::setNote);

		collocazioneGrid.getColumn("note").setEditorBinding(noteBinding);


	}


	/**
	 * set ups filters for grid column
	 */
	private  void setupFilters() {

		filterRow = collocazioneGrid.appendHeaderRow();

		HeaderCell cell = filterRow.getCell(collocazioneGrid.getColumn("collocazione"));

		// filtro per collocazione
		TextField filterCollocazione = new TextField();
		filterCollocazione.addStyleName(ValoTheme.TEXTFIELD_TINY);
		// Update filter When the filter input is changed
		filterCollocazione.addValueChangeListener(event -> {
			collocazioni.setFilter(Collocazione::getCollocazione, collocazione -> {
				String nameLower = collocazione == null ? ""
						: collocazione.toLowerCase();
				String filterLower = event.getValue()
						.toLowerCase();
				//cerco anche le collocazioni con i punti invec che con gli spazi e viceversa
				String nameLowerWithPointsInsteadOfWhiteSpaces = event.getValue().replace(" ",".").toLowerCase();
				String nameLowerWithWhiteSpacesInsteadOfPoints = event.getValue().replace("."," ").toLowerCase();
				return nameLower.contains(filterLower) || nameLower.contains(nameLowerWithPointsInsteadOfWhiteSpaces) || nameLower.contains(nameLowerWithWhiteSpacesInsteadOfPoints);
			});
		});
		cell.setComponent(filterCollocazione);

		cell = filterRow.getCell(collocazioneGrid.getColumn("piano"));
		// filtro per Piano
		TextField filterPiano = new TextField();
		filterPiano.addStyleName(ValoTheme.TEXTFIELD_TINY);
		// Update filter When the filter input is changed
		filterPiano.addValueChangeListener(event -> {
			collocazioni.setFilter(Collocazione::getPiano, piano -> {
				String nameLower = piano == null ? ""
						: piano.toLowerCase(Locale.ITALY);
				String filterLower = event.getValue()
						.toLowerCase(Locale.ITALY);
				return nameLower.contains(filterLower);
			});
		});
		cell.setComponent(filterPiano);

		cell = filterRow.getCell(collocazioneGrid.getColumn("stanza"));
		// filtro per Stanza
		TextField filterStanza = new TextField();
		filterStanza.addStyleName(ValoTheme.TEXTFIELD_TINY);
		// Update filter When the filter input is changed
		filterStanza.addValueChangeListener(event -> {
			collocazioni.setFilter(Collocazione::getStanza, stanza -> {
				String nameLower = stanza == null ? ""
						: stanza.toLowerCase(Locale.ITALY);
				String filterLower = event.getValue()
						.toLowerCase(Locale.ITALY);
				return nameLower.contains(filterLower);
			});
		});
		cell.setComponent(filterStanza);

		cell = filterRow.getCell(collocazioneGrid.getColumn("denominazione"));
		// filtro per denominazione
		TextField filterDenominazione = new TextField();
		filterDenominazione.addStyleName(ValoTheme.TEXTFIELD_TINY);
		// Update filter When the filter input is changed
		filterDenominazione.addValueChangeListener(event -> {
			collocazioni.setFilter(Collocazione::getDenominazione, denominazione -> {
				String nameLower = denominazione == null ? ""
						: denominazione.toLowerCase(Locale.ITALY);
				String filterLower = event.getValue()
						.toLowerCase(Locale.ITALY);
				return nameLower.contains(filterLower);
			});
		});
		cell.setComponent(filterDenominazione);

		cell = filterRow.getCell(collocazioneGrid.getColumn("note"));
		// filtro per denominazione
		TextField filterNote = new TextField();
		filterNote.addStyleName(ValoTheme.TEXTFIELD_TINY);
		// Update filter When the filter input is changed
		filterNote.addValueChangeListener(event -> {
			collocazioni.setFilter(Collocazione::getDenominazione, note -> {
				String nameLower = note == null ? ""
						: note.toLowerCase(Locale.ITALY);
				String filterLower = event.getValue()
						.toLowerCase(Locale.ITALY);
				return nameLower.contains(filterLower);
			});
		});
		cell.setComponent(filterNote);

	}

	/**
	 * load data from db and set in grid
	 */
	public  void updateCollocazioneGridData() {

		collocazioni = DataProvider.ofCollection(serviceCollocazione.findAll());

		collocazioneGrid.setDataProvider(collocazioni);

	}

	/**
	 * set gestioneCollocazioniItem
	 * @param vaadinUI
	 * @param barmenu
	 */
	public void setGestioneCollocazioniMenuItem(MenuBar barmenu) {

		//MenuItem gestioneCollocazioniMenuItem = barmenu.addItem("Gestione collocazioni", null);

		//gestioneCollocazioniMenuItem.addItem("Aggiungi collocazione",VaadinIcons.PLUS, sI -> vaadinUI.addWindow(addCollocazioneSubWindow));

		barmenu.addItem("Aggiungi collocazione", VaadinIcons.PLUS, sI -> vaadinUI.addWindow(addCollocazioneSubWindow));
	}

	/**
	 * set subWindow of adding collocazione
	 */
	private void setAddCollocazioneSubWindow() {

		addCollocazioneSubWindow = new Window("Aggiungi una collocazione");

		addCollocazioneSubWindow.setModal(true);

		VerticalLayout addCollocazioneForm = new VerticalLayout();

		TextField collocazione = new TextField("Collocazione");
		TextField piano = new TextField("Piano");
		TextField stanza = new TextField("Stanza");
		TextField denominazione = new TextField("Denominazione");
		TextField note = new TextField("Note");

		addCollocazioneForm.addComponent(collocazione);
		addCollocazioneForm.addComponent(piano);
		addCollocazioneForm.addComponent(stanza);
		addCollocazioneForm.addComponent(denominazione);
		addCollocazioneForm.addComponent(note);

		addCollocazioneForm.setWidthUndefined();


		addCollocazioneBinder = new Binder<>(Collocazione.class);

		addCollocazioneBinder.bind(collocazione,"collocazione");
		addCollocazioneBinder.bind(piano,"piano");
		addCollocazioneBinder.bind(stanza,"stanza");
		addCollocazioneBinder.bind(denominazione,"denominazione");
		addCollocazioneBinder.bind(note,"note");

		addCollocazioneBinder.setBean(new Collocazione());

		saveCollocazioneButton = new Button("Salva",VaadinIcons.CHECK);

		saveCollocazioneButton.addClickListener(clickEvent ->
		{
			if (addCollocazioneBinder.isValid()) {
				try {
					serviceCollocazione.save(addCollocazioneBinder.getBean());
					addCollocazioneSubWindow.close();
					updateCollocazioneGridData();
					new LongNotification("Ok salvataggio Avvenuto con successo",Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
					log.info("Collocazione added: utente_id="+ userLogged.getUtente().getId_utente()+" username="+userLogged.getUtente().getUsername());
					collocazione.setValue("");
					piano.setValue("");
					stanza.setValue("");
					denominazione.setValue("");
					note.setValue("");
				} catch (DataIntegrityViolationException e) { 
					new LongNotification("Attenzione, Collocazione non può essere vuoto",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
				catch (Exception e) {
					new LongNotification("Attenzione, errore durante il salvataggio",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
					log.error("errore Collocazione added: id_collocazione= "+addCollocazioneBinder.getBean().getId_collocazione()+" utente_id="+ userLogged.getUtente().getId_utente()+" username="+userLogged.getUtente().getUsername(),e);

				}
			}
		});

		VerticalLayout layout = new VerticalLayout(addCollocazioneForm,saveCollocazioneButton);

		layout.setComponentAlignment(addCollocazioneForm, Alignment.MIDDLE_CENTER);

		layout.setComponentAlignment(saveCollocazioneButton, Alignment.MIDDLE_CENTER);



		addCollocazioneSubWindow.setWidth("30%");

		addCollocazioneSubWindow.setContent(layout);

		addCollocazioneSubWindow.center();


	}

	public Grid<Collocazione> getCollocazioneGrid() {
		return collocazioneGrid;
	}
}
