package daniele.tavernelli.angelica.database.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ruolo database table.
 * 
 */
@Entity
@NamedQuery(name="Ruolo.findAll", query="SELECT r FROM Ruolo r")
public class Ruolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ruolo")
	private int idRuolo;

	@Lob
	@Column(name="desc_func")
	private String descFunc;

	private String nome;

	public Ruolo() {
	}

	public int getIdRuolo() {
		return this.idRuolo;
	}

	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getDescFunc() {
		return this.descFunc;
	}

	public void setDescFunc(String descFunc) {
		this.descFunc = descFunc;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}