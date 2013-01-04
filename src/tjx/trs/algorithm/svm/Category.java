package tjx.trs.algorithm.svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;

import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;
import tjx.trs.util.StaticValue;

/**
 * 传入一个文章.进行类别判定
 * 
 * @author ansj
 * 
 */
public class Category{

	public static int findCategoryId(String title, String content) {
		content = content + title;
		GetWord gw = new GetWord(StaticValue.getForestId(), content) ;
		String temp = null ;
		Integer id = null;
		TreeMap<Integer,Integer> tm = new TreeMap<Integer, Integer>() ;
		while ((temp = gw.getFrontWords()) != null) {
			id = Integer.parseInt(gw.getParam(0));
			if (tm.containsKey(id)) {
				tm.put(id, tm.get(id) + 1);
			} else {
				tm.put(id, 1);
			}
		}
		
		return (int) MyLibSvm.findCategory(tm) ;
	}
	
	public static String findCategoryName(String title , String content){
		int categoryId = findCategoryId(title, content) ;
		return StaticValue.CATEGORYIDMAP.get(categoryId) ;
	}
	
	public static void main(String[] args) throws IOException {
		String title = null ;
		String content = null ;
		BufferedReader reader = null;
		int all = 0  ;
		int success = 0 ;
		File[] files = new File("/Users/ansj/Documents/workspace/test/data/cloud/云计算/").listFiles() ;
		for (File file : files) {
			if(file.getName().endsWith(".txt")){
				reader = IOUtil.getReader(new FileInputStream(file), "UTF-8") ;
				String category = findCategoryName(reader.readLine(), reader.readLine());
				reader.close() ;
				if("cloud".equals(category)){
					success++ ;
				}
				all++ ;
			}
		}
		
		System.out.println(success/(double)all);
		
	}
}
