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
	private static final String QUERY_UTENTI_PER_SESSO = "utenti-per-sesso";
	private static final String QUERY_UTENTI_PER_STATO = "utenti-per-stato";

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
		int[] data; // frequenza sui 12 mesi/sugli anni
	}

	private class JsonDTO {
		String title;
		String asseY;
		JsonInnerDTO[] data; // tanti oggetti quanti sono i possibili valori della decodifica
	}


		private List<UnitDTO> getAll(String anno, HttpServletRequest request, String query, JsonDTO res) throws SerenaException {

			if (QUERY_MOTIVAZIONE_TESSERE.equals(query)) {
				res.title = "Motivazione rilascio tessere: " + ((anno!=null)?anno:"");
				res.asseY = "Numero tessere rilasciate";
				return getData(anno, request,"Tessera","emissione","tipologia_tessera");
			} if (QUERY_UTENTI_PER_SESSO.equals(query))	{
				res.title = "Utenti per sesso: " + ((anno!=null)?anno:"");
				res.asseY = "Numero Utenti";
				return getData(anno, request,"Utente","data_primo_colloquio","sesso");
			} if (QUERY_UTENTI_PER_STATO.equals(query))	{
				res.title = "Utenti per paese di provenienza: " + ((anno!=null)?anno:"");
				res.asseY = "Numero Utenti";
				return getData(anno, request,"Utente","data_primo_colloquio","stato_n");
			}
			else {
				String theError = "Richiesta non gestita: " + query;
				throw new SerenaException(theError);
			}
		}


		
		/**
		 * 
		 * @param anno0 - null = tutti gli anni
		 * @param request
		 * @return
		 * @throws SerenaException
		 */
		private List<UnitDTO> getData(String anno0, HttpServletRequest request, String theClass ,String theDate,	String theAttribute )
				throws SerenaException {
			try {
				List<UnitDTO> answer = new ArrayList<UnitDTO>();
				
				SelectQuery q = new SelectQuery(theClass);
				Element t = q.getFirstClassElement();
				// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
				// ConstantsXSerena.VAL_SELECT);
				t.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
				t.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "1");
				t.addAttribute(ConstantsXSerena.ATTR_ORDER_BY, theDate);
				if (anno0!=null){
					Element condElement = DocumentHelper.createElement(ConstantsXSerena.TAG_AND);
					Element cond = condElement.addElement(theDate);
					cond.setText("01/01/" + anno0);
					cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_EQUAL_THAN);
					cond = condElement.addElement(theDate);
					cond.setText("31/12/" + anno0);
					cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_LESS_EQUAL_THAN);
					q.addCondition(t, condElement);
				}
				Document data = ApplicationLibrary.getData(q, request);
				String[] messages2 = { "", "" };
				int res = ConstantsXSerena.getXserenaRequestResult(data, messages2, theClass);
				if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
					List<Element> tessere = data.selectNodes(".//"+theClass);
					for (Element tEl : tessere) {
						try {
							UnitDTO tNew = new UnitDTO();
							tNew.data = tEl.elementText(theDate);
							tNew.id = tEl.elementText("ID");
							tNew.val = tEl.elementText(theAttribute);
							answer.add(tNew);
						} catch (Exception r) {
							String message = "Errore in " + theClass + " " + tEl.elementText("ID") + ": dati mancanti ...";
							logger.error(message);
						}
					}
				} else {
					String message = "Impossibile reperire " + theClass +": " + messages2[0];
					logger.error(message);
					throw new SerenaException(message);
				}
				return answer;
			} catch (Exception e) {
				String message = "Impossibile reperire " + theClass +": " + e.getMessage();
				logger.error(message);
				throw new SerenaException(message);
			}
	}

	@Override
	public String doMethod(HttpServletRequest request, HttpServletResponse response) throws JSONException {
		String query = request.getParameter("query");
		String anno = request.getParameter("year");
		JsonDTO res = new JsonDTO();
		boolean isAnni = (anno==null);
		int xDim = (isAnni)?(new SerenaDate().getYear()-2006):12; // dim x? se statistica anni -> anno corrente -2007 (primo anno) +1. Se mesi: qw
		logger.debug("xdim = " + xDim);
		try	{
			TreeMap<Integer, Output> mesiAnni = new TreeMap<Integer, Output>();
			List<UnitDTO> units = getAll(anno, request, query, res);
			List<String> possibleValues = new ArrayList<String>();
			for (UnitDTO t : units) {
				Output o = null;
				SerenaDate c = new SerenaDate(t.data);
				if (c.getYear()<=2006 || c.getYear()>new SerenaDate().getYear()){
					continue; // dont want them
				}
				if (isAnni)	{
					if (!mesiAnni.containsKey(c.getYear()))	{
						mesiAnni.put(c.getYear(), new Output());
					}
					o = mesiAnni.get(c.getYear());
				} else {
					if (!mesiAnni.containsKey(c.getMonth()))	{
						mesiAnni.put(c.getMonth(), new Output());
					}
					o = mesiAnni.get(c.getMonth());
				}
				if (t.val==null || t.val.trim().isEmpty()){
					t.val = "non definito";
				}
				if (o.valori.containsKey(t.val)){
					o.valori.put(t.val, (o.valori.get(t.val)+1));
				} else {
					o.valori.put(t.val,1);
				}
				if (!possibleValues.contains(t.val)){
					possibleValues.add(t.val);
				}
				logger.debug("putting " + t.id  + " in " + t.val );
			}
			
			res.data = new JsonInnerDTO[possibleValues.size()]; // tante istanze quanti i possibili valori della decodifica
			// loop sui possibili valori
			int i=0;
			for (String val: possibleValues)	{
				logger.debug("Valore: "+val);
				JsonInnerDTO j = new JsonInnerDTO();
				res.data[i++] = j;
				j.name =  val;
				j.data = new int[xDim];
				// loop sui mesi/anni
				int k=0;
				for (Integer mese: mesiAnni.keySet()) {
					logger.debug("---> mese: " + mese + "(in " + k +")");
					Output o = mesiAnni.get(mese);
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