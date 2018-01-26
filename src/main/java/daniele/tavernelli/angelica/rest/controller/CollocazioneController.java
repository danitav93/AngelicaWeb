package daniele.tavernelli.angelica.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import daniele.tavernelli.angelica.database.entity.Collocazione;
import daniele.tavernelli.angelica.database.service.CollocazioneService;
import daniele.tavernelli.angelica.rest.DataConverter;
import daniele.tavernelli.angelica.rest.model.ListCollocazioneModel;

@Controller
@RequestMapping("/collocazione")
public class CollocazioneController {
	
	@Autowired
	DataConverter dataConverter;
	
	@Autowired
	CollocazioneService collocazioneService;

	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody ListCollocazioneModel getCollocazione(@RequestParam(value="collocazione",required=true)String collocazione) {
		
		try {
			
		List<Collocazione> collocazioneEntityList= collocazioneService.findCollocazioneByContieneCollocazione(collocazione);
		
		ListCollocazioneModel listCollocazioneModel = new ListCollocazioneModel();
		
		listCollocazioneModel.setListCollocazione(new ArrayList<>());
		
		for (Collocazione collocazioneEntity: collocazioneEntityList) {
			listCollocazioneModel.getListCollocazione().add(dataConverter.getCollocazioneModel(collocazioneEntity));
		}
		
		return listCollocazioneModel;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
}
