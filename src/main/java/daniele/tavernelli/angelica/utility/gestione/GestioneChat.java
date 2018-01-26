package daniele.tavernelli.angelica.utility.gestione;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import daniele.tavernelli.angelica.database.entity.Messaggio;
import daniele.tavernelli.angelica.database.service.MessaggioService;
import daniele.tavernelli.angelica.database.view.ViewUtente;
import daniele.tavernelli.angelica.utility.LongNotification;
import daniele.tavernelli.angelica.utility.broadcast.Broadcaster;
import daniele.tavernelli.angelica.utility.broadcast.MessageBuilder;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;
import daniele.tavernelli.angelica.utility.layout.RowLayoutForChatGrid;

/**
 * chat window
 * @author Daniele Tavernelli
 *
 */
@SpringComponent
@VaadinSessionScope
@Theme("mytheme")
public class GestioneChat extends Window{

	private static final long serialVersionUID = 1L;

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private MessaggioService messaggioService;

	@Autowired
	private UserLogged userLogged;
	
	@Autowired 
	MessageBuilder messageBuilder;

	public ViewUtente other;

	private Label name = new Label();

	private ListDataProvider<Messaggio> messaggi;

	public Grid<Messaggio> messaggiGrid;

	private TextField textMessaggio = new TextField("Scrivi il tuo messaggio");

	private Button inviaButton = new Button(VaadinIcons.ARROW_RIGHT);





	@PostConstruct
	public void init() {

		messaggiGrid = new Grid<>(Messaggio.class);

		center();	

		HorizontalLayout messaggioLayout = new HorizontalLayout(textMessaggio,inviaButton);

		inviaButton.setWidth("100%");
		
		inviaButton.setClickShortcut(KeyCode.ENTER);

		textMessaggio.setWidth("450px");

		messaggioLayout.setExpandRatio(inviaButton,1.0f);

		messaggioLayout.setSpacing(false);

		messaggioLayout.setWidth("500px");

		messaggioLayout.setComponentAlignment(inviaButton, Alignment.BOTTOM_RIGHT);

		VerticalLayout verticalLayout = new VerticalLayout(name,messaggiGrid,messaggioLayout);

		messaggiGrid.removeAllColumns();


		messaggiGrid.setWidth("500px");

		messaggiGrid.addComponentColumn(message -> {
			return beanFactory.getBean(RowLayoutForChatGrid.class,message);
		}).setId("1column").setResizable(false).setWidth(messaggiGrid.getWidth()-20);

		messaggiGrid.setRowHeight(120);

		messaggiGrid.addStyleName("no-stripes");

		messaggiGrid.addStyleName("v-grid");

		messaggiGrid.setStyleGenerator(messaggio -> "v-no-border");

		messaggiGrid.setHeaderVisible(false);

		messaggiGrid.setSelectionMode(SelectionMode.NONE);

		setContent(verticalLayout);

		inviaButton.addClickListener(f -> {

			try {
				messaggioService.save(new Messaggio(userLogged.getUtente().getId_utente(),other.getId_utente(),new Date(),textMessaggio.getValue(),0));
				textMessaggio.setValue("");
				// Broadcast the message
		        Broadcaster.broadcast(messageBuilder.createChatMessage(userLogged.getUtente().getId_utente(),other.getId_utente()));
				if (other!=null) {
					updateChatGridData(other);
				}
			} catch (Exception e) {
				e.printStackTrace();
				new LongNotification("Non è stato possibile inviare il messaggio riprovare",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
			}

		});




	}

	/**
	 * update data of grid
	 */
	public void updateChatGridData(ViewUtente other) {

		setCaption("Chat con "+other.getUsername());

		this.other = other;
		
		List<Messaggio> messaggiList;
		
		if (messaggi==null || messaggi.getItems()==null || messaggi.getItems().isEmpty()) {
		
			messaggiList= messaggioService.findByUtentiInChat(userLogged.getUtente().getId_utente(),other.getId_utente());
			
			messaggi = DataProvider.ofCollection(messaggiList);

			
		} else {
			 messaggiList=messaggioService.findNewMessaggiRicevutiEInviati(userLogged.getUtente().getId_utente(),other.getId_utente());
			 messaggi.getItems().addAll(messaggiList);
		}

		for (Messaggio messaggio: messaggiList) {
			if (messaggio.getLetto()==0 && messaggio.getId_destinatario()==userLogged.getUtente().getId_utente()) {
				messaggio.setLetto(1);
				messaggioService.update(messaggio);
			}
		}
		
		messaggi.setSortOrder(m->m.getData(), SortDirection.ASCENDING);
		
		messaggiGrid.setDataProvider(messaggi);
		
		messaggiGrid.scrollToEnd();
	}

	

}
