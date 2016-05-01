package eu.antoniano.centroascolto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.query.SelectQuery;

public class ReportRilascioTessereMethod extends GiveMethod {

	private static final Logger logger = Logger
			.getLogger(ReportRilascioTessereMethod.class);

	public static final String METHOD_NAME = "rilascio_tessere";

	private class UnitDTO {
		String label;
		int nuove = 0;
		int rinnovi = 0;
		int totale = 0;
	}

	private class TesseraDTO {
		String nome;
		String cognome;
		String id;
		String emissione;
		String sabatoDomenica;
		String progressivo;
		boolean italiano;
	}

	public ReportRilascioTessereMethod(DefaultModule parentModule,
			String[] defaultParameters) {
		super(parentModule, defaultParameters);
	}

	protected Document retrieveData(HttpServletRequest request,
			ReportEngine engine, File designFile) throws SerenaException {
		final Document document = DocumentHelper.createDocument();
		try {
			String anno0 = request.getParameter("dal");

			document.setXMLEncoding("UTF-8");
			Element currentElement = document
					.addElement(ConstantsXSerena.TAG_SERENA);
			Element base = currentElement
					.addElement(ConstantsXSerena.TAG_COMMAND);

			HashMap<String, UnitDTO> mesi = new HashMap<String, UnitDTO>();
			TreeMap<String, UnitDTO> anni = new TreeMap<String, UnitDTO>();
			TreeMap<String, Integer> map = new TreeMap<String, Integer>();
			List<TesseraDTO> tessere = getTessereAttive(anno0, request);
			// populate utenti map
			for (TesseraDTO t : tessere) {
				if (!map.containsKey(t.id)) {
					map.put(t.id, 1);
				} else {
					map.put(t.id, map.get(t.id) + 1);
				}
			}
			Element mesiEl = base.addElement("mesi");
			Element anniEl = base.addElement("anni");
			int i = 1;
			SerenaDate t0 = new SerenaDate("01/01/" + anno0);
			SerenaDate today = new SerenaDate();
			// external loop
			while (t0.isBefore(today)) {
				SerenaDate t1 = (SerenaDate) t0.clone();
				t1.addMonths(1);
				String annolabel = new Integer(t0.getYear()).toString();
				if (t0.getMonth()==1)	{
					UnitDTO a = new UnitDTO();
					a.label = annolabel;
					anni.put(annolabel, a);		
					logger.debug("ANNI: Adding " + annolabel);
				}
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, t0.getYear());
				cal.set(Calendar.MONTH, t0.getMonth()-1);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				SimpleDateFormat df = new SimpleDateFormat("MMM-YYYY");
				String meselabel = df.format(cal.getTime()).toUpperCase();
				// internal loop over tessere
				for (TesseraDTO t : tessere) {
					boolean newTessera = map.get(t.id) == 1;
					SerenaDate c = new SerenaDate(t.emissione);
					if (t0.isBefore(c) && c.isBefore(t1)) {
						if (!mesi.containsKey(meselabel)) {
							mesi.put(meselabel, new UnitDTO());
							logger.debug("MESI: Adding " + meselabel);
						}
						UnitDTO u = mesi.get(meselabel);
						u.totale++;
						if (newTessera) {
							u.nuove++;
						} else {
							u.rinnovi++;
						}
						mesi.put(meselabel, u);
					}
				}
				// another month is done:
				// 1) month to xml
				UnitDTO u = mesi.get(meselabel);
				if (u!=null)	{
					Element meseEl = mesiEl.addElement("Mese");
					Element currentEl = meseEl.addElement("totale");
					currentEl.setText(new Integer(u.totale).toString());
					currentEl = meseEl.addElement("nuove");
					currentEl.setText(new Integer(u.nuove).toString());
					currentEl = meseEl.addElement("rinnovi");
					currentEl.setText(new Integer(u.rinnovi).toString());
					currentEl = meseEl.addElement("label");
					currentEl.setText(meselabel);
					// 2) update anno
					UnitDTO a = anni.get(annolabel);
					a.totale += u.totale;
					a.nuove += u.nuove;
					a.rinnovi += u.rinnovi;
					anni.put(annolabel, a);
				}
				t0 = t1;
			}
			for (UnitDTO a : anni.values()) {
				Element annoEl = anniEl.addElement("Anno");
				Element currentEl = annoEl.addElement("totale");
				currentEl.setText(new Integer(a.totale).toString());
				currentEl = annoEl.addElement("nuove");
				currentEl.setText(new Integer(a.nuove).toString());
				currentEl = annoEl.addElement("rinnovi");
				currentEl.setText(new Integer(a.rinnovi).toString());
				currentEl = annoEl.addElement("label");
				currentEl.setText(a.label);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String theError = "Errore in generazione report tessere attive: "
					+ e.getMessage();
			throw new SerenaException(theError);
		}
		return document;
	}

	private List<TesseraDTO> getTessereAttive(String anno0,
			HttpServletRequest request) throws SerenaException {
		try {
			List<TesseraDTO> answer = new ArrayList<TesseraDTO>();
			SelectQuery q = new SelectQuery("Tessera");
			Element t = q.getFirstClassElement();
			// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
			// ConstantsXSerena.VAL_SELECT);
			t.addAttribute(ConstantsXSerena.ATTR_TARGET,
					ConstantsXSerena.TARGET_ALL);
			t.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "1");
			t.addAttribute(ConstantsXSerena.ATTR_ORDER_BY, "emissione");
			Element condElement = DocumentHelper
					.createElement(ConstantsXSerena.TAG_AND);
			Element cond = condElement.addElement("emissione");
			cond.setText("01/01/" + anno0);
			cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR,
					ConstantsXSerena.VAL_GREATER_THAN);
			q.addCondition(t, condElement);
			Document data = ApplicationLibrary.getData(q, request);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,
					"Tessera");
			if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> tessere = data.selectNodes(".//Tessera");
				for (Element tEl : tessere) {
					try {
						TesseraDTO tNew = new TesseraDTO();
						tNew.sabatoDomenica = tEl.elementText("sabatodomenica");
						tNew.emissione = tEl.elementText("emissione");
						Element utente = tEl.element("inverse_of_tessere")
								.element("Utente");
						tNew.id = utente.elementText("ID");
						/*
						tNew.cognome = utente.elementText("cognome");
						tNew.nome = utente.elementText("nome");
						Element statoNascita = utente.element("stato_n");
						tNew.italiano = statoNascita != null
								&& "Italia".equals(statoNascita.getText());
								*/
						answer.add(tNew);
					} catch (Exception r) {
						String message = "Errore in tessera "
								+ tEl.elementText("ID") + ": dati mancanti ...";
						logger.error(message);
					}
				}
			} else {
				String message = "Impossibile reperire tessere: "
						+ messages2[0];
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
