package eu.antoniano.centroascolto;

import eu.anastasis.serena.application.core.modules.DefaultModule;


public class CentroAscoltoModule extends DefaultModule {
public final static String MODULE_NAME = "centroascolto";

	protected void setUpMethods()
	{
		addMethod(new CreaIstanzaClassificazioneIcfMethod(this, getDefaultParameters()));
		addMethod(new RegistraClassificazioneIcfMethod(this, getDefaultParameters()));
		addMethod(new ReportTessereAttiveMethod(this, getDefaultParameters()));
		addMethod(new ReportRilascioTessereMethod(this, getDefaultParameters()));
	}
	
	@Override
	public String getDefaultName() 
	{
		return MODULE_NAME;
	}

}
