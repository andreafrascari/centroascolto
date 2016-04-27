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

public class YearReportTemplateMethod extends GiveMethod {

	private static final Logger logger = Logger
			.getLogger(YearReportTemplateMethod.class);

	public static final String METHOD_NAME = "year_report";

	private static final String QUERY_TESSERE_PER_MOTIVAZIONE = "tessere-per-motivazione";

	private class UnitDTO {
		String id;
		String data;
		String val;
	}

	private class Output {
		String xlabel;
		int totale = 0;
		TreeMap<String, Integer> valori = new TreeMap<String, Integer>();
	}

	public YearReportTemplateMethod(DefaultModule parentModule,
			String[] defaultParameters) {
		super(parentModule, defaultParameters);
	}

	protected Document retrieveData(HttpServletRequest request,
			ReportEngine engine, File designFile) throws SerenaException {
		final Document document = DocumentHelper.createDocument();
		try {
			String query = request.getParameter("query");
			String anno0 = request.getParameter("dal");

			document.setXMLEncoding("UTF-8");
			Element currentElement = document
					.addElement(ConstantsXSerena.TAG_SERENA);
			Element base = currentElement
					.addElement(ConstantsXSerena.TAG_COMMAND);

			TreeMap<String, Output> anni = new TreeMap<String, Output>();
			List<UnitDTO> units = getAll(anno0, request, query);

			Element anniEl = base.addElement("anni");
			SerenaDate t0 = new SerenaDate("01/01/" + anno0);
			SerenaDate today = new SerenaDate();
			// external loop
			while (t0.isBefore(today)) {
				SerenaDate t1 = (SerenaDate) t0.clone();
				t1.addYears(1);
				String annolabel = new Integer(t0.getYear()).toString();
				if (t0.getMonth() == 1) {
					Output a = new Output();
					a.xlabel = annolabel;
					anni.put(annolabel, a);
					logger.debug("ANNI: Adding " + annolabel);
				}
				// internal loop over units
				for (UnitDTO t : units) {
					SerenaDate c = new SerenaDate(t.data);
					if (t0.isBefore(c) && c.isBefore(t1)) {
						Output u = anni.get(annolabel);
						u.totale++;
						if (u.valori.containsKey(t.val)) {
							u.valori.put(t.val, u.valori.get(t.val) + 1);
						} else {
							u.valori.put(t.val, 1);
						}
						anni.put(annolabel, u);
					}
				}
				t0 = t1;
			}
			for (Output a : anni.values()) {
				Element annoEl = anniEl.addElement("Anno");
				Element currentEl = annoEl.addElement("totale");
				currentEl.setText(new Integer(a.totale).toString());
				currentEl = annoEl.addElement("x");
				currentEl.setText(a.xlabel);
				int i=1;
				for (String aVal : a.valori.keySet()) {
					currentEl = annoEl.addElement("y"+i++);
					currentEl.setText(new Integer(a.valori.get(aVal))
							.toString());
				}
			}
		} catch (Exception e) {
			String theError = "Errore in generazione report tessere attive: "
					+ e.getMessage();
			throw new SerenaException(theError);
		}
		return document;
	}

	private List<UnitDTO> getAll(String anno0, HttpServletRequest request,
			String query) throws SerenaException {

		if (QUERY_TESSERE_PER_MOTIVAZIONE.equals(query)) {
			return getTesserePerMotivazione(anno0, request);
		} else {
			String theError = "Richiesta non gestita: " + query;
			throw new SerenaException(theError);
		}
	}

	private List<UnitDTO> getTesserePerMotivazione(String anno0,
			HttpServletRequest request) throws SerenaException {
		try {
			List<UnitDTO> answer = new ArrayList<UnitDTO>();
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
						UnitDTO tNew = new UnitDTO();
						tNew.data = tEl.elementText("emissione");
						tNew.id = tEl.elementText("ID");
						tNew.val = tEl.elementText("tipologia_tessera");
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
