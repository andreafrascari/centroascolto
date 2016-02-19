package eu.antoniano.centroascolto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.core.modules.JSONMethod;
import eu.anastasis.serena.application.tokens.SingleUseTokenService;
import eu.anastasis.serena.exceptions.JSONException;

public class GetTokenJsonMethod extends JSONMethod {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GetTokenJsonMethod.class);

	private static final String METHOD_NAME = "getToken";

	public GetTokenJsonMethod(DefaultModule parentModule,
			String[] defaultParameters) {
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName() {
		return METHOD_NAME;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		try {
			String action = request.getParameter("action");
			String theNewToken = null;
			if (action != null && !action.isEmpty()) {
				theNewToken = SingleUseTokenService.addTokenToQueryString(
						request.getSession(), action);
			} else {
				theNewToken = SingleUseTokenService.getToken(request
						.getSession());
			}
			return new Gson().toJson(theNewToken);
		} catch (Exception e) {
			throw new JSONException("Errore nel reperimento dei token ", e);
		}
	}
}
