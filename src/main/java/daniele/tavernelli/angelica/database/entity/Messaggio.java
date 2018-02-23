package daniele.tavernelli.angelica.database.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the messaggio database table.
 * 
 */
@Entity(name="messaggio")
public class Messaggio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_messaggio")
	private int idMessaggio;

	private String body;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@Column(name="id_destinatario")
	private int idDestinatario;

	@Column(name="id_mittente")
	private int idMittente;

	private int letto;

	public Messaggio() {
	}

	public int getIdMessaggio() {
		return this.idMessaggio;
	}

	public void setIdMessaggio(int idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getIdDestinatario() {
		return this.idDestinatario;
	}

	public void setIdDestinatario(int idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public int getIdMittente() {
		return this.idMittente;
	}

	public void setIdMittente(int idMittente) {
		this.idMittente = idMittente;
	}

	public int getLetto() {
		return this.letto;
	}

	public void setLetto(int letto) {
		this.letto = letto;
	}

}