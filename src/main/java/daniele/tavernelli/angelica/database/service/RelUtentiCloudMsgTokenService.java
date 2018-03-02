package daniele.tavernelli.angelica.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daniele.tavernelli.angelica.database.entity.RelUtentiCloudMsgToken;
import daniele.tavernelli.angelica.database.entity.ViewUtente;
import daniele.tavernelli.angelica.database.repository.RelUtentiCloudMsgTokenRepository;



@Service
public class RelUtentiCloudMsgTokenService {

	
	@Autowired
	private RelUtentiCloudMsgTokenRepository relUtentiCloudMsgTokenRepository;
	
	@Autowired
	private UtenteService utenteService;

	public List<RelUtentiCloudMsgToken> findAll() {
		return (List<RelUtentiCloudMsgToken>)  relUtentiCloudMsgTokenRepository.findAll();
	}

	public void update(RelUtentiCloudMsgToken relUtentiCloudMsgToken) {
		relUtentiCloudMsgTokenRepository.save(relUtentiCloudMsgToken);
	}
	
	public void save(RelUtentiCloudMsgToken relUtentiCloudMsgToken) {
		relUtentiCloudMsgTokenRepository.save(relUtentiCloudMsgToken);
	}
	
	public void remove(RelUtentiCloudMsgToken relUtentiCloudMsgToken) {
		relUtentiCloudMsgTokenRepository.delete(relUtentiCloudMsgToken);
	}
	
	public String findTokenByIdUtente(Integer idUtente) {
		
		List<RelUtentiCloudMsgToken> relUtentiCloudMsgTokenList = relUtentiCloudMsgTokenRepository.findByUtente_idUtente(idUtente);
		if (relUtentiCloudMsgTokenList.size()>0) {
			return relUtentiCloudMsgTokenList.get(0).getToken();
		} 
		
		return null;
		
	}

	public void updateUserToken(ViewUtente viewUtenteFound, String cmToken) {
		
		List<RelUtentiCloudMsgToken> relUtentiCloudMsgTokenList = relUtentiCloudMsgTokenRepository.findByUtente_idUtente(viewUtenteFound.getIdUtente());
		RelUtentiCloudMsgToken relUtentiCloudMsgToken;
		if (relUtentiCloudMsgTokenList.size()==0) {
			relUtentiCloudMsgToken= new RelUtentiCloudMsgToken();
			relUtentiCloudMsgToken.setUtente(utenteService.findById(viewUtenteFound.getIdUtente()));
		} else {
			relUtentiCloudMsgToken=relUtentiCloudMsgTokenList.get(0);
		}
		relUtentiCloudMsgToken.setToken(cmToken);
		relUtentiCloudMsgTokenRepository.save(relUtentiCloudMsgToken);
	}

}

