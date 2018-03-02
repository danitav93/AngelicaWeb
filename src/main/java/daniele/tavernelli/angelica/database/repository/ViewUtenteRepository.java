package daniele.tavernelli.angelica.database.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import daniele.tavernelli.angelica.database.entity.ViewUtente;

public interface ViewUtenteRepository extends PagingAndSortingRepository<ViewUtente, Long> {
	
	ViewUtente findByUsername(String username);

	List<ViewUtente> findByIdRuolo(int idRuolo);
	
	ViewUtente findByIdUtente(int idUtente);

}

