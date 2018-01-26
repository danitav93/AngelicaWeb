package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vaadin.spring.annotation.SpringComponent;

import daniele.tavernelli.angelica.database.entity.Utente;

@SpringComponent
public class UtenteService {

	@Autowired
	private  JdbcTemplate jdbcTemplate;

	public List<Utente> findAll() {
		return  jdbcTemplate.query(
				"SELECT * FROM utente",
				(rs, rowNum) -> new Utente(rs.getLong("id_utente"),
						rs.getString("username"), rs.getString("password"), rs.getLong("id_ruolo")));
	}

	public void update(Utente utente) {
		jdbcTemplate.update(
				"UPDATE utente SET username=?,password=?,id_ruolo=? WHERE id_utente=?",
				utente.getUsername(), utente.getPassword(),utente.getId_ruolo(), utente.getId_ruolo());
	}
	
	public void save(Utente utente) {
		jdbcTemplate.update(
				"INSERT INTO utente (username,password,id_ruolo) VALUES (?,?,?)",
				utente.getUsername(), utente.getPassword(), utente.getId_ruolo());
	}
	
	public void remove(Utente legenda) {
		jdbcTemplate.update(
				"delete FROM utente WHERE id_utente = ?",
				legenda.getId_utente());
	}

	public List<Utente> findUtenteByUserName(String username) {
		return  jdbcTemplate.query(
				"SELECT * FROM utente where username=?",
				(rs, rowNum) -> new Utente(rs.getLong("id_utente"),
						rs.getString("username"), rs.getString("password"), rs.getLong("id_ruolo")),username);
		
	}

	

}

