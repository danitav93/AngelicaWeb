package daniele.tavernelli.angelica.database.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the legenda database table.
 * 
 */
@Entity
@NamedQuery(name="Legenda.findAll", query="SELECT l FROM Legenda l")
public class Legenda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_legenda")
	private int idLegenda;

	@Lob
	private String codifica;

	@Lob
	private byte[] simbolo;

	public Legenda() {
	}

	public int getIdLegenda() {
		return this.idLegenda;
	}

	public void setIdLegenda(int idLegenda) {
		this.idLegenda = idLegenda;
	}

	public String getCodifica() {
		return this.codifica;
	}

	public void setCodifica(String codifica) {
		this.codifica = codifica;
	}

	public byte[] getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(byte[] simbolo) {
		this.simbolo = simbolo;
	}

}