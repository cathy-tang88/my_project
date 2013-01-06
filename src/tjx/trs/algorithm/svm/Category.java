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
import tjx.trs.util.PageDown;
import tjx.trs.util.StaticValue;

/**
 * 传入一个文章.进行类别判定
 * 
 * @author ansj
 * 
 */
public class Category {

	public static int findCategoryId(String title, String content) {
		content = content + title;
		GetWord gw = new GetWord(StaticValue.getForestId(), content);
		Integer id = null;
		TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
		while (gw.getFrontWords() != null) {
			id = Integer.parseInt(gw.getParam(0));
			if (tm.containsKey(id)) {
				tm.put(id, tm.get(id) + 1);
			} else {
				tm.put(id, 1);
			}
		}

		return (int) MyLibSvm.findCategory(tm);
	}

	public static String findCategoryName(String title, String content) {
		int categoryId = findCategoryId(title, content);
		return StaticValue.CATEGORYIDMAP.get(categoryId);
	}

	public static void main(String[] args) throws IOException {
		String title = null;
		String content = null;
//		BufferedReader reader = null;
//		int all = 0;
//		int success = 0;
//		File[] files = new File("D:\\200").listFiles();
//		for (File file : files) {
//			if (file.getName().endsWith(".txt")) {
//				reader = IOUtil.getReader(new FileInputStream(file), "GBK");
//				String category = findCategoryName(reader.readLine(), reader.readLine());
//				reader.close();
//				System.out.println(category);
//				if ("code".equals(category)) {
//					success++;
//				}
//				all++;
//			}
//		}
//System.out.println(all);
//		System.out.println(success / (double) all);
//		title ="" ;
//		content=PageDown.getText("http://www.cnblogs.com/nerxious/archive/2012/12/18/2823617.html") ;
//
//		String category = findCategoryName(title,	content);
//		System.out.println(category);
		
		BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\filter.txt", "UTF-8");
		String line = null;
		while ((line = reader.readLine()) != null) {
			line = line.toLowerCase();
			line = line.split("\t")[5];
			
		String category = findCategoryName("",	PageDown.getText(line));
		System.out.println(category+"\t"+line);
		}
		
	}
}
