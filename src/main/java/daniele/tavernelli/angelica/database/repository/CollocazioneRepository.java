package daniele.tavernelli.angelica.database.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import daniele.tavernelli.angelica.database.entity.Collocazione;

public interface CollocazioneRepository extends PagingAndSortingRepository<Collocazione, Long> {

	@Query("from collocazione c where c.collocazione like :col%")
	List<Collocazione> findByFilter(@Param("col")String collocazione,Pageable pageable);
	
	@Query("from collocazione c where c.collocazione like :col%")
	List<Collocazione> findByFilter(@Param("col")String collocazione);
	
	List<Collocazione> findByCollocazione(String collocazione);
	
	
	
	
}
