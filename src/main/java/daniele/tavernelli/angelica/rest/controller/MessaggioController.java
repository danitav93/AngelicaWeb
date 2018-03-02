package daniele.tavernelli.angelica.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import daniele.tavernelli.angelica.database.entity.Messaggio;
import daniele.tavernelli.angelica.database.service.MessaggioService;
import daniele.tavernelli.angelica.utility.broadcast.MessageBuilder;
import daniele.tavernelli.angelica.utility.gestione.GestioneMessaggio;

@RestController
public class MessaggioController {
	
	private final String START_URI = "messaggio";
	
	
	@Autowired
	private MessaggioService messaggioService;
	
	@Autowired 
	MessageBuilder messageBuilder;
	
	@Autowired
	GestioneMessaggio gestioneMessaggio;
	
	
	
	
	@RequestMapping(value=START_URI+"/chat",method=RequestMethod.GET)
	public @ResponseBody List<Messaggio> getChat(
			@RequestParam(name = "page", defaultValue = "1") int pageNumber,
			@RequestParam(name = "size", defaultValue = "50") int pageSize,
			@RequestParam(name = "id_mittente") int id_mittente,
			@RequestParam(name = "id_destinatario") int id_destinatario) {
 
		return  messaggioService.getPage(pageNumber,pageSize,id_mittente,id_destinatario);	
	}
	
	@RequestMapping(value=START_URI+"/save",method=RequestMethod.POST)
	public ResponseEntity<Boolean> login(@RequestBody Messaggio messaggio){
		
		try {
			messaggio= messaggioService.save(messaggio);
			gestioneMessaggio.inviaMessaggio(messaggio.getIdMittente(), messaggio.getIdDestinatario(), messaggio.getIdMessaggio());
	        return ResponseEntity.status(HttpStatus.OK).body(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}
	
	@RequestMapping(value=START_URI+"/get",method=RequestMethod.GET)
	public @ResponseBody Messaggio get(
			@RequestParam(name = "id_messaggio", defaultValue = "1") int id_messaggio
			) {
 
		return  messaggioService.getMessaggio(id_messaggio);	
	}
	
	@RequestMapping(value=START_URI+"/setMessaggioLetto",method=RequestMethod.POST)
	public ResponseEntity<Boolean> setMessaggioLetto(@RequestBody List<Integer> messaggiIds){
		
		try {
			Messaggio messaggio;
			for (Integer id: messaggiIds) {
				messaggio=messaggioService.find(id);
				messaggio.setLetto(1);
				messaggioService.save(messaggio);
			}
	        return ResponseEntity.status(HttpStatus.OK).body(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

}
