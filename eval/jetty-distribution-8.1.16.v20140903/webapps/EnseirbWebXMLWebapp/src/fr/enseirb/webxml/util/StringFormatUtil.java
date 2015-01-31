package fr.enseirb.webxml.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StringFormatUtil {

	private static final long ONE_DAY_IN_MS = 1000 * 60 * 60 * 24;

	/**
	 * Permet d'obtenir un objet Date correspond � l'instant o� la m�thode est invoqu�e (seul la date du jour est
	 * importante) en rajoutant un d�lai
	 * 
	 * @param delay
	 *            le d�lai � ajouter, exprim� en nombre de jours
	 * @return l'objet Date correspondant � la date du jour d�cal� dans le futur du nombre de jours indiqu�s en
	 *         param�tre
	 */
	public static Date getNowDateWithDelay(String delay) {
		long delayedDate = System.currentTimeMillis() + StringFormatUtil.ONE_DAY_IN_MS * Integer.parseInt(delay);
		return new Date(delayedDate);
	}

	/**
	 * Permet de renvoyer une cha�ne de caract�res concat�nant les cha�nes de caract�res en param�tres, en les s�parant
	 * avec un ";" et en les triant alphab�tiquement au pr�alable
	 * 
	 * @param stringList
	 *            la liste des cha�nes de caract�res � trier puis concat�ner
	 * @return
	 */
	public static String sortAndFormatAsCSV(List<String> stringList) {
		if (stringList == null) {
			return "";
		}
		String[] stringArray = stringList.toArray(new String[0]);
		Arrays.sort(stringArray);

		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < stringArray.length; i++) {
			if (i > 0) {
				buffer.append(";");
			}
			buffer.append(stringArray[i]);
		}

		return buffer.toString();
	}

}
