package daniele.tavernelli.angelica.vaadin;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import daniele.tavernelli.angelica.utility.Constants;
import daniele.tavernelli.angelica.utility.broadcast.Broadcaster;
import daniele.tavernelli.angelica.utility.broadcast.Broadcaster.BroadcastListener;
import daniele.tavernelli.angelica.utility.broadcast.Receiver;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;



/**
 *	first class that respond to the application
 * @author Daniele Tavernelli
 *
 */
@Theme("mytheme")
@SpringUI
@SpringViewDisplay
@PreserveOnRefresh
@Push
public class Ui extends UI implements ViewDisplay,View,ViewAccessControl,BroadcastListener {


	@Autowired
	UserLogged userLogged;

	@Autowired
	private BeanFactory beanFactory;

	private static final long serialVersionUID = 1L;

	private Panel springViewDisplay;

	public Button logInButton = createNavigationButton("Accedi",VaadinLogInView.NAME);

	public Button infoButton = createNavigationButton("Info",InfoView.NAME);

	private  VerticalLayout root;


	@Override
	protected void init(VaadinRequest request) {

		/*if (VaadinSession.getCurrent().getAttribute("hasUI") != null) {
			setContent(new Label("Use only one tab!"));
			return;
		}

		VaadinSession.getCurrent().setAttribute("hasUI", Boolean.TRUE);*/

		// ... create your ui ...

		root = new VerticalLayout();
		root.setSizeFull();
		setContent(root);

		final CssLayout navigationBar = new CssLayout();

		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		navigationBar.addComponent(logInButton);

		navigationBar.addComponent(infoButton);
		
		root.addComponent(new Label("<p style=\"text-align: center;\"><span style=\"font-family: 'arial black', 'avant garde'; font-size: 8pt;\">Versione "+Constants.desc_versione+"</span></p>",ContentMode.HTML));

		root.addComponent(navigationBar);
		
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();

		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

		getNavigator().addView("", this);

		getNavigator().setErrorView(Ui.class);

		//register for broadcast
		Broadcaster.register(this);

	}



	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {

		logInButton.setVisible(true);

		Label title=new Label("<p style=\"text-align: center;\"><span style=\"font-family: 'arial black', 'avant garde';\"><strong><span style=\"font-size: 48pt;\">BIBLIOTECA ANGELICA</span></strong></span></p>"
				+"<p style=\"text-align: center;\"><span style=\"font-family: 'arial black', 'avant garde'; font-size: 20pt;\"><strong>Gestione delle collocazioni</strong></span></p>"
				,ContentMode.HTML);
		title.setSizeFull();
		
		springViewDisplay.setContent(title);


	}


	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}

	@Override
	public void showView(View view) {
		try {
			try {
				if ((MainView)view!=null) {
					setContent((Component) view);
				}
			} catch (Exception e) {
				setContent(root);
				springViewDisplay.setContent((Component) view);
			}
		}catch (Exception e) {

		}

	}

	@Override
	public boolean isAccessGranted(UI arg0, String arg1) {

		switch (arg1) {

		case "mainView": 
			if (userLogged.getViewUtente()!=null) {
				return true;
			} else {
				return false;
			}
		case "vaadinLogInView":
			return true;

		case "infoView":
			return true;
		}

		return false;
	}

	@Override
	public void receiveBroadcast(String message) {

		access(new Runnable() {
			@Override
			public void run() {
				Receiver receiver=beanFactory.getBean(Receiver.class);
				receiver.receiveMessage(message);
			}
		});

	}

	// Must also unregister when the UI expires
	@Override
	public void detach() {
		Broadcaster.unregister(this);
		super.detach();
	}

	/**
	 * logs out from application
	 */
	public void logOut() {

		getNavigator().navigateTo("");

		//Page.getCurrent().getStyles().add(".v-Notification-system { visibility: hidden !important; }");

		getSession().close();


	}

}
