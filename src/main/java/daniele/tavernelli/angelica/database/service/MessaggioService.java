package daniele.tavernelli.angelica.database.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vaadin.spring.annotation.SpringComponent;

import daniele.tavernelli.angelica.database.entity.Messaggio;


@SpringComponent
public class MessaggioService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Messaggio> findAll() {
		return  jdbcTemplate.query(
				"SELECT * FROM messaggio",
				(rs, rowNum) -> new Messaggio(rs.getLong("id_messaggio"),rs.getLong("id_mittente"),rs.getLong("id_destinatario"),
						rs.getTimestamp("data"), rs.getString("body"),rs.getInt("letto")));
	}

	public void update(Messaggio messaggio) {
		jdbcTemplate.update(
				"UPDATE messaggio SET id_mittente=?,id_destinatario=?,data=?,body=?,letto=? WHERE id_messaggio=?",
				messaggio.getId_mittente(), messaggio.getId_destinatario(), messaggio.getData(), messaggio.getBody(), messaggio.getLetto(),messaggio.getId_messaggio());
	}
	
	public void save(Messaggio messaggio) {
		jdbcTemplate.update(
				"INSERT INTO messaggio (id_mittente,id_destinatario,data,body,letto) VALUES (?,?,?,?,?)",
				messaggio.getId_mittente(), messaggio.getId_destinatario(), messaggio.getData(), messaggio.getBody(), messaggio.getLetto());
	}
	
	public void remove(Messaggio messaggio) {
		jdbcTemplate.update(
				"delete FROM messaggio WHERE id_Messaggio = ?",
				messaggio.getId_messaggio());
	}

	public List<Messaggio> findByUtentiInChat(long id_mittente, Long id_destinatario) {
		return  jdbcTemplate.query(
				"SELECT * FROM messaggio where (id_mittente=? OR id_destinatario=?) AND (id_mittente=? OR id_destinatario=?) ORDER BY data",
				(rs, rowNum) -> new Messaggio(rs.getLong("id_messaggio"),rs.getLong("id_mittente"),rs.getLong("id_destinatario"),
						rs.getTimestamp("data"), rs.getString("body"),rs.getInt("letto")),id_mittente,id_mittente,id_destinatario,id_destinatario);
	}

	public List<Messaggio> findNewMessaggiRicevutiEInviati(long me, Long other) {
		return  jdbcTemplate.query(
				"SELECT * FROM messaggio where ((id_mittente=? OR id_destinatario=?) AND (id_mittente=? OR id_destinatario=?)) AND letto=0 ORDER BY data",
				(rs, rowNum) -> new Messaggio(rs.getLong("id_messaggio"),rs.getLong("id_mittente"),rs.getLong("id_destinatario"),
						rs.getTimestamp("data"), rs.getString("body"),rs.getInt("letto")),other,other,me,me);
		
	}
	
	public List<Messaggio> findNewMessaggiRicevuti(long me, Long other) {
		return  jdbcTemplate.query(
				"SELECT * FROM messaggio where (id_mittente=? ) AND ( id_destinatario=?) AND letto=0 ORDER BY data",
				(rs, rowNum) -> new Messaggio(rs.getLong("id_messaggio"),rs.getLong("id_mittente"),rs.getLong("id_destinatario"),
						rs.getTimestamp("data"), rs.getString("body"),rs.getInt("letto")),other,me);
		
	}

	public boolean thereIsNewMessagge(long me, Long other) {
		List<Messaggio> messaggionLetti =findNewMessaggiRicevuti(me,other); 
		return messaggionLetti!=null && messaggionLetti.size()>0;
	}

	public List<Long> getUtentiMessaggiNonLetti(long destinatario) {
		return  jdbcTemplate.query(
				"SELECT DISTINCT id_mittente FROM messaggio where ( id_destinatario=?) AND letto=0",
				(rs, rowNum) -> new Long(rs.getLong("id_mittente")),destinatario);
	}

	public void deleteAll() {

		jdbcTemplate.execute(
				"delete FROM messaggio "
				);
		
	}

}
