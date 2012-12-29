package tjx.trs.run;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tjx.trs.util.CollectionUtil;

import love.cq.util.IOUtil;

public class Selectuser {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = IOUtil.getReader("D:\\”Ô¡œ\\SogouQ\\filter.txt", "UTF-8");
		HashMap<String, Integer> hsmap=new HashMap<String, Integer>();
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			String[] split = temp.split("\t");
			String query = split[2];
			if(hsmap.containsKey(query)){
				hsmap.put(query,hsmap.get(query)+1);
			}
			else{
				hsmap.put(query,1);
			}
		}
		Iterator it = hsmap.entrySet().iterator();
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sort.txt",false)));
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
			String word = entry.getKey();
			Integer number=entry.getValue();
			writer.write(word+"\t"+number+"\r\n");
		}
		writer.close();
	}
	
}
