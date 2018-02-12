package daniele.tavernelli.angelica.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ruolo {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_ruolo;
	private String nome;
	
	public Ruolo() {
		super();
	}
	
	public Ruolo(String nome) {
		super();
		this.nome = nome;
	}
	
	public Ruolo(Long id_ruolo, String nome) {
		super();
		this.id_ruolo = id_ruolo;
		this.nome = nome;
	}
	public Long getId_ruolo() {
		return id_ruolo;
	}
	public void setId_ruolo(Long id_ruolo) {
		this.id_ruolo = id_ruolo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
