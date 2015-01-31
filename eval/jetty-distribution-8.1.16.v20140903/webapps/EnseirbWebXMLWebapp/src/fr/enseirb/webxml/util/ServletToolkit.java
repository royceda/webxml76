package fr.enseirb.webxml.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.enseirb.webxml.servlet.listener.WebAppInitializer;

public final class ServletToolkit {
	private static final Logger LOGGER = Logger.getLogger(ServletToolkit.class.getName());

	/**
	 * Permet de renvoyer le contenu d'un fichier (textuel) sous forme de chaîne de caractères
	 * 
	 * @param path
	 *            le chemin vers le fichier à lire, sous la forme d'un chemin absolu (commençant par "/") à partir du
	 *            répertoire WebContent
	 * @return le contenu du fichier
	 * @throws IOException
	 */
	public static String readFile(String path) throws IOException {
		String fullPath = WebAppInitializer.ROOT + path;
		LOGGER.log(Level.INFO, "Reading file: " + fullPath);

		InputStream stream = new FileInputStream(fullPath);

		StringBuilder builder = new StringBuilder();
		int byt = -1;
		while ((byt = stream.read()) != -1) {
			builder.append((char) byt);
		}
		String stringifiedStream = builder.toString();
		stream.close();

		return new String(stringifiedStream.getBytes(), "UTF-8");
	}

	/**
	 * Permet de renvoyer le contenu d'un fichier (non textuel, comme une image par exemple) sous forme d'un tableau de
	 * byte
	 * 
	 * @param path
	 *            le chemin vers le fichier à lire, sous la forme d'un chemin absolu (commençant par "/") à partir du
	 *            répertoire WebContent
	 * @return le contenu du fichier
	 * @throws IOException
	 */
	public static byte[] readRawFile(String path) throws IOException {
		String fullPath = WebAppInitializer.ROOT + path;
		LOGGER.log(Level.INFO, "Reading raw file: " + fullPath);

		InputStream stream = new FileInputStream(fullPath);

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		int byt = -1;
		while ((byt = stream.read()) != -1) {
			outStream.write(byt);
		}
		stream.close();

		LOGGER.log(Level.INFO, "File read");

		return outStream.toByteArray();
	}

	/**
	 * Permet d'accéder aux paramétres contenus dans une URL (sous la forme
	 * http://www.monsite.fr?<cle_param1>=<valeur_param1>&<cle_param2>=<valeur_param2>)
	 * 
	 * @param request
	 *            la requête qu'il faut analyser
	 * @return un objet Properties (cf. Javadoc pour les fonctions permettant de l'utiliser) contenant les couples
	 *         clés/valeurs
	 */
	public static Properties parseURLParams(HttpServletRequest request) {
		String query = request.getQueryString();
		LOGGER.log(Level.INFO, "Parsing URL params for query: " + query);

		Properties props = new Properties();
		if (query != null) {
			query = query.replace("&", "\n\r");
			try {
				props.load(new StringReader(query));
			} catch (IOException e) {
				return null;
			}
		}
		for (Iterator<Object> keyIte = props.keySet().iterator(); keyIte.hasNext();) {
			String key = (String) keyIte.next();
			try {
				props.put(key, URLDecoder.decode((String) props.get(key), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				LOGGER.log(Level.INFO, "Error while reading parameter with key: " + key, e);
			}
		}

		LOGGER.log(Level.INFO, "returning: " + props.toString());
		return props;
	}

	/**
	 * Permet de récupérer les données envoyées en POST dans la requête, ces données sont interprétées comme une chaîne
	 * de caractères
	 * 
	 * @param request
	 *            la requête qu'il faut analyser
	 * @return la chaîne de caractères correspondant aux données postées
	 * @throws IOException
	 */
	public static String getPostData(HttpServletRequest request) throws IOException {
		LOGGER.log(Level.INFO, "Reading post data");

		InputStream stream = request.getInputStream();

		StringBuilder builder = new StringBuilder();
		int byt = -1;
		while ((byt = stream.read()) != -1) {
			builder.append((char) byt);
		}
		String stringifiedStream = builder.toString();
		stream.close();

		stringifiedStream = new String(stringifiedStream.getBytes(), "UTF-8");

		LOGGER.log(Level.INFO, "Post Data are: " + stringifiedStream);

		return stringifiedStream;
	}

	/**
	 * Permet de récupérer les données envoyées en POST dans la requête, ces données peuvent ne pas être une chaîne de
	 * caractères (une image par exemple)
	 * 
	 * @param request
	 *            la requête qu'il faut analyser
	 * @return les données brutes, sous la forme d'un tableau de byte
	 * @throws IOException
	 */
	public static byte[] getRawPostData(HttpServletRequest request) throws IOException {
		LOGGER.log(Level.INFO, "Reading raw post data");

		InputStream stream = request.getInputStream();

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		int byt = -1;
		while ((byt = stream.read()) != -1) {
			outStream.write(byt);
		}
		stream.close();

		LOGGER.log(Level.INFO, "Raw post Data read");

		return outStream.toByteArray();
	}

	/**
	 * Permet d'écrire une réponse à une requête, si cette réponses est reçue par un navigateur, elle sera affichée et
	 * interprétée comme du HTML
	 * 
	 * @param response
	 *            l'objet réponse qu'il faut utiliser pour faire transiter la chaîne à renvoyer
	 * @param text
	 *            la chaîne de caractères à renvoyer
	 * @throws IOException
	 */
	public static void writeResponse(HttpServletResponse response, String text) throws IOException {
		try {
			if (text == null) {
				text = "";
			}
			response.getOutputStream().write(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.INFO, "Problem while encoding response", e);
		}
	}
}
