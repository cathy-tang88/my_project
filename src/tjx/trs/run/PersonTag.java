package tjx.trs.run;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import tjx.trs.pojo.Person;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;

public class PersonTag {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = IOUtil.getReader("D:\\语料\\SogouQ\\filter.txt", "UTF-8");
		String temp = null;
		Forest forest = Library.makeForest("data/csdn_class.txt");
		GetWord getWord = null;
		HashMap<String, Person> personTag = new HashMap<String, Person>() ;
		Person person = null ;
		String sessionId  = null ;
		String line = null ;
		while ((line = reader.readLine()) != null) {
			temp = line.toLowerCase() ;
			String[] split = temp.split("\t");
			sessionId = split[1] ;
			person=personTag.get(sessionId) ;
			if(person==null){
				person = new Person();
				personTag.put(sessionId, person) ;
			}
			getWord = new GetWord(forest, split[2]);
			boolean flag = false ;
			while((temp=getWord.getFrontWords())!=null){
				flag = true ;
				person.addTag(temp,Double.parseDouble(getWord.getParam(1))) ;
			}
			if(!flag){
				System.out.println(line);
			}
		}
		
		
		Set<Entry<String, Person>> entrySet = personTag.entrySet() ;
		for (Entry<String, Person> entry : entrySet) {
		System.out.println(entry.getKey()+"\t"+entry.getValue().findTopTag(5));
		}
	}
}
