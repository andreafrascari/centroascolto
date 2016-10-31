package eu.antoniano.centroascolto;

import eu.anastasis.serena.application.core.modules.DefaultModule;


public class CentroAscoltoModule extends DefaultModule {
public final static String MODULE_NAME = "centroascolto";

	protected void setUpMethods()
	{
		addMethod(new CreaIstanzaClassificazioneIcfMethod(this, getDefaultParameters()));
		addMethod(new RegistraClassificazioneIcfMethod(this, getDefaultParameters()));
		addMethod(new ReportTessereAttiveMethod(this, getDefaultParameters()));
		addMethod(new ReportTessereAttiveMensaSeraleMethod(this, getDefaultParameters()));
		addMethod(new ReportRilascioTessereMethod(this, getDefaultParameters()));
		addMethod(new AllStatsMethod(this, getDefaultParameters()));
		addMethod(new EtaStatsMethod(this, getDefaultParameters()));
		addMethod(new IcfChartGenMethod(this, getDefaultParameters()));
		
		addFunctionToPostparse(new NumeroTessereAttiveFunction());
	}
	
	@Override
	public String getDefaultName() 
	{
		return MODULE_NAME;
	}

}
