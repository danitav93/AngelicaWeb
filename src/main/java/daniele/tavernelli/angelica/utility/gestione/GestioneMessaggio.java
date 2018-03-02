package daniele.tavernelli.angelica.utility.gestione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import daniele.tavernelli.angelica.database.service.RelUtentiCloudMsgTokenService;
import daniele.tavernelli.angelica.firebaseCM.Sender;
import daniele.tavernelli.angelica.utility.broadcast.Broadcaster;
import daniele.tavernelli.angelica.utility.broadcast.MessageBuilder;

@Component
@Scope("singleton")
public class GestioneMessaggio {
	
	@Autowired 
	MessageBuilder messageBuilder;
	
	@Autowired
	RelUtentiCloudMsgTokenService relUtentiCloudMsgTokenService;
	
	@Autowired
	private Sender sender;
	
	public boolean inviaMessaggio(Integer id_mittente,Integer id_destinatario, Integer id_messaggio) {
		
		try {
		Broadcaster.broadcast(messageBuilder.createChatMessage(id_mittente,id_destinatario));
		
        String token = relUtentiCloudMsgTokenService.findTokenByIdUtente(id_destinatario);
        
        if (token!=null) {
        	sender.sendMessage(id_messaggio, token);
        }
		
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

}
