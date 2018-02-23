package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daniele.tavernelli.angelica.database.entity.Legenda;
import daniele.tavernelli.angelica.database.repository.LegendaRepository;

@Service
public class LegendaService {

	
	@Autowired
	private LegendaRepository legendaRepository;

	public List<Legenda> findAll() {
		return (List<Legenda>)  legendaRepository.findAll();
	}

	public void update(Legenda legenda) {
		legendaRepository.save(legenda);
	}
	
	public void save(Legenda legenda) {
		legendaRepository.save(legenda);
	}
	
	public void remove(Legenda legenda) {
		legendaRepository.delete(legenda);
	}

}

