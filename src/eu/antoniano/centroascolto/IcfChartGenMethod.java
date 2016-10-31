package eu.antoniano.centroascolto;

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
import eu.anastasis.serena.common.SerenaDate;
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
		int[] data; // frequenza sui 12 mesi/sugli anni
		String pointPlacement= "on";
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
			//	cond = cond.addElement("inverse_of_presa_in_carico_attivazioni");
			//	cond = cond.addElement("Utente");
				cond = cond.addElement("ID");
				cond.setText(idProgetto);
			} else if ("Accoglienza".equals(theClass)) {
				Element cond = condElement.addElement("accoglienza");
				cond = cond.addElement("Accoglienza");
			//	cond = cond.addElement("inverse_of_presa_in_carico_accoglienza");
			//	cond = cond.addElement("Utente");
				cond = cond.addElement("ID");
				cond.setText(idProgetto);
			} else if ("Inserimento_Lavorativo".equals(theClass)) {
				Element cond = condElement.addElement("inserimento_lavorativo");
				cond = cond.addElement("Inserimento_Lavorativo");
			//	cond = cond.addElement("inverse_of_presa_in_carico_inserimenti_lavorativi");
			//	cond = cond.addElement("Utente");
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
					unaClassificazione.data = new int[icfitems.size()];
					int i = 0;
					for (Element icfitem : icfitems) {
						if (fillLabels) {
							answer.labels[i] = icfitem.elementText("codice_centro_ascolto");
						}
						try {
							unaClassificazione.data[i] = new Integer(icfitem.elementText("classificazione"))
									.intValue();
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
	
	/*

	protected JsonDTO getDataEquipe(HttpServletRequest request, String theClass, String dal, String al)
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
				cond = cond.addElement("inverse_of_presa_in_carico_attivazioni");
				cond = cond.addElement("Utente");
				cond = cond.addElement("ID");
				cond.setText(idUtente);
			} else if ("Accoglienza".equals(theClass)) {
				Element cond = condElement.addElement("accoglienza");
				cond = cond.addElement("Accoglienza");
				cond = cond.addElement("inverse_of_presa_in_carico_accoglienza");
				cond = cond.addElement("Utente");
				cond = cond.addElement("ID");
				cond.setText(idUtente);
			} else if ("Inserimento_Lavorativo".equals(theClass)) {
				Element cond = condElement.addElement("inserimento_lavorativo");
				cond = cond.addElement("Inserimento_Lavorativo");
				cond = cond.addElement("inverse_of_presa_in_carico_inserimenti_lavorativi");
				cond = cond.addElement("Utente");
				cond = cond.addElement("ID");
				cond.setText(idUtente);
			}
			q.addCondition(t, condElement);

			Document data = ApplicationLibrary.getData(q, request);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2, queryClass);
			int j = 0;
			if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> evals = data.selectNodes(".//" + queryClass);
				for (Element eval : evals) {
					// istanza di Classificazione
					answer.series = new JsonInnerDTO[evals.size()];
					JsonInnerDTO unaClassificazione = new JsonInnerDTO();
					unaClassificazione.name = eval.elementText("iter");
					List<Element> icfitems = eval.selectNodes(".//ItemIcfCentroAscolto");
					boolean fillLabels = false;
					if (answer.labels == null) {
						answer.labels = new String[icfitems.size()];
						fillLabels = true;
					}
					unaClassificazione.data = new int[icfitems.size()];
					int i = 0;
					for (Element icfitem : icfitems) {
						if (fillLabels) {
							answer.labels[i] = icfitem.elementText("codice");
						}
						try {
							unaClassificazione.data[i++] = new Integer(icfitem.elementText("classificazione"))
									.intValue();
						} catch (Exception e) {
							// item non classificato ... what to do?!?!?
							unaClassificazione.data[i++] = 8;
						}
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
*/
	
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
			}
		} catch (Exception e) {
			String theError = "Errore in generazione icf: " +  dominio + " " + richiesta + " - " + e.getMessage();
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