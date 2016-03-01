package eu.antoniano.centroascolto;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.exceptions.JSONException;
import eu.anastasis.serena.query.UpdateQuery;

/**
 * http://localhost:8080/CentroAscolto/Index?fme=1&q=object/detail_edit&p=ClassificazioneIcfCentroAscolto/_a_ID/_v_5&t=ClassificazioneIcfCentroAscolto_Edit
 * @author andrea
 *
 */

public class RegistraClassificazioneIcfMethod extends DefaultMethod {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RegistraClassificazioneIcfMethod.class);

	private static final String EVAL_PREFIX = "EVAL_";
	private static final String OSS_PREFIX = "OSS_";
	private static final String PROC_PREFIX = "PROC_";

	private static final String METHOD_NAME = "registraClassificazioneIcf";

	@Override
	protected String getName() {
		return METHOD_NAME;
	}
	
	private class Eval	{
		String eval;
		String oss;
		String proc;
	}

	public RegistraClassificazioneIcfMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		try {
			String idClassificazione = request.getParameter("ID");

			Hashtable<String, Eval> tmpMap = new Hashtable<String, Eval>();

			String attivazione = request.getParameter("attivazione");
			String inserimento = request.getParameter("inserimento");
			String accoglienza = request.getParameter("accoglienza");
			String note = request.getParameter("NOTE");

			Enumeration<String> keys = request.getParameterNames();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				if (key.contains(EVAL_PREFIX)) {
					String idItem = key.replace(EVAL_PREFIX, "");
					String value = request.getParameter(key);
					if (value!=null && ! value.isEmpty())	{
						String theOss = OSS_PREFIX+idItem;
						String theProc = PROC_PREFIX+idItem;
						Eval e = new Eval();
						e.eval = value;
						e.proc = request.getParameter(theProc);
						e.oss = request.getParameter(theOss);
						tmpMap.put(idItem, e);
					}		
				}
			}
			performUpdate(request, idClassificazione, tmpMap, note);

			response.sendRedirect("?q=object/detail&p=ClassificazioneIcfCentroAscolto/_a_ID/_v_"+idClassificazione+"&m=Registrazione avvenuta correttamente");
			
			return "";
		} catch (Exception e) {
			String msg = "Errore nella registrazione della classificazione: contattare assistenza";
			logger.fatal(msg+ " "+e.getMessage());
			return msg;
		}
	}

	private void performUpdate(HttpServletRequest request, String id, Hashtable<String, Eval> evals, String note)
			throws Exception {
		try {
			UpdateQuery uq = new UpdateQuery("ClassificazioneIcfCentroAscolto", id);
			Element classElem = uq.getFirstClassElement();
			if (note!=null && !note.isEmpty())	{
				Element noteEl = classElem.addElement("note");
				noteEl.setText(note);
			}
			Element items = classElem.addElement("item");
			for (String item : evals.keySet()) {
				Eval theEval = evals.get(item);
				String theVal = theEval.eval;
				if (theVal != null && !theVal.isEmpty()) {
					Element anotheritemClass = items.addElement("ItemIcfCentroAscolto");
					anotheritemClass.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
					Element cond=anotheritemClass.addElement(ConstantsXSerena.TAG_CONDITION);
					cond=cond.addElement("ID");
					cond.setText(item);
					Element anAttr = anotheritemClass.addElement("classificazione");
					anAttr.setText(theVal);
					if (theEval.oss!=null && !theEval.oss.isEmpty()){
						anAttr = anotheritemClass.addElement("osservazione");
						anAttr.setText(theEval.oss);
					}
					if (theEval.proc!=null && !theEval.proc.isEmpty()){
						anAttr = anotheritemClass.addElement("procedura_classificazione");
						anAttr.setText(theEval.proc);
					}
					logger.debug("Classificazione icf:  item " + item + " eval " + theVal);
				}
			}

			Document queryRes = ApplicationLibrary.setData(uq, request);

			if (XSerenaLibrary.isValidResult(queryRes)) {
				String[] messages = { "", "" };
				if (ConstantsXSerena.getXserenaRequestResult(queryRes, messages,
						"ItemIcfCentroAscolto") == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
					logger.debug("Classificazione " + id);
				} else {
					String message = "Classificazione " + id + " " + messages[0];
					throw new Exception(message);
				}
			} else {
				String message = "Classificazione " + id;
				throw new Exception(message);
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			throw new Exception(e);
		}
	}
}
