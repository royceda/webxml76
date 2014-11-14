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
	 * Permet de renvoyer le contenu d'un fichier (textuel) sous forme de cha�ne de caract�res
	 * 
	 * @param path
	 *            le chemin vers le fichier � lire, sous la forme d'un chemin absolu (commen�ant par "/") � partir du
	 *            r�pertoire WebContent
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
	 *            le chemin vers le fichier � lire, sous la forme d'un chemin absolu (commen�ant par "/") � partir du
	 *            r�pertoire WebContent
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
	 * Permet d'acc�der aux param�tres contenus dans une URL (sous la forme
	 * http://www.monsite.fr?<cle_param1>=<valeur_param1>&<cle_param2>=<valeur_param2>)
	 * 
	 * @param request
	 *            la requ�te qu'il faut analyser
	 * @return un objet Properties (cf. Javadoc pour les fonctions permettant de l'utiliser) contenant les couples
	 *         cl�s/valeurs
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
	 * Permet de r�cup�rer les donn�es envoy�es en POST dans la requ�te, ces donn�es sont interpr�t�es comme une cha�ne
	 * de caract�res
	 * 
	 * @param request
	 *            la requ�te qu'il faut analyser
	 * @return la cha�ne de caract�res correspondant aux donn�es post�es
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
	 * Permet de r�cup�rer les donn�es envoy�es en POST dans la requ�te, ces donn�es peuvent ne pas �tre une cha�ne de
	 * caract�res (une image par exemple)
	 * 
	 * @param request
	 *            la requ�te qu'il faut analyser
	 * @return les donn�es brutes, sous la forme d'un tableau de byte
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
	 * Permet d'�crire une r�ponse � une requ�te, si cette r�ponses est re�ue par un navigateur, elle sera affich�e et
	 * interpr�t�e comme du HTML
	 * 
	 * @param response
	 *            l'objet r�ponse qu'il faut utiliser pour faire transiter la cha�ne � renvoyer
	 * @param text
	 *            la cha�ne de caract�res � renvoyer
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
