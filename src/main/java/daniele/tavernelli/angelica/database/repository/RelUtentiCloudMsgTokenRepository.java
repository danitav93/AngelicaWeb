package daniele.tavernelli.angelica.database.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import daniele.tavernelli.angelica.database.entity.RelUtentiCloudMsgToken;

public interface RelUtentiCloudMsgTokenRepository extends PagingAndSortingRepository<RelUtentiCloudMsgToken, Long>{

	List<RelUtentiCloudMsgToken> findByUtente_idUtente(Integer idUtente);

}
