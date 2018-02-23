package daniele.tavernelli.angelica.database.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the collocazione database table.
 * 
 */
@Entity(name="collocazione")
public class Collocazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_collocazione")
	private int idCollocazione;

	@Lob
	private String collocazione;

	@Lob
	private String denominazione;

	@Lob
	private String note;

	@Lob
	private String piano;

	@Lob
	private String stanza;

	public Collocazione() {
	}

	public int getIdCollocazione() {
		return this.idCollocazione;
	}

	public void setIdCollocazione(int idCollocazione) {
		this.idCollocazione = idCollocazione;
	}

	public String getCollocazione() {
		return this.collocazione;
	}

	public void setCollocazione(String collocazione) {
		this.collocazione = collocazione;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPiano() {
		return this.piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getStanza() {
		return this.stanza;
	}

	public void setStanza(String stanza) {
		this.stanza = stanza;
	}

}