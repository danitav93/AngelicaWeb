package daniele.tavernelli.angelica.vaadin;


import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = InfoView.NAME,ui={Ui.class})
@UIScope
public class InfoView extends VerticalLayout implements View{

	private static final long serialVersionUID = 4142849464302963100L;

	public final static String NAME = "infoView";

	@PostConstruct
	void init() {

		Label info = new Label("<p style=\"text-align: center;\"><span style=\"font-family: 'arial black', 'avant garde'; font-size: 20pt;\"><strong>Questo software libero &egrave; stato progettato senza scopo di lucro. Un ringraziamento speciale va alla signora NOME SIGNORA il lavoro della quale ha inspirato questa applicazione.&nbsp;</strong></span></p>"
				+"<p style=\"text-align: left;\"><span style=\"font-family: 'arial black', 'avant garde'; font-size: 15pt;\"><strong>Per suggerimenti e segnalazioni di bug scrivere ai seguenti riferimenti:</strong></span></p>"
				+"<p><span style=\"font-family: 'arial black', 'avant garde'; font-size: 15pt;\">Ivonne Lo Russo: ivilorusso@gmail.com</span></p>"
				+"<p><span style=\"font-family: 'arial black', 'avant garde'; font-size: 15pt;\">Daniele Tavernelli: tavernelli.daniele@gmail.com</span></p>"
				+"<p>&nbsp;</p>",ContentMode.HTML);

		info.setSizeFull();
		
		addComponent(info);

	}

}
