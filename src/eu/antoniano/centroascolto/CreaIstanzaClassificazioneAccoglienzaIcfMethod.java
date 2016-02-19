package eu.antoniano.centroascolto;

import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.DetailEditMethod;
import eu.anastasis.serena.application.modules.object.ObjectModule;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.query.UpdateQuery;

public class CreaIstanzaClassificazioneAccoglienzaIcfMethod extends DetailEditMethod {

	public CreaIstanzaClassificazioneAccoglienzaIcfMethod(DefaultModule defaultModule, String[] defaultParameters) {
		super(defaultModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}
	
	private static final Logger logger = Logger
			.getLogger(CreaIstanzaClassificazioneAccoglienzaIcfMethod.class);


	@Override
	public void postValidInsertActions(HttpServletRequest request,
			Document query, Document ret, String[] messages)
			throws SerenaException {
		super.postValidInsertActions(request, query, ret, messages);
		try {
			String id = messages[1];
			List<ItemIcfCentroAscolto> itemPrevisti = getItemPrevisti();
			performUpdate(request,id,itemPrevisti);
		} catch (Exception e) {
			Vector<String> p = new Vector<String>();
			p.add(e.getMessage());
			throw new SerenaException(e.getMessage(),"",p);
		}

	} 
	
	private void performUpdate(HttpServletRequest request, String id, List<ItemIcfCentroAscolto> itemPrevisti) throws Exception {
		try	{
			UpdateQuery uq = new UpdateQuery("ClassificazioneIcfCentroAscolto",id);
			Element classElem = uq.getFirstClassElement();
			Element items = classElem.addElement("item");
			for (ItemIcfCentroAscolto item: itemPrevisti)	{
				Element anotheritemClass = items.addElement("ItemIcfCentroAscolto");
				anotheritemClass.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
				Element anAttr = anotheritemClass.addElement("codice_centro_ascolto");
				anAttr.setText(item.codice_centro_ascolto);
				anAttr = anotheritemClass.addElement("descrizione");
				anAttr.setText(item.descrizione);
				anAttr = anotheritemClass.addElement("ordine");
				anAttr.setText(item.ordine);
				if (item.idIcfVero!=null && !item.idIcfVero.isEmpty())	{
					// da valutare che operazione ne risultera ....
					anAttr = anotheritemClass.addElement("icf");
					anAttr = anAttr.addElement("Item_Icf_D");
					anAttr = anAttr.addElement("ID");
					anAttr.setText(item.idIcfVero);
				}
			}
	
			Document queryRes = ApplicationLibrary.setData(uq, request);
	
			if (XSerenaLibrary.isValidResult(queryRes)) {
				String[] messages = { "", "" };
				if (ConstantsXSerena.getXserenaRequestResult(queryRes, messages,
						"ItemIcfCentroAscolto") == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
					logger.debug("Inserimento ALBERO icf ok per classificazione " + id);
				} else {
					String message = "Inserimento ALBERO icf ok per classificazione " + id +" "
							+ messages[0];
					throw new Exception(message);
				}
			} else {
				String message = "Inserimento ALBERO icf ok per classificazione " + id ;
				throw new Exception(message);
			}
		} catch (Exception e){
			logger.fatal(e.getMessage());
			throw new Exception(e);
		}
	}

	private List<ItemIcfCentroAscolto> getItemPrevisti() throws SerenaException {
		return IcfXmlBuilder.getInstanceAccoglienza().asBeans();

	}

	/**
	protected Prestazione getPrestazione(String idPrestazione)
			throws PrestazioneNonEsistenteException {
		try {
			SelectQuery q = new SelectQuery(Prestazione.INSTANCE_NAME);
			Element prodotto = q.getFirstClassElement();
			Element condElement = DocumentHelper
					.createElement(ConstantsXSerena.TAG_AND);
			Element cond = condElement.addElement("ID");
			cond.setText(idPrestazione);
			q.addCondition(prodotto, condElement);
			Document data = new CronPersistenceHome().get(q);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,
					Prestazione.INSTANCE_NAME);
			if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				Element prodottoEl = (Element) data.selectSingleNode(".//"
						+ Prestazione.INSTANCE_NAME);
				return new CeReDiLiCoEleaBinder().createPrestazione(prodottoEl);
			} else {
				String message = "Impossibile reperire prestazione: "
						+ idPrestazione + " " + messages2[0];
				logger.error(message);
				return null;
			}
		} catch (Exception e) {
			String message = "Errore in getPrestazione " + idPrestazione;
			logger.error(message, e);
			throw new PrestazioneNonEsistenteException(message);
		}

	}
	*/
	

	@Override
	protected String getName() {
		return METHOD_NAME;
	}

	public static final String METHOD_NAME = "nuova_aclassificazione_accoglienza";

	protected String retrieveDefaultTemplateContext(String methodName) {

		return "object/" + methodName;
	}

}
