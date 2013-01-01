package tjx.trs.run;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;
import love.cq.util.StringUtil;
import tjx.trs.pojo.PersonString;

public class PersonStringMake {
	public static void main(String[] args) throws Exception {
		// BufferedReader reader =
		// IOUtil.getReader("D:\\”Ô¡œ\\SogouQ\\filter.txt", "UTF-8");
		BufferedReader reader = IOUtil.getReader("/Users/ansj/Downloads/filter.txt", "UTF-8");
		String temp = null;
		Forest forest = Library.makeForest("data/csdn");
		GetWord getWord = null;
		HashMap<String, PersonString> personTag = new HashMap<String, PersonString>();
		PersonString person = null;
		String sessionId = null;
		String line = null;
		while ((line = reader.readLine()) != null) {
			temp = line.toLowerCase();
			String[] split = temp.split("\t");
			sessionId = split[1];
			person = personTag.get(sessionId);
			if (person == null) {
				person = new PersonString();
				personTag.put(sessionId, person);
			}
			getWord = new GetWord(forest, split[2]);
			boolean flag = false;
			while ((temp = getWord.getFrontWords()) != null) {
				flag = true;
				person.addTags(temp);
			}
			if (!flag) {
				System.out.println(line);
			}
		}

		FileOutputStream fos = new FileOutputStream("lda/LDA.txt");
		Set<Entry<String, PersonString>> entrySet = personTag.entrySet();
		String words = null;
		for (Entry<String, PersonString> entry : entrySet) {
			words = entry.getValue().toString();
			if (StringUtil.isNotBlank(words)) {
				fos.write(words.trim().getBytes("UTF-8"));
				fos.write("\n".getBytes());
			}
		}
	}
}
