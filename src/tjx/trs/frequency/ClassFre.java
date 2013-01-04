package tjx.trs.frequency;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;
import tjx.trs.util.StaticValue;

public class ClassFre {
	public static void main(String[] args) throws Exception {

		HashMap<String, Integer[]> dicMap = new HashMap<String, Integer[]>();
		String temp = null;
		Forest forest = Library.makeForest("data/csdn_class.txt");
		GetWord getWord = null;

		Set<Entry<String, Integer>> entrySet = StaticValue.CATEGORYMAP.entrySet();
		int categorySize = StaticValue.CATEGORYMAP.size();
		Integer[] ints = null;
		for (Entry<String, Integer> entry : entrySet) {
			BufferedReader reader = IOUtil.getReader("data/training/" + entry.getKey() + ".txt", "UTF-8");
			while ((temp = reader.readLine()) != null) {
				getWord = new GetWord(forest, temp);
				while ((temp = getWord.getFrontWords()) != null) {
					if (dicMap.containsKey(temp)) {
						ints = dicMap.get(temp);
						int index = entry.getValue() - 1;
						if (ints[index] == null) {
							ints[index] = 1;
						} else {
							ints[index]++;
						}
					} else {
						dicMap.put(temp, new Integer[categorySize]);
						dicMap.get(temp)[entry.getValue() - 1] = 1;
					}
				}

			}
		}

		StringBuilder sb = new StringBuilder();

		Set<Entry<String, Integer[]>> sortMapByValue = dicMap.entrySet();

		for (Entry<String, Integer[]> entry : sortMapByValue) {
			sb.append(entry.getKey());
			Integer[] value = entry.getValue();
			for (Integer integer : value) {
				if (integer == null) {
					integer = 0;
				}
				sb.append("\t" + integer);
			}
			sb.append("\n");
		}
		IOUtil.Writer("data/word.txt", "UTF-8", sb.toString());

	}
}
