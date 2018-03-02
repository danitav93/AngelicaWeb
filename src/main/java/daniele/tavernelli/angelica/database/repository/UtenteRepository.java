package daniele.tavernelli.angelica.database.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import daniele.tavernelli.angelica.database.entity.Utente;

public interface  UtenteRepository extends PagingAndSortingRepository<Utente, Long> {
	
	List<Utente> findByUsername(String username);

	Utente findByIdUtente(int idUtente);

}
