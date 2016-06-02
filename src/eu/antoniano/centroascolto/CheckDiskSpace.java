package eu.antoniano.centroascolto;

import java.io.File;
import org.apache.log4j.Logger;
import eu.anastasis.serena.application.index.util.MailHandler;
import eu.anastasis.serena.exception.SerenaException;

public class CheckDiskSpace implements Runnable {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckDiskSpace.class);

	public void run() {
		try {
			/**
			 * Fetch delle scadenze di oggi (al netto del preavviso dinamico)
			 * per invio notifiche
			 */
			logger.trace("CheckDiskSpace is running..");
			float fspMB = new File("/").getFreeSpace() / 1024 / 1024;
			float tspMB = new File("/").getTotalSpace() / 1024 / 1024;
			logger.info("Free disk space is " + fspMB + "MB on " + tspMB);
			if (fspMB < 2000) {
				String msg = "Attenzione!!! Free disk space is MB " + fspMB;
				send(msg);
			}
		} catch (Exception e) {
			logger.error("Errore in CheckDiskSpace ", e);
		}
	}

	public void send(String msg) throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String subject = "Controllo spazio disco server Antoniano-Aruba";
		String from = "afrascari@gmail.com";
		String[] recipients = { from };
		MailHandler mailHandler = new MailHandler();
		mailHandler.send(from, recipients, cc, bcc, subject, msg);
	}
}
