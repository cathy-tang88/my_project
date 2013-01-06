package tjx.trs.util;

import java.util.HashMap;

import love.cq.domain.Forest;
import love.cq.library.Library;

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

	private static Forest forestId = null;

	private static Forest forest = null;

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
}
