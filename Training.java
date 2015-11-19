package training;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Training {

	
	public HashMap<String, Integer> train(File file) {
		HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>(
				400000);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "utf-8"));
			String inputLine = br.readLine();
			while (inputLine != null) {
				String[] inputLineArr = inputLine.trim().split("[ ]+");
				for (String word : inputLineArr) {
					if (wordFrequency.containsKey(word)) {
						wordFrequency.put(word, wordFrequency.get(word) + 1);
					} else {
						wordFrequency.put(word, 1);
					}
				}
				inputLine = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return wordFrequency;
	}

	public void trainAndSerialize(File file, File seriFile) {
		HashMap<String, Integer> wordFrequency = train(file);
		try {
			if (!seriFile.exists()) {
				seriFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(seriFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(wordFrequency);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getWordFrequency(File file) {
		HashMap<String, Integer> wordFrequency = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			wordFrequency = (HashMap<String, Integer>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wordFrequency;
	}
}
