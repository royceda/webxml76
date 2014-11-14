package fr.enseirb.webxml.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xerces.dom.DOMInputImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import fr.enseirb.webxml.servlet.listener.WebAppInitializer;

public final class XMLToolkit {
	private static final Logger LOGGER = Logger.getLogger(XMLToolkit.class.getName());

	public static final String CHARSET_STRING = Charset.forName("utf-8").displayName();

	public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"" + CHARSET_STRING + "\"?>";

	/**
	 * Permet de cr�er un message standardis� en r�ponse � une requ�te POST
	 * 
	 * @param message
	 *            le corps du message � renvoyer
	 * @param resultCode
	 *            le fait que la requ�te s'est bien pass�e ou non
	 * @return une cha�ne de caract�res sous la forme <result><code>#resultCode#</code>
	 *         <message>#message#</message></result>
	 */
	public static String createPostResult(String message, boolean resultCode) {
		StringBuilder buffer = new StringBuilder(XMLToolkit.XML_HEADER);
		buffer.append("<result>");

		buffer.append("<code>");
		buffer.append(Boolean.toString(resultCode));
		buffer.append("</code>");

		buffer.append("<message>");
		buffer.append(message);
		buffer.append("</message>");

		buffer.append("</result>");

		return buffer.toString();
	}

	/**
	 * Permet d'effectuer une transformation XSL sur un flux XML et de renvoyer la cha�ne obtenue
	 * 
	 * @param xmlStream
	 *            le flux XML � transformer
	 * @param xslPath
	 *            le chemin vers le fichier XSL � utiliser pour effectuer la transformation, sous la forme d'un chemin
	 *            absolu (commen�ant par "/") � partir du r�pertoire WebContent
	 * @return la cha�ne de caract�res obtenues apr�s transformation
	 * @throws IOException
	 */
	public static String transformXML(String xmlStream, String xslPath) throws IOException {
		return transformXML(xmlStream, xslPath, null);
	}

	/**
	 * Permet d'effectuer une transformation XSL sur un flux XML et de renvoyer la cha�ne obtenue
	 * 
	 * @param xmlStream
	 *            le flux XML � transformer
	 * @param xslPath
	 *            le chemin vers le fichier XSL � utiliser pour effectuer la transformation, sous la forme d'un chemin
	 *            absolu (commen�ant par "/") � partir du r�pertoire WebContent
	 * @param params
	 *            des param�tres de la feuille XSL � passer au moteur de transformation
	 * @return
	 * @throws IOException
	 */
	public static String transformXML(String xmlStream, String xslPath, Map<String, String> params) throws IOException {
		LOGGER.log(Level.INFO, "Transforming XMLStream against xsl: " + xslPath + ", with param: "
				+ (params == null ? "null" : params.toString()) + ", stream is: " + xmlStream);

		TransformerFactory tfactory = TransformerFactory.newInstance();
		InputStream xslIS = new BufferedInputStream(new FileInputStream(WebAppInitializer.ROOT + xslPath));
		StreamSource xslSource = new StreamSource(xslIS);

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		Transformer transformer;
		try {
			transformer = tfactory.newTransformer(xslSource);
			StreamSource xmlSource = new StreamSource(new StringReader(xmlStream));

			if (params != null) {
				for (Iterator<String> paramNames = params.keySet().iterator(); paramNames.hasNext();) {
					String name = paramNames.next();
					String value = params.get(name);
					transformer.setParameter(name, value);
				}
			}

			transformer.transform(xmlSource, new StreamResult(outStream));
		} catch (TransformerConfigurationException e) {
			String errorMsg = "An error occured while transforming XML with XSLT";
			LOGGER.log(Level.SEVERE, errorMsg, e);
			outStream.write(errorMsg.getBytes());

		} catch (TransformerException e) {
			String errorMsg = "An error occured while transforming XML with XSLT";
			LOGGER.log(Level.SEVERE, errorMsg, e);
			outStream.write(errorMsg.getBytes());
		}
		outStream.flush();

		return outStream.toString();
	}

	/**
	 * Permet de r�cup�rer la liste des valeurs contenues dans un flux XML correspondant au chemin XPath indiqu�
	 * 
	 * @param xmlStream
	 *            le flux XML � parser
	 * @param xPath
	 *            le chemin XPath � utiliser pour r�cup�rer les valeurs dans le flux XML
	 * @return la liste des valeurs trouv�es
	 */
	public static List<String> getXPathValues(String xmlStream, String xPath) {
		List<String> xPathResult = new ArrayList<String>();
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(xmlStream.getBytes("utf-8")));

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			XPathExpression expr = xpath.compile(xPath);

			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				xPathResult.add(nodes.item(i).getNodeValue());
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			xPathResult = null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			xPathResult = null;
		} catch (SAXException e) {
			e.printStackTrace();
			xPathResult = null;
		} catch (IOException e) {
			e.printStackTrace();
			xPathResult = null;
		}
		return xPathResult;
	}

	/**
	 * Permet de r�cup�rer la valeur contenue dans un flux XML correspondant au chemin XPath indiqu�, il est fait
	 * hypoth�se que le chemin XPath ne correspond qu'� une seule valeur
	 * 
	 * @param xmlStream
	 *            le flux XML � parser
	 * @param xPath
	 *            le chemin XPath � utiliser pour r�cup�rer la valeur dans le flux XML
	 * @return la valeur trouv�e ou null si aucune valeur n'est trouv�e et que plus d'une valeur est trouv�e
	 */
	public static String getXPathValue(String xmlStream, String xPath) {
		List<String> xPathResult = getXPathValues(xmlStream, xPath);

		if (xPathResult == null || xPathResult.size() != 1) {
			return null;

		} else {
			return xPathResult.get(0);
		}
	}

	/**
	 * Permet de v�rifier qu'un flux XML est conforme vis-�-vis d'un sch�ma XSD
	 * 
	 * @param xmlStream
	 *            le flux XML � v�rifier
	 * @param xsdPath
	 *            le chemin vers le fichier XSD � utiliser pour effectuer la validation, sous la forme d'un chemin
	 *            absolu (commen�ant par "/") � partir du r�pertoire WebContent
	 * @return true si le flux XML est valide par rapport au sch�ma fourni
	 */
	public static boolean isXMLValid(String xmlStream, String xsdPath) {
		LOGGER.log(Level.INFO, "Validating XMLStream against xsd: " + xsdPath + ", stream is: " + xmlStream);

		boolean result;
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			schemaFactory.setErrorHandler(new ErrorHandler() {

				public void warning(SAXParseException exception) throws SAXException {
					// on ne fait rien
				}

				public void fatalError(SAXParseException exception) throws SAXException {
					throw exception;
				}

				public void error(SAXParseException exception) throws SAXException {
					throw exception;
				}
			});

			InputStream xsdIS = new BufferedInputStream(new FileInputStream(WebAppInitializer.ROOT + xsdPath));
			StreamSource xsdSource = new StreamSource(xsdIS);
			schemaFactory.setResourceResolver(new LSResourceResolver() {

				public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId,
						String baseURI) {
					try {
						return new DOMInputImpl(publicId, systemId, systemId, new FileInputStream(
								WebAppInitializer.ROOT + "/resources/xsd/" + systemId), "utf-8");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
			});
			Schema schemaXSD = schemaFactory.newSchema(xsdSource);

			Validator validator = schemaXSD.newValidator();

			Document document = parseDocument(xmlStream);
			if (document != null) {
				validator.validate(new DOMSource(document));
				result = true;

			} else {
				result = false;
			}
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, "Error during validation", e);
			result = false;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error during validation", e);
			result = false;
		}
		return result;
	}

	/**
	 * Permet de r�cup�rer l'objet Document correspond � un flux XML
	 * 
	 * @param xmlStream
	 *            le flux XML, sous la forme d'une cha�ne de caract�res, � transformer
	 * @return le flux XML sous la forme d'un Document
	 */
	public static Document parseDocument(String xmlStream) {
		LOGGER.log(Level.INFO, "Parsing XMLStream: " + xmlStream);

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);

		Document doc;
		try {
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			doc = builder.parse(new ByteArrayInputStream(xmlStream.getBytes("utf-8")));
		} catch (ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, "Error during parsing document", e);
			doc = null;
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, "Error during parsing document", e);
			doc = null;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error during parsing document", e);
			doc = null;
		}
		return doc;
	}
}
