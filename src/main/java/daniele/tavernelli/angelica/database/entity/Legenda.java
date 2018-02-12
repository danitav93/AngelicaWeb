package daniele.tavernelli.angelica.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Legenda {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id_legenda;
	private byte[] simbolo;
	private String codifica;
	
	public Legenda() {
		super();
	}

	public Legenda(byte[] simbolo, String codifica) {
		super();
		this.simbolo = simbolo;
		this.codifica = codifica;
	}
	
	
	
	public Legenda(long id_legenda, byte[] simbolo, String codifica) {
		super();
		this.id_legenda = id_legenda;
		this.simbolo = simbolo;
		this.codifica = codifica;
	}

	public long getId_legenda() {
		return id_legenda;
	}
	public void setId_legenda(long id_legenda) {
		this.id_legenda = id_legenda;
	}
	public byte[] getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(byte[] simbolo) {
		this.simbolo = simbolo;
	}
	public String getCodifica() {
		return codifica;
	}
	public void setCodifica(String codifica) {
		this.codifica = codifica;
	}
	
	

	
	
}
