package daniele.tavernelli.angelica;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class logtest {
	static final Logger log = Logger.getLogger(logtest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logtest console = new logtest();
		console.execute();
	}

	public logtest() {
	}

	public void execute() {
		
		if (log.isTraceEnabled()) {
			log.trace("Test: TRACE level message.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Test: DEBUG level message.");
		}
		if (log.isInfoEnabled()) {
			log.info("Test: INFO level message.");
		}
		if (log.isEnabledFor(Level.WARN)) {
			log.warn("Test: WARN level message.");
		}
		if (log.isEnabledFor(Level.ERROR)) {
			log.error("Test: ERROR level message.");
		}
		if (log.isEnabledFor(Level.FATAL)) {
			log.fatal("Test: FATAL level message.");
		}
	}
}
