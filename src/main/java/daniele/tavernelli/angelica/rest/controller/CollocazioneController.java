package daniele.tavernelli.angelica.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import daniele.tavernelli.angelica.database.entity.Collocazione;
import daniele.tavernelli.angelica.database.service.CollocazioneService;

@RestController
public class CollocazioneController {
	
	private final String START_URI = "collocazione";

	@Autowired
	private CollocazioneService collocazioneService;


	@RequestMapping(value=START_URI,method=RequestMethod.GET)
	public @ResponseBody List<Collocazione> viewCustomers(
			@RequestParam(name = "page", defaultValue = "1") int pageNumber,
			@RequestParam(name = "size", defaultValue = "10") int pageSize,
			@RequestParam(name = "collocazione", defaultValue = "") String collocazione) {
 
		return  collocazioneService.getPage(pageNumber,pageSize,collocazione);
 
		
	}




}
