/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * 
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.antoniano.centroascolto;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.functions.DefaultFunction;
import eu.anastasis.serena.query.SelectQuery;

public class NumeroTessereAttiveFunction extends DefaultFunction {
	private static final Logger logger = Logger.getLogger(NumeroTessereAttiveFunction.class);
	private final static String FUNCTION_NAME = "FUN_NUMERO_TESSERE_ATTIVE";

	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request, Map<String, String> params) throws SerenaException {
		String nTessere=null;
		SelectQuery q = new SelectQuery("Tessera");
		Element t = q.getFirstClassElement();
		// t.addAttribute(ConstantsXSerena.ATTR_OPERATION,
		// ConstantsXSerena.VAL_SELECT);
		t.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
		t.addElement("ID");
		t.addElement("emissione");
		Element condElement = DocumentHelper.createElement(ConstantsXSerena.TAG_AND);
		Element cond = condElement.addElement("emissione");
		cond.setText(new SerenaDate().toString());
		cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_LESS_EQUAL_THAN);
		cond = condElement.addElement("scadenza");
		cond.setText(new SerenaDate().toString());
		cond.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_EQUAL_THAN);
		q.addCondition(t, condElement);
		Document data = ApplicationLibrary.getData(q, request);
		String[] messages2 = { "", "" };
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages2, "Tessera");
		if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
			List<Element> tessere = data.selectNodes(".//Tessera");
			nTessere= new Integer(tessere.size()).toString();
		} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {
			nTessere = "0";
		} else {
			String message = "Errore in computo tessere mancanti ...";
			logger.error(message);

		}
		return nTessere;
	}
}
