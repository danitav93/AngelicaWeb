package daniele.tavernelli.angelica.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Utente {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id_utente;
	
	private String username,password;
	
	private long id_ruolo;
	
	

	public Utente() {
		super();
	}
	
	
	public Utente(String username, String password, long id_ruolo) {
		super();
		this.username = username;
		this.password = password;
		this.id_ruolo = id_ruolo;
	}



	public Utente(long id_utente, String username, String password, long id_ruolo) {
		super();
		this.id_utente = id_utente;
		this.username = username;
		this.password = password;
		this.id_ruolo = id_ruolo;
	}



	public long getId_utente() {
		return id_utente;
	}

	public void setId_utente(long id_utente) {
		this.id_utente = id_utente;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId_ruolo() {
		return id_ruolo;
	}

	public void setId_ruolo(long id_ruolo) {
		this.id_ruolo = id_ruolo;
	}
	
	
	
	
}
