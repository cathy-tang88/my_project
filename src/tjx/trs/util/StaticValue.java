package tjx.trs.util;

import java.util.HashMap;

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
	}
}
