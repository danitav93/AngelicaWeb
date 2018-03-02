package daniele.tavernelli.angelica.firebaseCM;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.client.settings.PropertiesBasedSettings;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import de.bytefish.fcmjava.responses.FcmMessageResponse;

@Component
@Scope("singleton")
public class Sender {
	
	 private class MessageChat {

	        private final String idMessaggio;

	        public MessageChat(String idMessaggio) {
				super();
				this.idMessaggio = idMessaggio;
			}

			@JsonProperty("idMessaggio")
	        public String getIdMessaggio() {
	            return idMessaggio;
	        }

	 }

	 
	public void sendMessage(Integer idMessaggio, String receiverToken) throws Exception {

		// Create the Client using system-properties-based settings:
        try (FcmClient client = new FcmClient(PropertiesBasedSettings.createFromDefault())) {

            // Message Options:
            FcmMessageOptions options = FcmMessageOptions.builder()
                    .setTimeToLive(Duration.ofHours(1))
                    .build();

            List<String> registrationIds = new ArrayList<>();
            registrationIds.add(receiverToken);
            DataUnicastMessage dataUnicastMessage = new DataUnicastMessage(options, receiverToken, new MessageChat(idMessaggio.toString()));
            // Send a Message:
            FcmMessageResponse response=client.send(dataUnicastMessage);
            
       
           
        }
        
    }
	
	
	
	
}
