package tjx.trs.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import tjx.trs.util.StaticValue;

import love.cq.util.IOUtil;

public class QueryComputation {
	public static void main(String[] args) throws IOException {

		HashMap<String, Integer[]> result = new HashMap<String, Integer[]>();
		int allComputer = 0;
		int allOther = 0;
		// ͳ�Ƽ�����������
		BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\filter.txt", "UTF-8");
		String temp = null;
		String domain = null;
		Integer[] values = null;
		while ((temp = reader.readLine()) != null) {
			String[] split = temp.toLowerCase().split("\t");
			domain = StaticValue.getDomain(split[5]).toLowerCase();
			allComputer++;
			if ((values = result.get(domain)) == null) {
				values = new Integer[2];
				values[0] = 1;
				result.put(domain, values);
			} else {
				values[0] += 1;
			}
		}
		reader.close();
		// ͳ�ƷǼ�����������

		File[] files = new File("D:\\����\\SogouQ\\all").listFiles();

		for (int i = 0; i < files.length; i++) {
			if (!files[i].canRead() || !files[i].getName().endsWith("sogou")) {
				continue;
			}

			reader = IOUtil.getReader(new FileInputStream(files[i]), "UTF-8");
			while ((temp = reader.readLine()) != null) {
				String[] split = temp.toLowerCase().split("\t");
				domain = StaticValue.getDomain(split[5]).toLowerCase();

				if ((values = result.get(domain)) == null) {
					continue;
				}
				allOther++;
				if (values[1] == null) {
					values[1] = 1;
				} else {
					values[1] += 1;
				}
			}
			reader.close();

		}

		StringBuilder sb = new StringBuilder();
		Set<Entry<String, Integer[]>> entrySet = result.entrySet();
		int computer = 0;
		int other = 0;
		double score = 0  ;
		for (Entry<String, Integer[]> entry : entrySet) {
			computer = entry.getValue()[0] == null ? 0 : entry.getValue()[0];
			computer+=1 ;
			other = entry.getValue()[1] == null ? 0 : (entry.getValue()[1] - computer)+1;
			other+=1 ;
			score =  (computer/(double)allComputer)/((computer/(double)allComputer)+(other/(double)allOther)) ;
			System.out.println(computer);
			System.out.println(other);
			System.out.println(score);
			
			sb.append(entry.getKey());
			sb.append("\t");
			sb.append(computer);
			sb.append("\t");
			sb.append(other);
			sb.append("\t");
			sb.append(score);
			sb.append("\n");
		}

		IOUtil.Writer("data/domainScore.txt", "UTF-8", sb.toString());

	}

	
}
