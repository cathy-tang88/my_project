package tjx.trs.util;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;

import org.ansj.util.newWordFind.NewTerm;

import tjx.trs.pojo.URLScore;

import com.sun.org.apache.bcel.internal.generic.NEW;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.util.IOUtil;

/**
 * 储存了一些静态变量
 * 
 * @author tjx
 * 
 */
public class StaticValue {
	/**
	 * 每个类别对应的id
	 */
	public static final HashMap<String, Integer> CATEGORYMAP = new HashMap<String, Integer>();

	public static final HashMap<Integer, String> CATEGORYIDMAP = new HashMap<Integer, String>();
	
	public static final HashMap<String, URLScore> URLMAP = new HashMap<String, URLScore>();

	private static Forest forestId = null;

	private static Forest forest = null;
	
	
	public static HashSet<String> STOP= new HashSet<String>() ;

	static {
		CATEGORYMAP.put("cloud", 1);
		CATEGORYMAP.put("code", 2);
		CATEGORYMAP.put("database", 3);
		CATEGORYMAP.put("enterprise", 4);
		CATEGORYMAP.put("ml", 5);
		CATEGORYMAP.put("mobile", 6);
		CATEGORYMAP.put("system", 7);
		CATEGORYMAP.put("web", 8);
		CATEGORYMAP.put("www", 9);

		CATEGORYIDMAP.put(1, "cloud");
		CATEGORYIDMAP.put(2, "code");
		CATEGORYIDMAP.put(3, "database");
		CATEGORYIDMAP.put(4, "enterprise");
		CATEGORYIDMAP.put(5, "ml");
		CATEGORYIDMAP.put(6, "mobile");
		CATEGORYIDMAP.put(7, "system");
		CATEGORYIDMAP.put(8, "web");
		CATEGORYIDMAP.put(9, "www");

		try {
			forestId = Library.makeForest("data/library.dic");
			forest = Library.makeForest("data/csdn_class.txt");
			
			BufferedReader reader = IOUtil.getReader("data/newWordFilter.dic", "UTF-8") ;
			String temp = null ;
			while((temp=reader.readLine())!=null){
				STOP.add(temp.toLowerCase()) ;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			BufferedReader reader = IOUtil.getReader("data/domainScore.txt", "UTF-8") ;
			String temp = null ;
			String[]  strs = null ;
			URLScore uScore = null ;
			while((temp=reader.readLine())!=null){
				strs = temp.split("\t") ;
				uScore = new URLScore(Integer.parseInt(strs[1]), Integer.parseInt(strs[2]), Double.parseDouble(strs[3])) ;
				URLMAP.put(strs[0].toLowerCase(), uScore) ;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Forest getForestId() {
		return forestId;
	}

	public static Forest getForest() {
		return forest;
	}

	public static URLScore getUrlScore(String url) {
		// TODO Auto-generated method stub
		String domain = getDomain(url) ;
		return URLMAP.get(domain) ;
		
	}
	
	public static String getDomain(String url) {
		return url.split("//")[1].split("/")[0];
	}
}
