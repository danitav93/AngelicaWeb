package daniele.tavernelli.angelica.database.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rel_utenti_cloud_msg_token database table.
 * 
 */
@Entity
@Table(name="rel_utenti_cloud_msg_token")
@NamedQuery(name="RelUtentiCloudMsgToken.findAll", query="SELECT r FROM RelUtentiCloudMsgToken r")
public class RelUtentiCloudMsgToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rel_utenti_cloud_msg_token")
	private int idRelUtentiCloudMsgToken;

	@Lob
	private String token;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_utente")
	private Utente utente;

	public RelUtentiCloudMsgToken() {
	}

	public int getIdRelUtentiCloudMsgToken() {
		return this.idRelUtentiCloudMsgToken;
	}

	public void setIdRelUtentiCloudMsgToken(int idRelUtentiCloudMsgToken) {
		this.idRelUtentiCloudMsgToken = idRelUtentiCloudMsgToken;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}