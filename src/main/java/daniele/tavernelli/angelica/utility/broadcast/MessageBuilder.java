package daniele.tavernelli.angelica.utility.broadcast;

import java.io.Serializable;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import daniele.tavernelli.angelica.utility.Constants;

@SpringComponent
@UIScope
public class MessageBuilder implements Serializable{

	private static final long serialVersionUID = 1L;

	public String createChatMessage(Long id_mittente,Long id_destinatario) {
		return Constants.TIPO_CHAT+"#"+id_mittente+"#"+id_destinatario;
	}
	
	
	
}
