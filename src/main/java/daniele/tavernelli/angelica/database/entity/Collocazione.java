package daniele.tavernelli.angelica.database.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="collocazione")
@Table(name="collocazione")
public class Collocazione implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id_collocazione;
	
	private String collocazione,piano,stanza,denominazione,note;

	
	
	public Collocazione() {
	}



	public Collocazione(String collocazione, String piano, String stanza,
			String denominazione, String note) {
		super();
		this.collocazione = collocazione;
		this.piano = piano;
		this.stanza = stanza;
		this.denominazione = denominazione;
		this.note = note;
	}
	
	

	public Collocazione(long id_collocazione, String collocazione,
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
