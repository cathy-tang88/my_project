package tjx.trs.frequency;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tjx.trs.util.CollectionUtil;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;

public class ClassFre {
	public static void main(String[] args) throws Exception{
		
		
		HashMap<String, Integer[]> CATEGORYMAP=new HashMap<String,Integer[]>();
			BufferedReader reader = IOUtil.getReader("svm/training/ml.txt", "UTF-8");
			String temp = null;
			HashMap<String, Integer> hm = new HashMap<String, Integer>();
			Forest forest = Library.makeForest("data/csdn_class.txt");
			GetWord getWord = null;
		
			while ((temp = reader.readLine()) != null) {
				
				getWord = new GetWord(forest, temp);
				
				while ((temp = getWord.getFrontWords()) != null) {
					if (hm.containsKey(temp)) {
						hm.put(temp, hm.get(temp) + 1);
					} else {
						hm.put(temp, 1);
					}
				}

			}
			
			BufferedReader reader1 = IOUtil.getReader("svm/training/cloud.txt", "UTF-8");
			String temp1 = null;
			HashMap<String, Integer> hm1 = new HashMap<String, Integer>();
			
		
			while ((temp1 = reader.readLine()) != null) {
				
				getWord = new GetWord(forest, temp1);
				
				while ((temp1= getWord.getFrontWords()) != null) {
					if (hm1.containsKey(temp1)) {
						hm1.put(temp1, hm1.get(temp1) + 1);
					} else {
						hm1.put(temp1, 1);
					}
				}

			}
			
			BufferedReader reader2 = IOUtil.getReader("data/csdn_class.txt", "UTF-8") ;
			while((temp=reader2.readLine())!=null){
				String[] split = temp.toLowerCase().split("\t");
				if(!hm.containsKey(split[0])){
					hm.put(split[0], 1) ;
				}
			}
			
		
			StringBuilder sb = new StringBuilder();
			List<Entry<String, Integer>> sortMapByValue = CollectionUtil.sortMapByValue(hm, 1) ;
			
			for (Entry<String, Integer> entry : sortMapByValue) {
				sb.append(entry.getKey()+"\t"+entry.getValue()) ;
				String word=entry.getKey();
				Integer freq=entry.getValue();
				if(!CATEGORYMAP.containsKey(word))
				{
					Integer[] tempFreq=new Integer[9];
					CATEGORYMAP.put(word, tempFreq);
				}
				CATEGORYMAP.get(word)[4]=freq;
				
				sb.append("\n") ;
			}
			//IOUtil.Writer("data/word.txt", "UTF-8", sb.toString()) ;

			Iterator it=CATEGORYMAP.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry<String, Integer[]> entry=(Map.Entry<String, Integer[]>)it.next();
				System.out.println(entry.getKey());
				System.out.print("\t");
				for (Integer i : entry.getValue()) {
					System.out.print(i+" ");
				}
				System.out.println();
			}
			
		}
}
