package tjx.trs.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import tjx.trs.util.CollectionUtil;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;

public class QueryDemo {
	public static void main(String[] args) throws Exception {

		// FileOutputStream fos = new FileOutputStream("D:\\语料\\SogouQ\\filter.txt");
		 FileOutputStream fos = new FileOutputStream("D:\\语料\\SogouQ\\filter1.txt");
	/*	File[] files = new File("D:\\语料\\SogouQ\\all").listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].canRead() || !files[i].getName().endsWith("sogou")) {
				continue;
			}
			BufferedReader reader = IOUtil.getReader(new FileInputStream(files[i]), "GBK");*/
			BufferedReader reader = IOUtil.getReader(new FileInputStream("D:\\训练集\\日志.txt"), "GBK");
			String temp = null;
      
			while ((temp = reader.readLine()) != null) {
				
				String[] split = temp.toLowerCase().split("\t");
				
				if (split.length>3&&filter(split[2])) {
					System.out.println();
					fos.write(temp.getBytes());
					fos.write("\n".getBytes());
//					System.out.println(temp);
				}
			}
	//	}
		fos.flush();
		 fos.close();
	}

	public static void clearnWord() throws Exception {
		filter("aa");
		BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\filter.txt", "UTF-8");
		// BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\all.txt",
		// "UTF-8");
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
			// hs.add(sortMapByValue.get(i).getKey()) ;
		}

	}

	private static Forest forest = null;



	public static boolean filter(String query) throws Exception {
		if (forest == null) {
			forest = Library.makeForest("data/library.dic");
			// forest = Library.makeForest(IOUtil.getReader("data/计算机与自动化.txt",
			// "UTF-8")) ;
		}
		GetWord gw = new GetWord(forest, query);
		double pe1  = 1;
		double pe2  = 1;
		int size = 0 ;
		int tempAllFreq  = 0 ;
		String temp = null;

		while ((temp = gw.getFrontWords()) != null) {
			pe1 = pe1*Double.parseDouble(gw.getParam(3)) ;
			pe2 = pe1*(1-Double.parseDouble(gw.getParam(3))) ;
			tempAllFreq = Integer.parseInt(gw.getParam(2)) ;
			size ++ ;
		}
		//如果只分出一个词语。并且这个词的other频率 大于1000 那么这个串串我们夜抛弃掉  这样 婚姻匹配	0.946122608833829 这种词就不会出现了 
		if(size==1&&tempAllFreq>1000){
			return false ;
		}
		double p = pe1/(pe1+pe2) ; 
		
		if(p>0.87){
			return true;
		}
		return false;
	}

}
