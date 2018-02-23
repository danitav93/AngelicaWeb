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

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;

import daniele.tavernelli.angelica.database.entity.RelUtentiCloudMsgToken;
import daniele.tavernelli.angelica.database.entity.ViewUtente;
import daniele.tavernelli.angelica.database.repository.RelUtentiCloudMsgTokenRepository;
import daniele.tavernelli.angelica.database.service.ViewUtenteService;
import daniele.tavernelli.angelica.utility.Constants;

@RestController
public class UtenteController {
	
	private final String START_URI = "utente";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ViewUtenteService viewUtenteService;
	
	@Autowired
	private RelUtentiCloudMsgTokenRepository relUtentiCloudMsgTokenRepository;

	@RequestMapping(value=START_URI+"/login",method=RequestMethod.POST)
	public ResponseEntity<ViewUtente> login(@RequestBody ViewUtente viewUtente){
		
		ViewUtente viewUtenteFound= viewUtenteService.findByUsername(viewUtente.getUsername());
		
		if (viewUtenteFound!=null && viewUtenteFound.getPassword().equals(viewUtente.getPassword())) {
			
			log.info("Login: ok userId: "+viewUtenteFound.getIdUtente());

			return ResponseEntity.status(HttpStatus.OK).body(viewUtenteFound);
			
		} else {
			
			return ResponseEntity.noContent().build();
			
		}
		
	}
	
	
	@RequestMapping(value=START_URI+"/refreshToken",method=RequestMethod.POST)
	public ResponseEntity<ViewUtente> refreshToken(
			@RequestParam(name = "refreshToken") String refreshToken,
			@RequestParam(name = "idUtente") int idUtente){
		
			RelUtentiCloudMsgToken relUtentiCloudMsgToken= new RelUtentiCloudMsgToken();
			relUtentiCloudMsgToken.setToken(refreshToken);
			relUtentiCloudMsgToken.setU();
		
		
			
			return ResponseEntity.noContent().build();
			
		
		
	}
	
	
	@RequestMapping(value=START_URI+"/listForMessage",method=RequestMethod.GET)
	public @ResponseBody List<ViewUtente> getListForMessage(
			@RequestParam(name = "id_utente") int id_utente) {
 
		
		ViewUtente viewUtente = viewUtenteService.findByIdUtente(id_utente);
		switch ((int)(long)viewUtente.getIdRuolo()) {
		
			case Constants.UTENTE_ESTERNO:
				return  viewUtenteService.getListByRuoloExceptThis(Constants.UTENTE_INTERNO,id_utente);
				
				
		}
		return viewUtenteService.findAllExceptThis(id_utente);
 
		
	}
	
	
	
}

