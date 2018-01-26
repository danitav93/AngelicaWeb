package daniele.tavernelli.angelica.rest.model;

import java.util.List;

public class ListCollocazioneModel {

	List<CollocazioneModel> listCollocazione;

	
	
	public ListCollocazioneModel() {
		super();
	}

	public ListCollocazioneModel(List<CollocazioneModel> listCollocazione) {
		super();
		this.listCollocazione = listCollocazione;
	}

	public List<CollocazioneModel> getListCollocazione() {
		return listCollocazione;
	}

	public void setListCollocazione(List<CollocazioneModel> listCollocazione) {
		this.listCollocazione = listCollocazione;
	}
	
	
	
}
