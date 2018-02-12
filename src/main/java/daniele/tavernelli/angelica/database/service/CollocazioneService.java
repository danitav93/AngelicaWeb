package daniele.tavernelli.angelica.database.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import daniele.tavernelli.angelica.database.entity.Collocazione;
import daniele.tavernelli.angelica.database.repository.CollocazioneRepository;


@Service
public class CollocazioneService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private CollocazioneRepository collocazioneRepository;

	public void save(Collocazione customer) {
		collocazioneRepository.save(customer);
	}

	public Iterable<Collocazione> findAllCustomers() {
		return collocazioneRepository.findAll();
	}

	public List<Collocazione> getPage(int pageNumber, int pageSize) {

		PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "collocazione");

		return collocazioneRepository.findAll(request).getContent();
	}

	public List<Collocazione> getPage(int pageNumber, int pageSize,String collocazione) {

		PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "collocazione");

		return collocazioneRepository.findByFilter(collocazione,request);
	}


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

	/*public void save(Collocazione collocazione) {
		jdbcTemplate.update(
				"INSERT INTO collocazione (collocazione,piano,stanza,denominazione,note) VALUES (?,?,?,?,?)",
				collocazione.getCollocazione(), collocazione.getPiano(), collocazione.getStanza(), collocazione.getDenominazione(), collocazione.getNote());
	}*/

	public void remove(Collocazione collocazione) {
		jdbcTemplate.update(
				"delete FROM collocazione WHERE id_collocazione = ?",
				collocazione.getId_collocazione());
	}

	public List<Collocazione> findCollocazioneByCollocazione(String collocazione) {
		return  jdbcTemplate.query(
				"SELECT * FROM collocazione WHERE collocazione=?",
				(rs, rowNum) -> new Collocazione(rs.getLong("id_collocazione"),
						rs.getString("collocazione"), rs.getString("piano"),rs.getString("stanza"),rs.getString("denominazione"),rs.getString("note")),collocazione);
	}

	public List<Collocazione> findCollocazioneByContieneCollocazione(String collocazione) {
		return  jdbcTemplate.query(
				"SELECT * FROM collocazione WHERE collocazione like ?",
				(rs, rowNum) -> new Collocazione(rs.getLong("id_collocazione"),
						rs.getString("collocazione"), rs.getString("piano"),rs.getString("stanza"),rs.getString("denominazione"),rs.getString("note")), collocazione+"%");
	}



}
