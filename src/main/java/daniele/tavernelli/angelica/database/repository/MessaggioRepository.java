package daniele.tavernelli.angelica.database.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import daniele.tavernelli.angelica.database.entity.Messaggio;

public interface MessaggioRepository extends PagingAndSortingRepository<Messaggio, Long> {

	@Query("FROM messaggio where (id_mittente=:mittente OR id_destinatario=:destinatario) AND (id_mittente=:destinatario OR id_destinatario=:mittente) ORDER BY data")
	List<Messaggio> findByMittenteDestinatario(@Param("mittente")int mittente,@Param("destinatario")int destinatario,Pageable pageable);
	
	@Query("FROM messaggio where (id_mittente=:mittente OR id_destinatario=:destinatario) AND (id_mittente=:destinatario OR id_destinatario=:mittente) ORDER BY data")
	List<Messaggio> findByMittenteDestinatario(@Param("mittente")int mittente,@Param("destinatario")int destinatario);
	
	@Query("FROM messaggio where ((id_mittente=:me OR id_destinatario=:other) AND (id_mittente=:other OR id_destinatario=:me)) AND letto=0 ORDER BY data")
	List<Messaggio> findNewMessaggiRicevutiEInviati(@Param("me") int me,@Param("other") int other);
	
	@Query("FROM messaggio where (id_mittente=:other ) AND ( id_destinatario=:me) AND letto=0 ORDER BY data")
	List<Messaggio> findNewMessaggiRicevuti(@Param("me") int me,@Param("other") int other);
	
	
}
