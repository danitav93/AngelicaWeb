package daniele.tavernelli.angelica.utility.gestione;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.grid.cellrenderers.view.BlobImageRenderer;

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
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;

import daniele.tavernelli.angelica.database.entity.Legenda;
import daniele.tavernelli.angelica.database.service.LegendaService;
import daniele.tavernelli.angelica.utility.LongNotification;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;

/**
 * manage legenda
 * @author Daniele Tavernelli
 *
 */
@SpringComponent
@UIScope
public class GestioneLegenda implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LegendaService serviceLegenda;

	@Autowired
	private UI vaadinUI;

	@Autowired
	private UserLogged userLogged;

	Grid<Legenda> legendaGrid;

	private Window legendaSubWindow;

	private Window addVoceLegendaSubWindow;

	private ListDataProvider<Legenda> legende;

	private Button saveLegendaButton;

	private File file;




	/**
	 * init the grid of legenda
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void init() {


		legendaGrid = new Grid<>(Legenda.class); 

		legendaGrid.getColumn("id_legenda").setHidden(true);

		legendaGrid.getColumn("simbolo").setHidden(true);

		legendaGrid.getEditor().addSaveListener(event -> {
			try {
				serviceLegenda.update(event.getBean());
				log.info("Legenda updated: id_legenda"+event.getBean().getIdLegenda()+" utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());
			} catch (Exception e) {
				e.printStackTrace();
				new LongNotification("Errore durante l'aggiornamento",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());;
				log.error("Legenda updated: id_legenda"+event.getBean().getIdLegenda()+" utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());

			}
		});

		gridLegendaBinding();

		Column<Legenda,byte[]> simboloImageColumn = legendaGrid.addColumn(Legenda::getSimbolo,new BlobImageRenderer(40,40));

		simboloImageColumn.setId("imageSimbolo").setCaption("Simbolo");

		legendaGrid.setColumnOrder("imageSimbolo","codifica");

		legendaGrid.setWidth("90%");

		legendaGrid.addColumn( legenda -> "Rimuovi",
				new ButtonRenderer(clickEvent -> {
					ConfirmDialog.show(vaadinUI, "Attenzione!", "Sei sicuro di voler rimuovere l'elemento selezionato?",
							"SI", "NO", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								try {
									serviceLegenda.remove((Legenda)clickEvent.getItem());
									updateLegendaGridData();
									log.info("Legenda removed: id_legenda"+((Legenda)clickEvent.getItem()).getIdLegenda()+" utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());

								} catch (Exception e) {
									e.printStackTrace();
									log.error("errore Legenda removed: id_legenda"+((Legenda)clickEvent.getItem()).getIdLegenda()+" utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());
									new LongNotification("Errore durante l'eliminazione",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());;
								}
							} else {

							}
						}
					});

				})).setId("remove").setWidthUndefined();

		legendaGrid.getEditor().setEnabled(true);

		updateLegendaGridData();

		setLegendaSubWindow();

		setAddVoceLegendaSubWindow();


	}


	/**
	 * binding fields of grid legenda
	 */
	private void gridLegendaBinding() {

		TextField codifica = new TextField();

		Binder<Legenda> binder = legendaGrid.getEditor().getBinder();

		Binding<Legenda, String> codificaBinding = binder.bind(
				codifica, Legenda::getCodifica, Legenda::setCodifica);

		legendaGrid.getColumn("codifica").setEditorBinding(codificaBinding);

	}

	/**
	 * update legendaGrid Data
	 */
	public void updateLegendaGridData() {

		legende = DataProvider.ofCollection(serviceLegenda.findAll());

		legendaGrid.setDataProvider(legende);

	}



	public Window getLegendaSubWindow() {
		return legendaSubWindow;
	}

	/**
	 * set window of legenda
	 */
	private void setLegendaSubWindow() {

		legendaSubWindow = new Window("Legenda");

		legendaSubWindow.setModal(true);

		Button aggiungiButton = new Button("Aggiungi voce");
		aggiungiButton.addClickListener(e-> {
			vaadinUI.addWindow(addVoceLegendaSubWindow);
		});

		VerticalLayout layout = new VerticalLayout(legendaGrid,aggiungiButton);

		layout.setComponentAlignment(legendaGrid, Alignment.MIDDLE_CENTER);

		legendaSubWindow.setWidth("30%");

		legendaSubWindow.setContent(layout);

		legendaSubWindow.center();

	}



	/*public void setGestioneLegendaMenuItem( MenuBar barmenu) {

		//gestione legenda
		MenuItem gestioneLegendaMenuItem = barmenu.addItem("Gestione legenda", null);
		gestioneLegendaMenuItem.addItem("Aggiungi voce",VaadinIcons.PLUS,sI -> {

		});

	}*/

	/**
	 * set features of form to insert legenda's row
	 */
	private void setAddVoceLegendaSubWindow() {

		addVoceLegendaSubWindow = new Window("Aggiungi una voce alla legenda");

		addVoceLegendaSubWindow.setModal(true);

		VerticalLayout addLegendaForm = new VerticalLayout();

		TextField codifica = new TextField("Codifica");

		class ImageUploader implements Receiver, SucceededListener {

			private static final long serialVersionUID = 1L;



			public OutputStream receiveUpload(String filename,String mimeType) {


				try {
					if (mimeType.equalsIgnoreCase("image/png")||mimeType.equalsIgnoreCase("image/jpeg")) {
						file =File.createTempFile("tempfile", ".tmp");
						new LongNotification("Immagine caricata correttamente",Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
						return new FileOutputStream(file);
					} else {
						new LongNotification("Formato file errato. Caricare solo immagini con estensione \".png\"",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
					}
				} catch (Exception e) {
					e.printStackTrace();
					new LongNotification("Errore durante il caricamento del file",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
				return null;
			}

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				// TODO Auto-generated method stub

			}


		};

		Upload esploraSimbolo = new Upload("Scegli un immagine per il simbolo", new ImageUploader());

		esploraSimbolo.setButtonCaption("Upload");

		addLegendaForm.addComponent(esploraSimbolo);
		addLegendaForm.addComponent(codifica);


		addLegendaForm.setWidthUndefined();

		saveLegendaButton = new Button("Salva",VaadinIcons.CHECK);

		saveLegendaButton.addClickListener(clickEvent ->
		{
			try {
				if (file==null) {
					new LongNotification("Selezionare un immagine", Notification.Type.WARNING_MESSAGE).show(Page.getCurrent());
					return;
				}
				if (codifica.getValue()==null || codifica.getValue().trim().length()==0) {
					new LongNotification("Inserire una codifica", Notification.Type.WARNING_MESSAGE).show(Page.getCurrent());
					return;
				}
				Legenda legenda = new Legenda();
				legenda.setSimbolo(Files.readAllBytes(file.toPath()));
				legenda.setCodifica(codifica.getValue());			
				if (isValid(legenda)) {

					serviceLegenda.save(legenda);
					addVoceLegendaSubWindow.close();
					updateLegendaGridData();
					new LongNotification("Salvataggio avvenuto con successo",Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
					log.info("Legenda saved: utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());
					file=null;
					codifica.setValue("");
				}
			} catch (Exception e) {
				e.printStackTrace();
				new LongNotification("Errore durante il salvataggio",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				log.error("utente_id="+ userLogged.getUtente().getIdUtente()+" username="+userLogged.getUtente().getUsername());

			}
		});

		VerticalLayout layout = new VerticalLayout(addLegendaForm,saveLegendaButton);

		layout.setComponentAlignment(addLegendaForm, Alignment.MIDDLE_CENTER);

		layout.setComponentAlignment(saveLegendaButton, Alignment.MIDDLE_CENTER);



		addVoceLegendaSubWindow.setWidth("30%");

		addVoceLegendaSubWindow.setContent(layout);

		addVoceLegendaSubWindow.center();

	}




	private boolean isValid(Legenda legenda) {
		return true;
	}
}
