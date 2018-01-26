package daniele.tavernelli.angelica.rest.model;

public class CollocazioneModel {
private long id_collocazione;
	
	private String collocazione,piano,stanza,denominazione,note;

	
	
	public CollocazioneModel() {
	}



	public CollocazioneModel(String collocazione, String piano, String stanza,
			String denominazione, String note) {
		super();
		this.collocazione = collocazione;
		this.piano = piano;
		this.stanza = stanza;
		this.denominazione = denominazione;
		this.note = note;
	}
	
	

	public CollocazioneModel(long id_collocazione, String collocazione,
			String piano, String stanza, String denominazione,
			String note) {
		super();
		this.id_collocazione = id_collocazione;
		this.collocazione = collocazione;
		this.piano = piano;
		this.stanza = stanza;
		this.denominazione = denominazione;
		this.note = note;
	}



	public long getId_collocazione() {
		return id_collocazione;
	}

	public void setId_collocazione(long id_collocazione) {
		this.id_collocazione = id_collocazione;
	}

	public String getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(String collocazione) {
		this.collocazione = collocazione;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getStanza() {
		return stanza;
	}

	public void setStanza(String stanza) {
		this.stanza = stanza;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
