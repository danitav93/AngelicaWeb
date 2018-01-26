package daniele.tavernelli.angelica.database.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import daniele.tavernelli.angelica.database.entity.Collocazione;

import java.util.List;


@Component
public class CollocazioneService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Collocazione> findAll() {
		return  jdbcTemplate.query(
				"SELECT * FROM collocazione",
				(rs, rowNum) -> new Collocazione(rs.getLong("id_collocazione"),
						rs.getString("collocazione"), rs.getString("piano"),rs.getString("stanza"),rs.getString("denominazione"),rs.getString("note")));
	}

	public void update(Collocazione collocazione) {
		jdbcTemplate.update(
				"UPDATE collocazione SET collocazione=?,piano=?,stanza=?,denominazione=?,note=? WHERE id_collocazione=?",
				collocazione.getCollocazione(), collocazione.getPiano(), collocazione.getStanza(), collocazione.getDenominazione(), collocazione.getNote(),collocazione.getId_collocazione());
	}
	
	public void save(Collocazione collocazione) {
		jdbcTemplate.update(
				"INSERT INTO collocazione (collocazione,piano,stanza,denominazione,note) VALUES (?,?,?,?,?)",
				collocazione.getCollocazione(), collocazione.getPiano(), collocazione.getStanza(), collocazione.getDenominazione(), collocazione.getNote());
	}
	
	public void remove(Collocazione collocazione) {
		jdbcTemplate.update(
				"delete FROM collocazione WHERE id_collocazione = ?",
				collocazione.getId_collocazione());
	}

}
