package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daniele.tavernelli.angelica.database.entity.Ruolo;
import daniele.tavernelli.angelica.database.repository.RuoloRepository;

@Service
public class RuoloService {

	
	@Autowired
	private RuoloRepository ruoloRepository;

	public List<Ruolo> findAll() {
		return (List<Ruolo>) ruoloRepository.findAll();  
	}

	

}

