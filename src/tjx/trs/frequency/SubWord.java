package tjx.trs.frequency;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import love.cq.util.IOUtil;
import love.cq.util.StringUtil;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.Graph;
import org.ansj.util.recognition.NatureRecognition;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SubWord {

	private static HashSet<String> filter = new HashSet<String>();

	public static void main(String[] args) throws IOException {
		// 加载停用词词典
		// List<String> a = new ArrayList<String>();
		HashMap<String, Integer> wordHashMap = new HashMap<String, Integer>();
		String filename = "data/training/code.txt";
		BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		String line = null;

		// 加载停用词词典
		initFilter();
		// 对每句话分词
		while ((line = bReader.readLine()) != null) {
			JSONArray parseArray = JSONArray.parseArray(line);
			String temp = null;
			for (Object object : parseArray) {
				JSONObject jsonObject = (JSONObject) object;
				List<String> paser = substr(jsonObject.getString("title") + "   " + jsonObject.getString("content"));
				for (int i = 0; i < paser.size(); i++) {
					
					if (wordHashMap.containsKey(paser.get(i))) {
						String a = paser.get(i).toString();
						wordHashMap.put(a, wordHashMap.get(paser.get(i)) + 1);
					} else {
						wordHashMap.put(paser.get(i).toString(), 1);
					}
				}
			}

		}

	//	System.out.println(wordHashMap.size());
//System.out.println(wordHashMap);
		List<Entry<String, Integer>> sortMapByValue = sortMapByValue(wordHashMap, 1);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			sb.append(sortMapByValue.get(i)+"\r\n");
			//System.out.println(sortMapByValue.get(i));
		
			IOUtil.Writer("data/class/code.txt", "UTF-8", sb.toString());
		}

	}

	private static List<String> substr(String line) {
		// 将合成词存储在这个集合中
		List<String> all = new ArrayList<String>();
		// 进行分词
		List<Term> paser = ToAnalysis.paser(line);
		new NatureRecognition(paser).recognition();
		Term term = null;
		// 链表结构下一个词
		Term to = null;
		// 对分词结果的一个遍历

		for (int i = 0; i < paser.size(); i++) {
			term = paser.get(i);
			if (filter(term)) {
				continue;
			}
			boolean flag = false;
			StringBuilder sb = new StringBuilder();
			sb.append(term.getName());
			to = term;
			if (to.getNatrue().natureStr.equals("userDefine")) {
				flag = true;
			}
			while ((to = to.getTo()) != null && to.getOffe() < line.length() && !filter(to)) {//getTo 是链表的左边，GetFrom 是链表的右面，指针走到最到最后并且不是停用词
				if (to.getNatrue().natureStr.equals("userDefine")) {
					flag = true;
				}
				sb.append(to.getName());
				if (flag) {
					all.add(sb.toString());
				}
			}
		}
		return all;
	}

	/**
	 * 判断一个词是否可能是新词的一部分
	 * 
	 * @param str
	 * @return true不可能.false可能
	 */
	public static boolean filter(Term term) {
		String str = term.getName();
		if (filter.contains(str)) {
			return true;
		}

		// 词性过滤
		String natureStr = term.getNatrue().natureStr;
		if ((natureStr == null || natureStr.contains("m") || "v".equals(natureStr)) && term.getTermNatures().allFreq > 1000) {
			return false;
		}
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	private static void initFilter() throws IOException {
		String fileFullName = "data/newWordFilter.dic";
		BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileFullName), "utf-8"));
		String line = null;
		while ((line = bReader.readLine()) != null) {
			filter.add(line);
		}
		filter.add("!");
		filter.add("．");
		filter.add("　");
		filter.add("·");
		filter.add(" ");
		filter.add("—");
		filter.add("——");
	}

	/**
	 * map 按照value排序
	 * 
	 * @return
	 */
	public static <K, V> List<Map.Entry<K, V>> sortMapByValue(HashMap<K, V> map, final int sort) {
		List<Map.Entry<K, V>> orderList = new ArrayList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(orderList, new Comparator<Map.Entry<K, V>>() {
			@Override
			@SuppressWarnings("unchecked")
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (((Comparable<V>) o2.getValue()).compareTo(o1.getValue())) * sort;
			}
		});
		return orderList;
	}

	/**
	 * 归并子串，把大串找出来
	 * 
	 * @param a
	 */
/*	public static void cut(List<String> a) {
		for (int i = 0; i < a.size(); i++) {
			int flag = 0;
			for (int j = 0; j < a.size(); j++) {
				if (i == j)
					continue;
				if (a.get(j).contains(a.get(i))) {
					flag = 1;
					break;
				}

			}
			if (flag == 0) {
				System.out.println(a.get(i));
			}
		}

	}*/
}