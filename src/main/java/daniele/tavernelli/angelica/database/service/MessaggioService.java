package daniele.tavernelli.angelica.database.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import daniele.tavernelli.angelica.database.entity.Messaggio;
import daniele.tavernelli.angelica.database.repository.MessaggioRepository;


@Service
public class MessaggioService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MessaggioRepository messaggioRepository;
	
	public Messaggio find(int id) {
		return messaggioRepository.findByIdMessaggio(id);
	}

	public List<Messaggio> findAll() {
		return  (List<Messaggio>) messaggioRepository.findAll();
	}

	public void update(Messaggio messaggio) {
		messaggioRepository.save(messaggio);
	}
	
	public Messaggio save(Messaggio messaggio) {
		return messaggioRepository.save(messaggio);
	}
	
	public void remove(Messaggio messaggio) {
		messaggioRepository.delete(messaggio);
	}

	public List<Messaggio> findByUtentiInChat(int id_mittente, int id_destinatario) {
		return messaggioRepository.findByMittenteDestinatario(id_mittente, id_destinatario);
	}

	public List<Messaggio> findNewMessaggiRicevutiEInviati(int me, int other) {
		return messaggioRepository.findNewMessaggiRicevutiEInviati(me, other);
	}
	
	public List<Messaggio> findNewMessaggiRicevuti(int me, int other) {
		return  messaggioRepository.findNewMessaggiRicevuti(me, other);
		
	}

	public boolean thereIsNewMessagge(int me, int other) {
		List<Messaggio> messaggionLetti =findNewMessaggiRicevuti(me,other); 
		return messaggionLetti!=null && messaggionLetti.size()>0;
	}

	public List<Integer> getUtentiMessaggiNonLetti(long destinatario) {
		return  jdbcTemplate.query(
				"SELECT DISTINCT id_mittente FROM messaggio where ( id_destinatario=?) AND letto=0",
				(rs, rowNum) -> new Integer(rs.getInt("id_mittente")),destinatario);
	}

	public void deleteAll() {

		jdbcTemplate.execute(
				"delete FROM messaggio "
				);
		
	}

	public List<Messaggio> getPage(int pageNumber, int pageSize, int mittente, int destinatario) {

		PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "data");

		return messaggioRepository.findByMittenteDestinatario(mittente,destinatario,request);
	}

	public Messaggio getMessaggio(int id_messaggio) {
		return messaggioRepository.findByIdMessaggio(id_messaggio);
	}

}
