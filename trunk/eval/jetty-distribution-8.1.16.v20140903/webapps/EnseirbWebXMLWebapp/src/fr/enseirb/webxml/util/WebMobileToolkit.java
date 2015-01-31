package fr.enseirb.webxml.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class WebMobileToolkit {
	/*
	 * Code inspir� de http://notnotmobile.appspot.com/
	 */

	// TODO : gerer l'iPad
	private static final Pattern RE_MOBILE = Pattern.compile("(iphone|ipod|blackberry|android|palm|windows\\s+ce)",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern RE_DESKTOP = Pattern.compile("(windows|linux|os\\s+[x9]|solaris|bsd)",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern RE_BOT = Pattern.compile("(spider|crawl|slurp|bot)", Pattern.CASE_INSENSITIVE);
	private static final Pattern RE_TEST = Pattern.compile("(Apache)", Pattern.CASE_INSENSITIVE);

	private static final String HTTP_X_OPERAMINI_PHONE_UA = "X-Operamini-Phone-UA";
	private static final String HTTP_X_SKYFIRE_PHONE = "X-Skyfire-Phone";
	private static final String HTTP_USER_AGENT = "User-Agent";

	/**
	 * Permet de savoir si une requ�te provient d'un navigateur install� sur un t�l�phone mobile ou un ordinateur
	 * 
	 * @param request
	 *            la requ�te envoy�e par le navigateur
	 * @return true si le navigateur est celui d'un ordinateur et faux si c'est un SmartPhone
	 */
	public static boolean isDesktopBrowser(HttpServletRequest request) {
		String userAgentString = getUserAgent(request);
		if (userAgentString == null || userAgentString.isEmpty()) {
			return true;
		}
		return ((!(RE_MOBILE.matcher(userAgentString).find())) && (RE_DESKTOP.matcher(userAgentString).find()))
				|| RE_BOT.matcher(userAgentString).find() || RE_TEST.matcher(userAgentString).find();
	}

	/**
	 * Permet de conna�tre le UserAgent � partir d'une requ�te envoy�e par un navigateur
	 * 
	 * @param request
	 *            la requ�te � analyser
	 * @return la cha�ne correspondant au User-Agent contenu dans l'ent�te de requ�te
	 */
	private static String getUserAgent(HttpServletRequest request) {
		// Some mobile browsers put the User-Agent in a HTTP-X header
		String userAgentString = request.getHeader(HTTP_X_OPERAMINI_PHONE_UA);
		if (userAgentString == null || userAgentString.isEmpty()) {
			userAgentString = request.getHeader(HTTP_X_SKYFIRE_PHONE);
		}
		if (userAgentString == null || userAgentString.isEmpty()) {
			userAgentString = request.getHeader(HTTP_USER_AGENT);
		}
		return userAgentString;
	}

}
