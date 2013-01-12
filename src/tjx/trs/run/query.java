package tjx.trs.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import love.cq.util.IOUtil;

public class query {
	public static void main(String[] args) throws IOException {

		HashMap<String, Integer[]> result = new HashMap<String, Integer[]>();

		// 统计计算机类的域名
		BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\filter.txt", "UTF-8");
		String temp = null;
		String domain = null;
		Integer[] values = null;
		while ((temp = reader.readLine()) != null) {
			String[] split = temp.toLowerCase().split("\t");
			domain = getDomain(split[5]).toLowerCase();

			if ((values = result.get(domain)) == null) {
				values = new Integer[2];
				values[0] = 1;
				result.put(domain, values);
			} else {
				values[0] += 1;
			}
		}

		// 统计非计算机类的域名
		reader = IOUtil.getReader("D:\\语料\\SogouQ\\all.txt", "UTF-8");
		while ((temp = reader.readLine()) != null) {
			String[] split = temp.toLowerCase().split("\t");
			domain = getDomain(split[5]).toLowerCase();

			if ((values = result.get(domain)) == null) {
				continue;
			}

			if (values[1] == null) {
				values[1] = 1;
			} else {
				values[1] += 1;
			}
		}
		
		
		StringBuilder sb = new StringBuilder() ;
		Set<Entry<String, Integer[]>> entrySet = result.entrySet() ;
		for (Entry<String, Integer[]> entry : entrySet) {
			sb.append(entry.getKey()) ;
			sb.append("\t") ;
			sb.append(entry.getValue()[0]==null?0:entry.getValue()[0]) ;
			sb.append("\t") ;
			sb.append(entry.getValue()[1]==null?0:entry.getValue()[1]) ;
			sb.append("\n") ;
		}
		
		IOUtil.Writer("data/domainScore.txt", "UTF-8", sb.toString()) ;
		
	}

	public static String getDomain(String url) {
		return url.split("//")[1].split("/")[0];
	}
}

