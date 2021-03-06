package eu.antoniano.centroascolto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.gson.Gson;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.core.modules.JSONMethod;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.exceptions.JSONException;
import eu.anastasis.serena.query.SelectQuery;

public class IcfChartGenMethod extends JSONMethod {

	public IcfChartGenMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = Logger.getLogger(IcfChartGenMethod.class);

	public static final String METHOD_NAME = "icf_chart_gen";

	private static final String PAR_RICHIESTA = "richiesta";
	private static final String PAR_PERSONA_EQUIPE = "dominio";
	private static final String PAR_ID = "id";
	private static final String PAR_DATA_DAL = "dal";
	private static final String PAR_DATA_AL = "al";

	protected class JsonInnerDTO {
		String name; // valore della decodifica
		float[] data; // frequenza sui 12 mesi/sugli anni
		String pointPlacement = "on";
	}

	protected class JsonDTO {
		String[] labels; // frequenza sui 12 mesi/sugli anni
		JsonInnerDTO[] series;
		int number_of_occurrences;
	}

	protected JsonDTO getDataPersona(HttpServletRequest request, String theClass, String idProgetto)
			throws SerenaException {
		try {
			JsonDTO answer = new JsonDTO();
			String queryClass = "ClassificazioneIcfCentroAscolto";

			SelectQuery q = new SelectQuery(queryClass);
			Element t = q.getFirstClassElement();
			// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
			// ConstantsXSerena.VAL_SELECT);
			t.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			t.addAttribute(ConstantsXSerena.ATTR_ORDER_BY, "data");
			t.addElement("ID");
			t.addElement("data");
			t.addElement("iter");
			Element items = t.addElement("item");
			items = items.addElement("ItemIcfCentroAscolto");
			items.addElement("codice");
			items.addElement("ordine");
			items.addElement("classificazione");
			Element condElement = DocumentHelper.createElement(ConstantsXSerena.TAG_AND);
			if ("Attivazione".equals(theClass)) {
				Element cond = condElement.addElement("attivazione");
				cond = cond.addElement("Attivazione");
				// cond =
				// cond.addElement("inverse_of_presa_in_carico_attivazioni");
				// cond = cond.addElement("Utente");
				cond = cond.addElement("ID");
				cond.setText(idProgetto);
			} else if ("Accoglienza".equals(theClass)) {
				Element cond = condElement.addElement("accoglienza");
				cond = cond.addElement("Accoglienza");
				// cond =
				// cond.addElement("inverse_of_presa_in_carico_accoglienza");
				// cond = cond.addElement("Utente");
				cond = cond.addElement("ID");
				cond.setText(idProgetto);
			} else if ("Inserimento_Lavorativo".equals(theClass)) {
				Element cond = condElement.addElement("inserimento_lavorativo");
				cond = cond.addElement("Inserimento_Lavorativo");
				// cond =
				// cond.addElement("inverse_of_presa_in_carico_inserimenti_lavorativi");
				// cond = cond.addElement("Utente");
				cond = cond.addElement("ID");
				cond.setText(idProgetto);
			}
			q.addCondition(t, condElement);

			Document data = ApplicationLibrary.getData(q, request);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2, queryClass);
			int j = 0;
			if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> evals = data.selectNodes(".//" + queryClass);
				answer.series = new JsonInnerDTO[evals.size()];
				for (Element eval : evals) {
					// istanza di Classificazione
					JsonInnerDTO unaClassificazione = new JsonInnerDTO();
					unaClassificazione.name = eval.elementText("iter");
					List<Element> icfitems = eval.selectNodes(".//ItemIcfCentroAscolto");
					boolean fillLabels = false;
					if (answer.labels == null) {
						answer.labels = new String[icfitems.size()];
						fillLabels = true;
					}
					unaClassificazione.data = new float[icfitems.size()];
					int i = 0;
					for (Element icfitem : icfitems) {
						if (fillLabels) {
							answer.labels[i] = icfitem.elementText("codice_centro_ascolto");
						}
						try {
							unaClassificazione.data[i] = new Integer(icfitem.elementText("classificazione")).intValue();
						} catch (Exception e) {
							// item non classificato ... what to do?!?!?
							unaClassificazione.data[i] = 8;
						}
						i++;
					}
					answer.series[j++] = unaClassificazione;
				}
			} else {
				String message = "Impossibile reperire classificazioni icf per " + theClass + ": " + messages2[0];
				logger.error(message);
				throw new SerenaException(message);
			}
			return answer;
		} catch (Exception e) {
			String message = "Impossibile reperire classificazioni icf per " + theClass + ": " + e.getMessage();
			logger.error(message);
			throw new SerenaException(message);
		}
	}

	protected JsonDTO getDataEquipe(HttpServletRequest request, String theClass, String dal, String al)
			throws SerenaException {
		try {
			JsonDTO answer = new JsonDTO();
			String queryClass = "ClassificazioneIcfCentroAscolto";
			int occurrencesNumber = 0;
			HashMap<String, HashMap<String, List<Integer>>> all = new HashMap<String, HashMap<String, List<Integer>>>();

			SelectQuery q = new SelectQuery(queryClass);
			Element t = q.getFirstClassElement();
			// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
			// ConstantsXSerena.VAL_SELECT);
			t.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			t.addAttribute(ConstantsXSerena.ATTR_ORDER_BY, "data");
			t.addElement("ID");
			t.addElement("data");
			t.addElement("iter");
			Element items = t.addElement("item");
			items = items.addElement("ItemIcfCentroAscolto");
			items.addElement("codice");
			items.addElement("ordine");
			items.addElement("classificazione");
			Element condElement = DocumentHelper.createElement(ConstantsXSerena.TAG_AND);
			if ("Attivazione".equals(theClass)) {
				Element cond = condElement.addElement("attivazione");
				cond = cond.addElement("Attivazione");
				if (dal != null && !dal.isEmpty()) {
					Element dalCond = cond.addElement("dal");
					dalCond.setText(("01/01/" + dal));
					dalCond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_THAN);
				}
				if (al != null && !al.isEmpty()) {
					Element alCond = cond.addElement("al");
					alCond.setText("31/12/" + al);
					alCond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_LESS_THAN);
				}
			} else if ("Accoglienza".equals(theClass)) {
				Element cond = condElement.addElement("accoglienza");
				cond = cond.addElement("Accoglienza");
				if (dal != null && !dal.isEmpty()) {
					Element dalCond = cond.addElement("dal");
					dalCond.setText(("01/01/" + dal));
					dalCond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_THAN);
				}
				if (al != null && !al.isEmpty()) {
					Element alCond = cond.addElement("al");
					alCond.setText("31/12/" + al);
					alCond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_LESS_THAN);
				}
			} else if ("Inserimento_Lavorativo".equals(theClass)) {
				Element cond = condElement.addElement("inserimento_lavorativo");
				cond = cond.addElement("Inserimento_Lavorativo");
				if (dal != null && !dal.isEmpty()) {
					Element dalCond = cond.addElement("dal");
					dalCond.setText(("01/01/" + dal));
					dalCond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_THAN);
				}
				if (al != null && !al.isEmpty()) {
					Element alCond = cond.addElement("al");
					alCond.setText("31/12/" + al);
					alCond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_LESS_THAN);
				}
			}
			q.addCondition(t, condElement);

			Document data = ApplicationLibrary.getData(q, request);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2, queryClass);
			int j = 0;
			int minNumberOfClassifications = 9999;
			if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> evals = data.selectNodes(".//" + queryClass);
				for (Element eval : evals) {
					// istanza di Classificazione
					String iter = eval.elementText("iter");
					if (!all.containsKey(iter)) {
						all.put(iter, new HashMap<String, List<Integer>>());
					}
					List<Element> icfitems = eval.selectNodes(".//ItemIcfCentroAscolto");
					boolean fillLabels = false;
					if (answer.labels == null) {
						answer.labels = new String[icfitems.size()];
						fillLabels = true;
					}
					int i = 0;
					for (Element icfitem : icfitems) {
						String questoCodice = icfitem.elementText("codice_centro_ascolto");
						if (fillLabels) {
							answer.labels[i++] = questoCodice;
						}
						try {
							HashMap<String, List<Integer>> iterMap = all.get(iter);
							if (!iterMap.containsKey(questoCodice)) {
								iterMap.put(questoCodice, new ArrayList<Integer>());
							}
							iterMap.get(questoCodice).add(new Integer(icfitem.elementText("classificazione")));
						} catch (Exception e) {
							;
						}
					}
				}
				answer.series = new JsonInnerDTO[all.keySet().size()];
				for (String unaClassificazione : all.keySet()) {
					if (all.get(unaClassificazione).size() < minNumberOfClassifications) {
						minNumberOfClassifications = all.get(unaClassificazione).size();
					}
					answer.series[j++] = getClassificazione(unaClassificazione, all.get(unaClassificazione));
				}
				answer.number_of_occurrences = minNumberOfClassifications;

			} else {
				String message = "Impossibile reperire classificazioni icf per " + theClass + ": " + messages2[0];
				logger.error(message);
				throw new SerenaException(message);
			}
			return answer;
		} catch (Exception e) {
			String message = "Impossibile reperire classificazioni icf per " + theClass + ": " + e.getMessage();
			logger.error(message);
			throw new SerenaException(message);
		}
	}

	/**
	 * Torna le medie di un "iter" di classificazioni (es la iniziale)
	 * 
	 * @param hashMap
	 * @return
	 */
	private JsonInnerDTO getClassificazione(String key, HashMap<String, List<Integer>> iterMap) {
		JsonInnerDTO res = new JsonInnerDTO();
		int keysDim = iterMap.keySet().size();
		res.data = new float[keysDim];
		res.name = key;
		int i = 0;
		for (String aKey : iterMap.keySet()) {
			List<Integer> keyValues = iterMap.get(aKey);
			res.data[i++] = computeFromArray(keyValues);
		}
		return res;
	}

	/**
	 * Summary value is (now) median
	 * 
	 * @param keyValues
	 * @return
	 */
	private float computeFromArray(List<Integer> keyValues) {
		int theSize = computeSizeSkipping89(keyValues);
		if (theSize==0)	{
			// all 8/9
			return 8;
		}
		int[] theArray = new int[theSize];
		int i=0;
		for (Integer x : keyValues) {
			if (x<8){theArray[i++]=x;}
		}
		//return median(theArray);	
		float res =  average(theArray);	
		logger.info("res id " +res);
		return res;
	}

	private int computeSizeSkipping89(List<Integer> keyValues) {
		int theSize = 0;
		for (Integer x : keyValues) {
			if (x<8){
				theSize++;
			}
		}
		return theSize;
	}

	public int median(int[] l) {
		Arrays.sort(l);
		int middle = l.length / 2;
		if (l.length % 2 == 0) {
			int left = l[middle - 1];
			int right = l[middle];
			return (left + right) / 2;
		} else {
			return l[middle];
		}
	}

	public float average(int[] l) {
		float avg = 0;
		for (int i=0; i<l.length; i++)	{
			avg+=l[i];
		}
		return avg/l.length;
	}

	@Override
	public String doMethod(HttpServletRequest request, HttpServletResponse response) throws JSONException {

		String richiesta = request.getParameter(PAR_RICHIESTA);
		String dominio = request.getParameter(PAR_PERSONA_EQUIPE);
		String id = request.getParameter(PAR_ID);
		String dal = request.getParameter(PAR_DATA_DAL);
		String al = request.getParameter(PAR_DATA_AL);

		JsonDTO res = new JsonDTO();
		logger.debug("IcfChartGenerator " + dominio + " " + richiesta);
		try {
			if ("persona".equals(dominio)) {
				res = getDataPersona(request, richiesta, id);
			} else if ("equipe".equals(dominio)) {
				res = getDataEquipe(request, richiesta, dal, al);
			}
		} catch (Exception e) {
			String theError = "Errore in generazione icf: " + dominio + " " + richiesta + " - " + e.getMessage();
			e.printStackTrace();
			throw new JSONException(theError);
		}
		logger.debug("Returning json....");
		return new Gson().toJson(res);
	}

	@Override
	protected String getName() {
		return METHOD_NAME;
	}

}