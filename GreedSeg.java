package wordsegm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import statistic.Statistic;

public class GreedSeg {

	HashMap<String, Integer> wordFrequency;
	int totalfreq = 15948617;
	int maxSize = 3;

	public GreedSeg(HashMap<String, Integer> wordFrequency, int maxSize) {
		this.wordFrequency = wordFrequency;
		this.maxSize = maxSize;
	}

	public GreedSeg(File file, int i) {
		Statistic demo = new Statistic();
		this.wordFrequency = demo.statWordFreq(file);
		this.maxSize = i;
	}

	public ArrayList<String> segment(String str) {
		ArrayList<String> words = new ArrayList<String>();
		Preprocess pretreatment = new Preprocess();
		ArrayList<String> preprocess = pretreatment.pretreat(str, maxSize);

		int segSize = preprocess.size();
		int localMaxSize = maxSize;
		
		for (int index = 0; index < segSize;) {
			int freq = 0, ind = index;
			double prob = 0.0, nprob = 0.0;
	
			if (wordFrequency.containsKey(preprocess.get(index))) {
				freq = wordFrequency.get(preprocess.get(index));
				prob = Math.log((double) totalfreq / freq);
			}
			int count = 1;
			for (; count < localMaxSize; count++) {
				if (wordFrequency.containsKey(preprocess.get(index + count))) {
					double nextfreq = wordFrequency.get(preprocess.get(index
							+ count))
							* Math.pow(10, count + 1);
					nprob = Math.log((double) totalfreq / nextfreq);
					if (nprob < prob) {
						freq = wordFrequency.get(preprocess.get(index + count));
						ind = index + count;
					}
				}
			}
			words.add(preprocess.get(ind));
			count = ind - index + 1;
			if (localMaxSize == maxSize) {
				int i = 0;
				for (; i < count; i++) {
					index += maxSize;
					if (index == segSize - (maxSize * (maxSize - 1)) / 2) {
						localMaxSize--;
						i++;
						break;
					}
				}
				for (; i < count; i++) {
					index += localMaxSize;
					localMaxSize--;
				}
			} else {
				for (int i = 0; i < count; i++) {
					index += localMaxSize;
					localMaxSize--;
				}
			}

		}
		return words;
	}
}
