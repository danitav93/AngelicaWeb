package daniele.tavernelli.angelica.database.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import daniele.tavernelli.angelica.database.entity.Messaggio;

public interface MessaggioRepository extends PagingAndSortingRepository<Messaggio, Long> {

	@Query("FROM messaggio where (id_mittente=:mittente AND id_destinatario=:destinatario) OR (id_mittente=:destinatario AND id_destinatario=:mittente) ORDER BY data DESC")
	List<Messaggio> findByMittenteDestinatario(@Param("mittente")int mittente,@Param("destinatario")int destinatario,Pageable pageable);
	
	@Query("FROM messaggio where (id_mittente=:mittente AND id_destinatario=:destinatario) OR (id_mittente=:destinatario AND id_destinatario=:mittente) ORDER BY data DESC")
	List<Messaggio> findByMittenteDestinatario(@Param("mittente")int mittente,@Param("destinatario")int destinatario);
	
	@Query("FROM messaggio where ((id_mittente=:me AND id_destinatario=:other) OR (id_mittente=:other AND id_destinatario=:me)) AND letto=0 ORDER BY data DESC")
	List<Messaggio> findNewMessaggiRicevutiEInviati(@Param("me") int me,@Param("other") int other);
	
	@Query("FROM messaggio where (id_mittente=:other ) AND ( id_destinatario=:me) AND letto=0 ORDER BY data DESC")
	List<Messaggio> findNewMessaggiRicevuti(@Param("me") int me,@Param("other") int other);

	Messaggio findByIdMessaggio(int id_messaggio);
	
	
}
