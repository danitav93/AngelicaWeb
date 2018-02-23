package daniele.tavernelli.angelica.database.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the utente database table.
 * 
 */
@Entity
@NamedQuery(name="Utente.findAll", query="SELECT u FROM Utente u")
public class Utente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_utente")
	private int idUtente;

	private String password;

	private String username;

	//bi-directional many-to-one association to RelUtentiCloudMsgToken
	@OneToMany(mappedBy="utente")
	private List<RelUtentiCloudMsgToken> ListCloudMsgTokens;

	//uni-directional many-to-one association to Ruolo
	@ManyToOne
	@JoinColumn(name="id_ruolo")
	private Ruolo ruolo;

	public Utente() {
	}

	public int getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<RelUtentiCloudMsgToken> getListCloudMsgTokens() {
		return this.ListCloudMsgTokens;
	}

	public void setListCloudMsgTokens(List<RelUtentiCloudMsgToken> ListCloudMsgTokens) {
		this.ListCloudMsgTokens = ListCloudMsgTokens;
	}

	public RelUtentiCloudMsgToken addListCloudMsgToken(RelUtentiCloudMsgToken ListCloudMsgToken) {
		getListCloudMsgTokens().add(ListCloudMsgToken);
		ListCloudMsgToken.setUtente(this);

		return ListCloudMsgToken;
	}

	public RelUtentiCloudMsgToken removeListCloudMsgToken(RelUtentiCloudMsgToken ListCloudMsgToken) {
		getListCloudMsgTokens().remove(ListCloudMsgToken);
		ListCloudMsgToken.setUtente(null);

		return ListCloudMsgToken;
	}

	public Ruolo getRuolo() {
		return this.ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

}