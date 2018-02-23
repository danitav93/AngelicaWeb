package daniele.tavernelli.angelica.utility.broadcast;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;

import daniele.tavernelli.angelica.utility.Constants;

@SpringComponent
@Scope("singleton")
public class MessageBuilder implements Serializable{

	private static final long serialVersionUID = 1L;

	public String createChatMessage(int id_mittente,int id_destinatario) {
		return Constants.TIPO_CHAT+"#"+id_mittente+"#"+id_destinatario;
	}
	
	
	
}
