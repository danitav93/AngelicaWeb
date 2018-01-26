package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import daniele.tavernelli.angelica.database.entity.Legenda;

@Component
public class LegendaService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Legenda> findAll() {
		return  jdbcTemplate.query(
				"SELECT * FROM legenda",
				(rs, rowNum) -> new Legenda(rs.getLong("id_legenda"),
						rs.getBytes("simbolo"), rs.getString("codifica")));
	}

	public void update(Legenda legenda) {
		jdbcTemplate.update(
				"UPDATE legenda SET simbolo=?,codifica=? WHERE id_legenda=?",
				legenda.getSimbolo(), legenda.getCodifica(), legenda.getId_legenda());
	}
	
	public void save(Legenda legenda) {
		jdbcTemplate.update(
				"INSERT INTO legenda (simbolo,codifica) VALUES (?,?)",
				legenda.getSimbolo(), legenda.getCodifica());
	}
	
	public void remove(Legenda legenda) {
		jdbcTemplate.update(
				"delete FROM legenda WHERE id_legenda = ?",
				legenda.getId_legenda());
	}

}

