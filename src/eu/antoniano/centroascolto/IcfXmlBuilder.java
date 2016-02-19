package eu.antoniano.centroascolto;


import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.common.IoLibrary;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.exception.SerenaException;

/**
 * <classificazione>
	<area>Accoglienza</area>
	<item>
		<ItemCentroAscolto>
			<ordine>1</ordine>
			<descrizione></descrizione>
			<codice>D540 - Vestirsi</codice>
			<icf>205</icf>
		</ItemCentroAscolto>
 * @author afrascari
 *
 */

public class IcfXmlBuilder {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(IcfXmlBuilder.class);
	
	private static final String rulesFolder=ConstantsBaseLibrary.realpath + "/" + ConstantsBaseLibrary.CONF_PATH + "/controllers/";	
	private static final String rulesFile="DefIcf@ID@.xml";
	
	private static final String ACCOGLIENZA="Accoglienza";
	private static final String ATTIVAZIONE="Attivazione";
	private static final String INSERIMENTO="Inserimento";
	
	private Document rules=null;
	
	public static final String TAG_class="classificazione";
	public static final String TAG_item="item";
	public static final String TAG_itemCA="ItemCentroAscolto";
	public static final String TAG_ordine="ordine";
	public static final String TAG_decsrizione="descrizione";
	public static final String TAG_codice="codice";
	public static final String TAG_icf="icf";
	
	public static IcfXmlBuilder getInstanceAccoglienza() throws SerenaException
	{
		return new IcfXmlBuilder(ACCOGLIENZA);
	}
	
	public static IcfXmlBuilder getInstanceAttivazione() throws SerenaException
	{
		return new IcfXmlBuilder(ATTIVAZIONE);
	}
	
	public static IcfXmlBuilder getInstanceInserimento() throws SerenaException
	{
		return new IcfXmlBuilder(INSERIMENTO);
	}
	
	private IcfXmlBuilder(String whichInstance) throws SerenaException {
		loadRules(whichInstance);
	}
	
	
	private void loadRules(String whichInstance) throws SerenaException
	{
		
		try 
		{
			String theFile = rulesFile.replace("@ID@", whichInstance);
			rules = DocumentHelper.parseText(IoLibrary.readTextFile(rulesFolder+"/"+theFile));
		} catch (DocumentException e) {
			throw new SerenaException("Unable to parse rules file",e);
		} catch (IOException e) {
			throw new SerenaException("Unable to read rules file",e);
		}
	}
	
	public List<ItemIcfCentroAscolto> asBeans() throws SerenaException
	{

		List<Element> items = rules.selectNodes(".//"+TAG_itemCA);
		ArrayList<ItemIcfCentroAscolto> theList = new ArrayList<ItemIcfCentroAscolto>();
		for (Element anItem: items)	{
			ItemIcfCentroAscolto icfca = new ItemIcfCentroAscolto();
			icfca.codice_centro_ascolto = anItem.elementText(TAG_codice);
			icfca.descrizione = anItem.elementText(TAG_decsrizione);
			icfca.ordine = anItem.elementText(TAG_ordine);
			icfca.idIcfVero = anItem.elementText(TAG_icf);
			theList.add(icfca);
		}
		return theList;
	}
				
	
	
}
