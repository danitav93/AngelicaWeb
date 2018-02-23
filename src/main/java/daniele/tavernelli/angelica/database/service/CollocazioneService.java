package daniele.tavernelli.angelica.database.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import daniele.tavernelli.angelica.database.entity.Collocazione;
import daniele.tavernelli.angelica.database.repository.CollocazioneRepository;


@Service
public class CollocazioneService {

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
		return (List<Collocazione>)collocazioneRepository.findAll();
	}

	public void update(Collocazione collocazione) {
		collocazioneRepository.save(collocazione);
	}


	public void remove(Collocazione collocazione) {
		collocazioneRepository.delete(collocazione);
	}

	public List<Collocazione> findCollocazioneByCollocazione(String collocazione) {
		return  collocazioneRepository.findByCollocazione(collocazione);
	}

	/*public List<Collocazione> findCollocazioneByContieneCollocazione(String collocazione) {
		return  jdbcTemplate.query(
				"SELECT * FROM collocazione WHERE collocazione like ?",
				(rs, rowNum) -> new Collocazione(rs.getLong("id_collocazione"),
						rs.getString("collocazione"), rs.getString("piano"),rs.getString("stanza"),rs.getString("denominazione"),rs.getString("note")), collocazione+"%");
	}*/

	public List<Collocazione> findCollocazioneByContieneCollocazione(String collocazione) {
		return collocazioneRepository.findByFilter(collocazione);
	}


}
