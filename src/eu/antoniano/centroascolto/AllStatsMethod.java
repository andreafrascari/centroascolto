package eu.antoniano.centroascolto;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
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

public class AllStatsMethod extends JSONMethod {

	public AllStatsMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = Logger.getLogger(AllStatsMethod.class);

	public static final String METHOD_NAME = "all_stats";

	private static final String QUERY_MOTIVAZIONE_TESSERE = "motivazione-tessere";

	private class UnitDTO {
		String id;
		String data;
		String val;
	}

	private class Output {
		TreeMap<String, Integer> valori = new TreeMap<String, Integer>();
	}

	private class JsonInnerDTO {
		String name; // valore della decodifica
		int[] data; // frequenza sui 12 mesi
	}

	private class JsonDTO {
		String title;
		String asseY;
		JsonInnerDTO[] data; // tanti oggetti quanti sono i possibili valori della decodifica
	}


		private List<UnitDTO> getAll(String anno, HttpServletRequest request, String query) throws SerenaException {

			if (QUERY_MOTIVAZIONE_TESSERE.equals(query)) {
				return getTesserePerMotivazione(anno, request);
			} else {
				String theError = "Richiesta non gestita: " + query;
				throw new SerenaException(theError);
			}
		}

		private List<UnitDTO> getTesserePerMotivazione(String anno0, HttpServletRequest request)
				throws SerenaException {
			try {
				List<UnitDTO> answer = new ArrayList<UnitDTO>();
				SelectQuery q = new SelectQuery("Tessera");
				Element t = q.getFirstClassElement();
				// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
				// ConstantsXSerena.VAL_SELECT);
				t.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
				t.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "1");
				t.addAttribute(ConstantsXSerena.ATTR_ORDER_BY, "emissione");
				Element condElement = DocumentHelper.createElement(ConstantsXSerena.TAG_AND);
				Element cond = condElement.addElement("emissione");
				cond.setText("01/01/" + anno0);
				cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_EQUAL_THAN);
				cond = condElement.addElement("emissione");
				cond.setText("31/12/" + anno0);
				cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_LESS_EQUAL_THAN);
				q.addCondition(t, condElement);
				Document data = ApplicationLibrary.getData(q, request);
				String[] messages2 = { "", "" };
				int res = ConstantsXSerena.getXserenaRequestResult(data, messages2, "Tessera");
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
	public String doMethod(HttpServletRequest request, HttpServletResponse response) throws JSONException {
		String query = request.getParameter("query");
		String anno = request.getParameter("year");
		JsonDTO res = new JsonDTO();
		try	{
			TreeMap<Integer, Output> mesi = new TreeMap<Integer, Output>();
			List<UnitDTO> units = getAll(anno, request, query);
			List<String> possibleValues = new ArrayList<String>();
			for (UnitDTO t : units) {
				SerenaDate c = new SerenaDate(t.data);
				if (!mesi.containsKey(c.getMonth()))	{
					mesi.put(c.getMonth(), new Output());
				}
				Output o = mesi.get(c.getMonth());
				if (o.valori.containsKey(t.val)){
					o.valori.put(t.val, (o.valori.get(t.val)+1));
					if (!possibleValues.contains(t.val)){
						possibleValues.add(t.val);
					}
				} else {
					o.valori.put(t.val,1);
				}
			}
			res.title = anno;
			res.asseY = "Numero tessere rilasciate";
			res.data = new JsonInnerDTO[possibleValues.size()]; // tante istanze quanti i possibili valori della decodifica
			// loop sui possibili valori
			int i=0;
			for (String val: possibleValues)	{
				JsonInnerDTO j = new JsonInnerDTO();
				res.data[i++] = j;
				j.name =  val;
				j.data = new int[12];
				// loop sui mesi
				int k=0;
				for (Integer mese: mesi.keySet()) {
					Output o = mesi.get(mese);
					if (o.valori.containsKey(val))	{
						j.data[k++] = o.valori.get(val);
					} else {
						j.data[k++]=0;
					}
				}
			}
	} catch (Exception e) {
		String theError = "Errore in calcolo frequenze: " + query +" - " + e.getMessage();
		e.printStackTrace();
		throw new JSONException(theError);
	}
	logger.debug("Returning json....");
	return  new Gson().toJson(res);
	}

	@Override
	protected String getName() {
		return METHOD_NAME;
	}
}