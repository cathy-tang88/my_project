package tjx.trs.run;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;
import tjx.trs.util.CollectionUtil;

public class Selectuser {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\filter.txt", "UTF-8");
		String temp = null;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		Forest forest = Library.makeForest("data/csdn_class.txt");
		GetWord getWord = null;
		int count = 0;
		while ((temp = reader.readLine()) != null) {
			String[] split = temp.split("\t");
			count++;
			getWord = new GetWord(forest, split[2]);
			while ((temp = getWord.getFrontWords()) != null) {
				if (hm.containsKey(temp)) {
					hm.put(temp, hm.get(temp) + 1);
				} else {
					hm.put(temp, 2);
				}
			}

		}
		
		//补录为0的东西
		BufferedReader reader2 = IOUtil.getReader("data/csdn_class.txt", "UTF-8") ;
		while((temp=reader2.readLine())!=null){
			String[] split = temp.split("\t");
			if(!hm.containsKey(split[0])){
				hm.put(split[0], 1) ;
			}
		}
		
		System.out.println("总数" + count);
		StringBuilder sb = new StringBuilder();
		List<Entry<String, Integer>> sortMapByValue = CollectionUtil.sortMapByValue(hm, 1) ;
		for (Entry<String, Integer> entry : sortMapByValue) {
			sb.append(entry.getKey()+"\t"+entry.getValue()) ;
			sb.append("\n") ;
		}
		IOUtil.Writer("data/word_freq.txt", "UTF-8", sb.toString()) ;
	}
	
}
