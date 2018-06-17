package eu.antoniano.centroascolto;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.exceptions.JSONException;
import eu.antoniano.centroascolto.AllStatsMethod.Output;
import eu.antoniano.centroascolto.AllStatsMethod.TesseraDTO;
import eu.antoniano.centroascolto.AllStatsMethod.UnitDTO;

public class EtaStatsMethod extends AllStatsMethod {

	public EtaStatsMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = Logger.getLogger(EtaStatsMethod.class);

	public static final String METHOD_NAME = "eta_stats";

	private static final String QUERY_ETA_MEDIA_PRIMO_COLLOQUIO = "eta-media-primo-colloquio";
	private static final String QUERY_ETA_MEDIA_UTENTI_ATTIVI = "eta-media-utenti-attivi";


	protected class Output {
		float etaMedia;
		int numIstanze;

		public void adjustWith(String theVal) {
			float eta = new Float(theVal).floatValue();
			etaMedia = (etaMedia * numIstanze + eta) / (++numIstanze);
		}

		public Output(String eta) {
			etaMedia = new Float(eta).floatValue();
			numIstanze = 1;
		}
	}

	private List<UnitDTO> getAll(String anno, HttpServletRequest request, String query, JsonDTO res)
			throws SerenaException {

		if (QUERY_ETA_MEDIA_PRIMO_COLLOQUIO.equals(query)) {
			res.title = "Eta' al primo colloquio: " + ((anno != null) ? anno : "per anno");
			res.asseY = "Eta' media";
			return getEtaMediaPrimoColloquio(anno, request);
		} else if (QUERY_ETA_MEDIA_UTENTI_ATTIVI.equals(query)){
			res.title = "Eta' media utenti con tessera: " + ((anno!=null)?anno:"per anno");
			res.asseY = "Numero Tessere";
			return getEtaMediaUtentiAttivi(anno, request);
		} else {
			String theError = "Richiesta non gestita: " + query;
			throw new SerenaException(theError);
		}
	}

	private List<UnitDTO> getEtaMediaPrimoColloquio(String anno, HttpServletRequest request) throws SerenaException {
		List<UnitDTO> all = getData(anno, request, "Utente", "data_primo_colloquio", "data_n");
		for (UnitDTO u : all) {
			SerenaDate d0 = new SerenaDate(u.val);
			SerenaDate d1 = new SerenaDate(u.data);
			u.val = new Integer(d1.diffInYear(d0)).toString();
		}
		return all;
	}


	@Override
	public String doMethod(HttpServletRequest request, HttpServletResponse response) throws JSONException {
		String query = request.getParameter("query");
		String anno = request.getParameter("year");
		JsonDTO res = new JsonDTO();
		if (anno.equals("-1")){
			anno=null;
		}
		
		boolean isAnni = (anno==null);
		
		int annoUltimo = new SerenaDate().getYear();
		int annoPrimo = annoUltimo-9;
		
		int xDim = (isAnni)?10:12; // ANNI: SEMPRE 10 ... a partire da anno corrente
		logger.debug("xdim = " + xDim);
		
		
		try {
			TreeMap<Integer, Output> mesiAnni = new TreeMap<Integer, Output>();
			List<UnitDTO> units = getAll(anno, request, query, res);
			List<String> possibleValues = new ArrayList<String>();
			if (!isAnni)	{
				for (int i=1; i<13; i++){
					mesiAnni.put(i, new Output("0"));
				}
			} else {
				for (int i=annoPrimo; i<=annoUltimo; i++){ // INTERVENIRE ++ x ULTERIORE ANNO
					mesiAnni.put(i, new Output("0"));
				}
			}
			for (UnitDTO t : units) {
				Output o = null;
				SerenaDate c = new SerenaDate(t.data);
				if (c.getYear()< annoPrimo || c.getYear()>new SerenaDate().getYear()){
					continue; // dont want them
				}
				if (isAnni) {
					if (!mesiAnni.containsKey(c.getYear())) {
						mesiAnni.put(c.getYear(), new Output(t.val));
					} else {
						Output soFar = mesiAnni.get(c.getYear());
						soFar.adjustWith(t.val);
						mesiAnni.put(c.getYear(), soFar);
					}
				} else {
					if (!mesiAnni.containsKey(c.getMonth())) {
						mesiAnni.put(c.getMonth(), new Output(t.val));
					} else {
						Output soFar = mesiAnni.get(c.getMonth());
						soFar.adjustWith(t.val);
						mesiAnni.put(c.getMonth(), soFar);
					}
				}
			}

			res.data = new JsonInnerDTO[1]; // tante istanze quanti i possibili
											// valori della decodifica
			// loop sui possibili valori
			// logger.debug("Valore: "+val);
			JsonInnerDTO j = new JsonInnerDTO();
			res.data[0] = j;
			j.name = "Eta' media";
			j.data = new int[xDim];
			// loop sui mesi/anni
			int k = 0;
			for (Integer mese : mesiAnni.keySet()) {
				Output o = mesiAnni.get(mese);
				j.data[k++] = new Float(o.etaMedia).intValue();
			}
		} catch (Exception e) {
			String theError = "Errore in calcolo frequenze: " + query + " - " + e.getMessage();
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