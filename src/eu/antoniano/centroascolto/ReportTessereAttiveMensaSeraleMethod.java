package eu.antoniano.centroascolto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.BirtReport.*;
import eu.anastasis.serena.application.modules.BirtReport.engine.ReportEngine;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.query.SelectQuery;

public class ReportTessereAttiveMensaSeraleMethod extends GiveMethod {

	private static final Logger logger = Logger.getLogger(ReportTessereAttiveMensaSeraleMethod.class);

	public static final String METHOD_NAME = "mensa_serale";
	
	private static final int NUM_OF_EXTRA_BLANK_ROWS = 10;

	private class TesseraDTO {
		String nome;
		String cognome;
		String scadenza;
		String sabatoDomenica;
		String progressivo;
		boolean italiano;
		
		String getProcessedSabatoDomenica()	{
			return ("SI".equals(sabatoDomenica))?"1":"0";
		}
		
		String getProcessedScadenza()	{
			return scadenza.substring(0,5);
		}
		
		String getProcessedCognome()	{
			return cognome.toUpperCase();
		}
		String getProcessedNome()	{
			return nome.toUpperCase();
		}

	}

	public ReportTessereAttiveMensaSeraleMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
	}

	protected Document retrieveData(HttpServletRequest request, ReportEngine engine, File designFile)
			throws SerenaException {
		final Document document = DocumentHelper.createDocument();
		try {
			// int mese = new
			// Integer(request.getParameter("mese")).intValue()-1;
			Calendar cal = Calendar.getInstance();
			int anno = cal.get(Calendar.YEAR);
			int mese = cal.get(Calendar.MONTH);
			cal.set(Calendar.YEAR, anno);
			cal.set(Calendar.MONTH, new Integer(mese).intValue());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			document.setXMLEncoding("UTF-8");
			Element currentElement = document.addElement(ConstantsXSerena.TAG_SERENA);
			Element base = currentElement.addElement(ConstantsXSerena.TAG_COMMAND);
			Element meta = base.addElement("Meta");
			currentElement = meta.addElement("calendario");
			currentElement.setText("Mensa serale");
			currentElement = meta.addElement("mese");
			SimpleDateFormat df = new SimpleDateFormat("MMM-YYYY");
			currentElement.setText(df.format(cal.getTime()).toUpperCase());
			Element meseCorrente = base.addElement("meseCorrente");

			df = new SimpleDateFormat("E");
			for (int i = 1; i <= maxDay; i++) {
				cal.set(Calendar.DAY_OF_MONTH, i);
				Element giorno = meseCorrente.addElement("Giorno");
				currentElement = giorno.addElement("progressivo");
				currentElement.setText(new Integer(i).toString());
				currentElement = giorno.addElement("sigla");
				currentElement.setText(df.format(cal.getTime()));
				currentElement = giorno.addElement("sabatoDomenica");
				currentElement.setText("0");
			}

			Element utenti = base.addElement("utenti");

			TreeMap<String, TesseraDTO> tessere = getTessereAttive(cal, request);

			int i = 1;
			for (TesseraDTO t : tessere.values()) {
				Element utente = utenti.addElement("Utente");
				currentElement = utente.addElement("nome");
				currentElement.setText(t.getProcessedNome());
				currentElement = utente.addElement("cognome");
				currentElement.setText(t.getProcessedCognome());
				currentElement = utente.addElement("progressivo");
				currentElement.setText(new Integer(i++).toString());
				currentElement = utente.addElement("scadenza");
				currentElement.setText(t.getProcessedScadenza());
				currentElement = utente.addElement("sabatoDomenica");
				currentElement.setText(t.getProcessedSabatoDomenica());
			}
			
			for (int j=0; j< NUM_OF_EXTRA_BLANK_ROWS; j++) {
				Element utente = utenti.addElement("Utente");
				currentElement = utente.addElement("nome");
				currentElement.setText("");
				currentElement = utente.addElement("cognome");
				currentElement.setText("");
				currentElement = utente.addElement("progressivo");
				currentElement.setText(new Integer(i++).toString());
				currentElement = utente.addElement("scadenza");
				currentElement.setText("");
				currentElement = utente.addElement("sabatoDomenica");
				currentElement.setText("");
			}
			
		} catch (Exception e) {
			String theError = "Errore in generazione report tessere attive: " + e.getMessage();
			throw new SerenaException(theError);
		}
		return document;
	}

	private TreeMap<String, TesseraDTO> getTessereAttive(Calendar cal, HttpServletRequest request)
			throws SerenaException {
		TreeMap<String, TesseraDTO> answer = new TreeMap<String, TesseraDTO>();
		try {
			SelectQuery q = new SelectQuery("Tessera");
			Element t = q.getFirstClassElement();
			// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
			// ConstantsXSerena.VAL_SELECT);
			t.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			t.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2");
			Element condElement = DocumentHelper.createElement(ConstantsXSerena.TAG_AND);
			Element cond = condElement.addElement("scadenza");
			cond.setText("01/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR));
			cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_THAN);
			cond = condElement.addElement("inverse_of_tessere");
			cond = cond.addElement("Utente");
			cond = cond.addElement("servizi_antoniano");
			cond = cond.addElement("_system_decode");
			cond = cond.addElement("sd_description");
			cond.setText("mensa serale");
			q.addCondition(t, condElement);
			Document data = ApplicationLibrary.getData(q, request);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2, "Tessera");
			if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> tessere = data.selectNodes(".//Tessera");
				for (Element tEl : tessere) {
					try {
						TesseraDTO tNew = new TesseraDTO();
						tNew.sabatoDomenica = tEl.elementText("sabatodomenica");
						tNew.scadenza = tEl.elementText("scadenza");
						Element utente = tEl.element("inverse_of_tessere").element("Utente");
						tNew.cognome = utente.elementText("cognome");
						tNew.nome = utente.elementText("nome");
						Element statoNascita = utente.element("stato_n");
						tNew.italiano = statoNascita!=null && "Italia".equals(statoNascita.getText());
						answer.put(tNew.cognome.toUpperCase(), tNew);
					} catch (Exception r) {
						String message = "Errore in tessera " + tEl.elementText("ID") + ": dati mancanti ...";
						logger.error(message);
					}
				}
			} else {
				String message = "Impossibile reperire tessere: " + messages2[0];
				logger.error(message);
				throw new SerenaException(message);
			}
			return answer;
		} catch (Exception e) {
			String message = "Impossibile reperire tessere: " + e.getMessage();
			logger.error(message);
			throw new SerenaException(message);
		}

	}

	@Override
	protected String getName() {
		return METHOD_NAME;
	}
}
