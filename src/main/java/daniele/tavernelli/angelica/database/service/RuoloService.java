package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vaadin.spring.annotation.SpringComponent;

import daniele.tavernelli.angelica.database.entity.Ruolo;

@SpringComponent
public class RuoloService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Ruolo> findAll() {
		return  jdbcTemplate.query(
				"SELECT * FROM ruolo",
				(rs, rowNum) -> new Ruolo(rs.getLong("id_ruolo"),
						rs.getString("nome")));
	}

	

}

