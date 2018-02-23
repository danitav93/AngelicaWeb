package daniele.tavernelli.angelica.rest;

import com.vaadin.spring.annotation.SpringComponent;

import daniele.tavernelli.angelica.database.entity.Collocazione;
import daniele.tavernelli.angelica.rest.model.CollocazioneModel;

@SpringComponent
public class DataConverter {

	public CollocazioneModel getCollocazioneModel(Collocazione collocazione) {

		return new CollocazioneModel(collocazione.getIdCollocazione(),
				collocazione.getCollocazione(),
				collocazione.getPiano(),
				collocazione.getStanza(),
				collocazione.getDenominazione(),
				collocazione.getNote());
	}


}
