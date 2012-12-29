package tjx.trs.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;
import tjx.trs.util.CollectionUtil;

public class QueryDemo {
	public static void main(String[] args) throws Exception {
		
		
		
		FileOutputStream fos = new FileOutputStream("D:\\语料\\SogouQ\\filter.txt");
		File[] files = new File("D:\\语料\\SogouQ\\all").listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].canRead() || !files[i].getName().endsWith("sogou")) {
				continue;
			}
			BufferedReader reader = IOUtil.getReader(new FileInputStream(files[i]), "GBK");
			String temp = null;

			while ((temp = reader.readLine()) != null) {
				String[] split = temp.toLowerCase().split("\t");
				if (filter(split[2])) {
					fos.write(temp.getBytes());
					fos.write("\n".getBytes());
				}
			}
		}
		fos.flush();
		fos.close();
	}
	
	
	public static  void clearnWord()throws Exception{
		filter("aa");
		//	BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\filter.txt", "UTF-8");
			BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\all.txt", "UTF-8");
			String temp = null;
			HashMap<String, Integer> hMap = new HashMap<String, Integer>();
			while ((temp = reader.readLine()) != null) {
				String[] split = temp.split("\t");
				temp = split[2];

				GetWord gWord = new GetWord(forest, temp);
				while ((temp = gWord.getFrontWords()) != null) {
					if (hMap.containsKey(temp)) {
						hMap.put(temp, hMap.get(temp) + 1);
					} else {
						hMap.put(temp, 1);
					}
				}
			}

			System.out.println(hMap.size());
			List<Entry<String, Integer>> sortMapByValue = CollectionUtil.sortMapByValue(hMap, 1);
			HashSet<String> hs = new HashSet<String>();
			for (int i = 0; i < 300; i++) {
				System.out.println(sortMapByValue.get(i));
				//hs.add(sortMapByValue.get(i).getKey()) ;
			}
			
			
			
			reader = IOUtil.getReader("data\\csdn_class.txt", "UTF-8");
			
			 FileOutputStream fos = new FileOutputStream("data\\csdn_class.new.txt");
			while((temp=reader.readLine())!=null){
				if(hs.contains(temp.split("\t")[0])){
					continue ;
				}else{
					fos.write(temp.getBytes()) ;
					fos.write("\n".getBytes()) ;
				}
			}
			fos.flush() ;
			fos.close() ; 
	}

	private static Forest forest = null;

	public static boolean filter(String query) throws Exception {
		if (forest == null) {
			forest = Library.makeForest("data/csdn_class.txt");
			//forest = Library.makeForest(IOUtil.getReader("data/计算机与自动化.txt", "UTF-8")) ;
		}
		GetWord gw = new GetWord(forest, query);
		int size = 0;
		String temp = null ;
		while ((temp=gw.getFrontWords()) != null) {
			
			size+=temp.length();
		}
		if (size > 5) {
			return true;
		}
		return false;
	}
	
}
