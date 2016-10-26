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
import eu.antoniano.centroascolto.EtaStatsMethod.Output;


public class AllStatsMethod extends JSONMethod {

	public AllStatsMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = Logger.getLogger(AllStatsMethod.class);

	public static final String METHOD_NAME = "all_stats";

	private static final String QUERY_TESSERE_RILASCIATE = "tessere-rilasciate";
	private static final String QUERY_MOTIVAZIONE_TESSERE = "motivazione-tessere";
	private static final String QUERY_TESSERE_PER_SESSO = "tessere-per-sesso";
	private static final String QUERY_TESSERE_PER_STATO = "tessere-per-stato";
	private static final String QUERY_TESSERE_PER_STATO_CIVILE = "tessere-per-stato-civile";
	private static final String QUERY_TESSERE_PER_RESIDENZA = "tessere-per-residenza";
	private static final String QUERY_UTENTI_PER_SESSO = "utenti-per-sesso";
	private static final String QUERY_UTENTI_PER_STATO = "utenti-per-stato";
	private static final String QUERY_UTENTI_PER_RESIDENZA = "utenti-per-residenza";
	private static final String QUERY_UTENTI_PER_STATO_CIVILE = "utenti-per-stato-civile";
	private static final String QUERY_EVENTI = "eventi";
	private static final String QUERY_ETA_PRIMO_COLLOQUIO = "eta-primo-colloquio";

	protected class UnitDTO {
		String id;
		String data;
		String val;
	}

	// verra associato ad ogni entry di anno/mese
	protected class Output {
		TreeMap<String, Integer> valori = new TreeMap<String, Integer>();
	}

	protected class JsonInnerDTO {
		String name; // valore della decodifica
		int[] data; // frequenza sui 12 mesi/sugli anni
	}

	protected class JsonDTO {
		String title;
		String asseY;
		JsonInnerDTO[] data; // tanti oggetti quanti sono i possibili valori della decodifica
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


		private List<UnitDTO> getAll(String anno, HttpServletRequest request, String query, JsonDTO res) throws SerenaException {

			if (QUERY_MOTIVAZIONE_TESSERE.equals(query)) {
				res.title = "Motivazione rilascio tessere: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero tessere rilasciate";
				return getData(anno, request,"Tessera","emissione","tipologia_tessera");
			} else if (QUERY_UTENTI_PER_SESSO.equals(query))	{
				res.title = "Utenti accolti suddivisi per sesso: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Utenti";
				return getData(anno, request,"Utente","data_primo_colloquio","sesso");
			} else if (QUERY_TESSERE_PER_SESSO.equals(query))	{
				res.title = "Utenti in carico suddivisi per sesso: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Utenti";
				return get2kevelData(anno, request,"Tessera","Utente","emissione","sesso");
			} else if (QUERY_UTENTI_PER_STATO.equals(query))	{
				res.title = "Utenti accolti suddivisi per paese di provenienza: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Utenti";
				return getData(anno, request,"Utente","data_primo_colloquio","stato_n");
			} else if (QUERY_TESSERE_PER_STATO.equals(query))	{
				res.title = "Utenti in carico suddivisi per paese di provenienza: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Utenti";
				return get2kevelData(anno, request,"Tessera","Utente","emissione","stato_n");
			} else if (QUERY_UTENTI_PER_STATO_CIVILE.equals(query))	{
				res.title = "Utenti  accolti suddivisi per stato civile: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Utenti";
				return getData(anno, request,"Utente","data_primo_colloquio","stato_civile");
			} else if (QUERY_TESSERE_PER_STATO_CIVILE.equals(query))	{
				res.title = "Utenti in carico suddivisi per stato civile: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Utenti";
				return get2kevelData(anno, request,"Tessera","Utente","emissione","stato_civile");
			} else if (QUERY_EVENTI.equals(query))	{
				res.title = "Eventi: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Eventi";
				return getData(anno, request,"Evento","data","tipo_nota");
			} else if (QUERY_UTENTI_PER_RESIDENZA.equals(query))	{
				res.title = "Utenti accolti con/privi di residenza: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Eventi";
				return getData(anno, request,"Utente","data_primo_colloquio","privo_residenza");
			} else if (QUERY_TESSERE_PER_RESIDENZA.equals(query))	{
				res.title = "Utenti in carico con/privi di residenza: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Eventi";
				return get2kevelData(anno, request,"Tessera","Utente","emissione","privo_residenza");
			} else if (QUERY_TESSERE_RILASCIATE.equals(query)){
				res.title = "Tessere rilasciate: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Tessere";
				return getTessereRilasciate(anno, request);
			} else if (QUERY_ETA_PRIMO_COLLOQUIO.equals(query)){
				res.title = "Eta' al primo colloquio: " + ((anno!=null)?anno:"per anno");
				res.asseY = "Numero Tessere";
				return getEtaMediaPrimoColloquio(anno, request);
			}
			else {
				String theError = "Richiesta non gestita: " + query;
				throw new SerenaException(theError);
			}
		}


		private List<UnitDTO> getEtaMediaPrimoColloquio(String anno, HttpServletRequest request) throws SerenaException {
			List<UnitDTO> all = getData(anno, request,"Utente","data_primo_colloquio","data_n");
			for (UnitDTO u:all){
				SerenaDate d0 = new SerenaDate(u.val);
				SerenaDate d1 = new SerenaDate(u.data);
				u.val = new Integer(d1.diffInYear(d0)).toString();
			}
			return all;
		}





		/**
		 * 
		 * @param anno0 - null = tutti gli anni
		 * @param request
		 * @return
		 * @throws SerenaException
		 */
		protected List<UnitDTO> getData(String anno0, HttpServletRequest request, String theClass ,String theDate,	String theAttribute )
				throws SerenaException {
			try {
				List<UnitDTO> answer = new ArrayList<UnitDTO>();
				
				SelectQuery q = new SelectQuery(theClass);
				Element t = q.getFirstClassElement();
				// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
				// ConstantsXSerena.VAL_SELECT);
				t.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
				t.addAttribute(ConstantsXSerena.ATTR_ORDER_BY, theDate);
				t.addElement("ID");
				t.addElement(theDate);
				t.addElement(theAttribute);				
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

		/**
		 * Come la precendete, ma pesca theAttribute dalla classe a secondo livello (esempio utente di tessere)
		 * @param anno0
		 * @param request
		 * @param level1Class
		 * @param level2Class
		 * @param theDate
		 * @param theAttribute
		 * @return
		 * @throws SerenaException
		 */
		protected List<UnitDTO> get2kevelData(String anno0, HttpServletRequest request, String level1Class ,String level2Class ,String theDate,	String theAttribute )
				throws SerenaException {
			try {
				List<UnitDTO> answer = new ArrayList<UnitDTO>();
				
				SelectQuery q = new SelectQuery(level1Class);
				Element t = q.getFirstClassElement();
				// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
				// ConstantsXSerena.VAL_SELECT);
				t.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
				t.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2");
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
				int res = ConstantsXSerena.getXserenaRequestResult(data, messages2, level1Class);
				if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
					List<Element> tessere = data.selectNodes(".//"+level1Class);
					for (Element tEl : tessere) {
						try {
							UnitDTO tNew = new UnitDTO();
							tNew.data = tEl.elementText(theDate);
							tNew.id = tEl.elementText("ID");
							Element level2Element = (Element)tEl.selectSingleNode(".//"+level2Class);
							tNew.val = level2Element.elementText(theAttribute);
							answer.add(tNew);
						} catch (Exception r) {
							String message = "Errore in " + level1Class + " " + tEl.elementText("ID") + ": dati mancanti ...";
							logger.error(message);
						}
					}
				} else {
					String message = "Impossibile reperire " + level1Class +": " + messages2[0];
					logger.error(message);
					throw new SerenaException(message);
				}
				return answer;
			} catch (Exception e) {
				String message = "Impossibile reperire " + level1Class +": " + e.getMessage();
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
			if (!isAnni)	{
				for (int i=1; i<13; i++){
					mesiAnni.put(i, new Output());
				}
			}
			for (UnitDTO t : units) {
				Output o = null;
				if (t.data==null)	{
					System.out.print(t.id);;
				}
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
		//		logger.debug("putting " + t.id  + " in " + t.val );
			}
			
			res.data = new JsonInnerDTO[possibleValues.size()]; // tante istanze quanti i possibili valori della decodifica
			// loop sui possibili valori
			int i=0;
			for (String val: possibleValues)	{
			//	logger.debug("Valore: "+val);
				JsonInnerDTO j = new JsonInnerDTO();
				res.data[i++] = j;
				j.name =  val;
				j.data = new int[xDim];
				// loop sui mesi/anni
				int k=0;
				for (Integer mese: mesiAnni.keySet()) {
				//	logger.debug("---> mese: " + mese + "(in " + k +")");
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
	
	
	/*********************** Caso particolare tessere rilasciate *****************************/
	
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
			if (anno0!=null){
				Element condElement = DocumentHelper.createElement(ConstantsXSerena.TAG_AND);
				Element cond = condElement.addElement("emissione");
				cond.setText("01/01/" + anno0);
				cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_EQUAL_THAN);
				cond = condElement.addElement("emissione");
				cond.setText("31/12/" + anno0);
				cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_LESS_EQUAL_THAN);
				q.addCondition(t, condElement);
			}
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
	
	private List<UnitDTO> getTessereRilasciate(String anno, HttpServletRequest request) throws SerenaException {
		
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		List<TesseraDTO> tessere = getTessereAttive(anno, request);
		// populate utenti map: quante tessere ha ogni utente? per distinguerle fra nuove e rilasci
		for (TesseraDTO t : tessere) {
			if (!map.containsKey(t.id)) {
				map.put(t.id, 1);
			} else {
				map.put(t.id, map.get(t.id) + 1);
			}
		}
		List<UnitDTO> answer = new ArrayList<UnitDTO>();
		for (TesseraDTO t : tessere) {
			boolean newTessera = map.get(t.id) == 1;
			UnitDTO u = new UnitDTO();
			u.id = t.id;
			u.data = t.emissione;
			u.val = newTessera?"nuove":"rinnovi";
			answer.add(u);
		}
		return answer;
	}
}