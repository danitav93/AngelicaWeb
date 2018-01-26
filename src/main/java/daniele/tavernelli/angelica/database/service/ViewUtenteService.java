package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import daniele.tavernelli.angelica.database.view.ViewUtente;

@Component
public class ViewUtenteService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ViewUtente> findAll() {
		return  jdbcTemplate.query(
				"SELECT * FROM view_utente",
				(rs, rowNum) -> new ViewUtente(rs.getLong("id_utente"),rs.getLong("id_ruolo"),
						rs.getString("username"), rs.getString("password"),rs.getString("nome_ruolo")));
	}

	public boolean update(ViewUtente utente) {
		try {
		jdbcTemplate.update(
				"UPDATE view_utente SET username=?,password=?,id_ruolo=? WHERE id_utente=?",
				utente.getUsername(), utente.getPassword(),utente.getId_ruolo(), utente.getId_utente());
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ViewUtente> findByIdUtente(long id) {
		return  jdbcTemplate.query(
				"SELECT * FROM view_utente where id_utente=?",
				(rs, rowNum) -> new ViewUtente(rs.getLong("id_utente"),rs.getLong("id_ruolo"),
						rs.getString("username"), rs.getString("password"),rs.getString("nome_ruolo")),id);
	}
	
	
	

}

