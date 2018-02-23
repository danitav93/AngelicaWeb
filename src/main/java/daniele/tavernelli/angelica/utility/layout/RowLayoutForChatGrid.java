package daniele.tavernelli.angelica.utility.layout;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;

import daniele.tavernelli.angelica.database.entity.Messaggio;
import daniele.tavernelli.angelica.utility.gestione.logIn.UserLogged;

/**
 *layout for row of chat messages 
 * @author Daniele Tavernelli
 *
 */
@SpringComponent
@Scope("prototype")
@Lazy(value = true)
public class RowLayoutForChatGrid extends Label {

	private static final long serialVersionUID = 1L;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");

	@Autowired
	UserLogged userLogged;

	private Messaggio messaggio;

	public  RowLayoutForChatGrid(Messaggio messaggio) {
		this.messaggio=messaggio;
		setContentMode(ContentMode.HTML);
	}

	@PostConstruct
	void init() {
				
		setWidth("100%");
		
		String align="left";
		
		String color="#000000";

		if (messaggio.getIdMittente()==userLogged.getUtente().getIdUtente()) {
			align="right";
			color="#003399";
		} 

		setValue(
				"<body><div align=\""+align+"\">"+
						"<textarea disabled name=\"Text1\" cols=\"21\" rows=\"1\" style=\"background: #FFF;font-family:Times New Roman;color:"+color+";font-size: 12px;border: none;resize: none;\">"+
						dateFormat.format(messaggio.getData())+
						"</textarea>"+
						"</div>"+
						"<div align=\""+align+"\" >"+
						"<textarea disabled name=\"Text1\" cols=\"30\" rows=\"4\" style=\"background: #FFF;font-family:Times New Roman;color:"+color+";font-size: 14px;border: none;resize: none;\">"+ 
						messaggio.getBody()+
						"</textarea>"+
						"</div></body>");

	}


}

