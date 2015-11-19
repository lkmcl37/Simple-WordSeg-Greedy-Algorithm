package wordsegm;

import java.util.ArrayList;

public class Preprocess {


	public ArrayList<String> pretreat(String str, int maxSize) {
		ArrayList<String> prepare = new ArrayList<String>();
		str = str.replaceAll("[\\pP¡®¡¯¡°¡±]", ""); 
		char[] strChars = str.toCharArray();
		for(int index = 0; index < strChars.length; index++) {
			String temp = "" + strChars[index];
			prepare.add(temp);
			for(int count = 1; count < maxSize; count++) {
				if(index + count < strChars.length) {
					temp = temp + strChars[index + count];
					prepare.add(temp);
				}
			}
		}
		return prepare;
	}
}
