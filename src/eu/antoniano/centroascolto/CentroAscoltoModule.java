package eu.antoniano.centroascolto;

import eu.anastasis.serena.application.core.modules.DefaultModule;


public class CentroAscoltoModule extends DefaultModule {
public final static String MODULE_NAME = "centroascolto";

	protected void setUpMethods()
	{
		addMethod(new CreaIstanzaClassificazioneAccoglienzaIcfMethod(this, getDefaultParameters()));
		addMethod(new RegistraClassificazioneIcfMethod(this, getDefaultParameters()));
	}
	
	@Override
	public String getDefaultName() 
	{
		return MODULE_NAME;
	}

}
