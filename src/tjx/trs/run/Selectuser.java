package tjx.trs.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import love.cq.util.IOUtil;

public class Selectuser {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = IOUtil.getReader("D:\\”Ô¡œ\\SogouQ\\filter.txt", "UTF-8");
		String temp = null;
        HashMap<String, HashMap<String, Integer>> tagHashMap=new HashMap<String, HashMap<String, Integer>> ();
		while ((temp = reader.readLine()) != null) {
			String[] split = temp.split("\t");
			String id=split[1];
			String queryString=split[2];
			if(tagHashMap.containsKey(id))
			{
				HashMap<String,Integer> hasMap=tagHashMap.get(id);
				
				if(hasMap.containsKey(queryString)){
				hasMap.put(queryString, hasMap.get(queryString)+1);
				}
				else{
					hasMap.put(queryString, 1);
				}
				tagHashMap.put(id, hasMap);
			}
			else{
				HashMap<String , Integer> noMap=new HashMap<String, Integer>();
				if(noMap.containsKey(queryString)){
					noMap.put(queryString, noMap.get(queryString)+1);
					}
					else{
						noMap.put(queryString, 1);
					}
				tagHashMap.put(id, noMap);
			}
			
		}
		
		System.out.println(tagHashMap);
	}
}
