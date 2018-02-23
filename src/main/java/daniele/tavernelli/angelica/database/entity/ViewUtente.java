package daniele.tavernelli.angelica.database.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the view_utente database table.
 * 
 */
@Entity
@Table(name="view_utente")
@NamedQuery(name="ViewUtente.findAll", query="SELECT v FROM ViewUtente v")
public class ViewUtente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Lob
	@Column(name="desc_func")
	private String descFunc;

	@Column(name="id_ruolo")
	private int idRuolo;

	@Id
	@Column(name="id_utente")
	private int idUtente;

	@Column(name="nome_ruolo")
	private String nomeRuolo;

	private String password;

	private String username;

	public ViewUtente() {
	}

	public String getDescFunc() {
		return this.descFunc;
	}

	public void setDescFunc(String descFunc) {
		this.descFunc = descFunc;
	}

	public int getIdRuolo() {
		return this.idRuolo;
	}

	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}

	public int getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getNomeRuolo() {
		return this.nomeRuolo;
	}

	public void setNomeRuolo(String nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
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

}