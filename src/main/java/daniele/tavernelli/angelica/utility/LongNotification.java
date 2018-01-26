package daniele.tavernelli.angelica.utility;

import com.vaadin.ui.Notification;

public class LongNotification extends Notification {

	
	private static final long serialVersionUID = 1L;

	public LongNotification(String caption, Type type) {
		super(caption, type);
		if (type==Type.HUMANIZED_MESSAGE || type==Type.WARNING_MESSAGE) {
			setDelayMsec(2000);
		} else {
			setDelayMsec(30000);
		}
	}

}
