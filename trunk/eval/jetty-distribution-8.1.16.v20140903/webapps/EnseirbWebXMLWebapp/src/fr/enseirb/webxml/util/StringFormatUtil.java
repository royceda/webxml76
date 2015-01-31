package fr.enseirb.webxml.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StringFormatUtil {

	private static final long ONE_DAY_IN_MS = 1000 * 60 * 60 * 24;

	/**
	 * Permet d'obtenir un objet Date correspond à l'instant où la méthode est invoquée (seul la date du jour est
	 * importante) en rajoutant un délai
	 * 
	 * @param delay
	 *            le délai à ajouter, exprimé en nombre de jours
	 * @return l'objet Date correspondant à la date du jour décalé dans le futur du nombre de jours indiqués en
	 *         paramétre
	 */
	public static Date getNowDateWithDelay(String delay) {
		long delayedDate = System.currentTimeMillis() + StringFormatUtil.ONE_DAY_IN_MS * Integer.parseInt(delay);
		return new Date(delayedDate);
	}

	/**
	 * Permet de renvoyer une chaîne de caractères concaténant les chaînes de caractères en paramètres, en les séparant
	 * avec un ";" et en les triant alphabétiquement au préalable
	 * 
	 * @param stringList
	 *            la liste des chaînes de caractères à trier puis concaténer
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
