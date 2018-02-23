package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daniele.tavernelli.angelica.database.entity.Utente;
import daniele.tavernelli.angelica.database.repository.UtenteRepository;

@Service
public class UtenteService {

	
	
	@Autowired
	private UtenteRepository utenteRepository;

	public List<Utente> findAll() {
		return  (List<Utente>) utenteRepository.findAll();
	}

	public void update(Utente utente) {
		utenteRepository.save(utente);
	}
	
	public void save(Utente utente) {
		utenteRepository.save(utente);
	}
	
	public void remove(Utente utente) {
		utenteRepository.delete(utente);
	}

	public List<Utente> findUtenteByUserName(String username) {
		return  utenteRepository.findByUsername(username);
		
	}

	

}

