package daniele.tavernelli.angelica.utility;

public class Constants {

	public final static String imagesPath = "C:/Users/Daniele Tavernelli/Desktop/Angelica/";
	
	//USER RULES
	public final static String usernameRule = "Lo username deve contenere almeno quattro caratteri";
	public final static String passwordRule = "La password deve contenere almeno cinque caratteri";
	public final static String ruoloRule = "Il ruolo deve essere scelto tra SUPERVISORE, UTENTE ESTERNO, UTENTE INTERNO";


	//user roles
	public static final int SUPERVISORE = 1;
	public static final int UTENTE_ESTERNO = 2;
	public static final int UTENTE_INTERNO = 3;
	
	
	//BROADCAST
	//i messaggi sono del tipo:
	//prima lettera indica il TIPO seguito da altri casi trattai nello specifico:
	
	//TIPO CHAT+#+id_mittente+#+id_destinatario
	public final static String TIPO_CHAT="1";
	
	//versione
	public static final String desc_versione="0.2";
	
	//rest
	public static final String rootRestPath="rest";
	
	
	
}
