package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daniele.tavernelli.angelica.database.entity.ViewUtente;
import daniele.tavernelli.angelica.database.repository.ViewUtenteRepository;

@Service
public class ViewUtenteService {


	@Autowired
	private ViewUtenteRepository viewUtenteRepository;

	public ViewUtente findByUsername(String username) {
		return viewUtenteRepository.findByUsername(username);
	}

	public List<ViewUtente> findAll() {
		return (List<ViewUtente>) viewUtenteRepository.findAll();
	}

	public boolean update(ViewUtente utente) {
		try {
			viewUtenteRepository.save(utente);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public ViewUtente findByIdUtente(long id) {
		return  viewUtenteRepository.findOne(id);
	}

	public List<ViewUtente> getListByRuolo(int idRuolo) {
		return viewUtenteRepository.findByIdRuolo(idRuolo);
	}

	public List<ViewUtente> getListByRuoloExceptThis(int id_ruolo, long id_utente_to_remove) {
		return removeFromList(getListByRuolo(id_ruolo), id_utente_to_remove);
	}

	public List<ViewUtente> findAllExceptThis(int id_utente_to_remove) {
		return removeFromList(findAll(), id_utente_to_remove);
	}

	public List<ViewUtente> removeFromList(List<ViewUtente> list, long id_utente) {
		ViewUtente toRemove=null;
		for (ViewUtente viewUtente : list) {
			if (id_utente == viewUtente.getIdUtente()) {
				toRemove=viewUtente;
				break;
			}
		}
		if (toRemove!=null) {
			list.remove(toRemove);
		}
		return list;
	}

}

