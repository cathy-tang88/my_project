package tjx.trs.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import tjx.trs.util.CollectionUtil;

/**
 * one perosn have some tags
 * @author dell
 *
 */
public class Person {
	private String id = null ;
	private HashMap<String, Double> tagsMap= new HashMap<String, Double>() ;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, Double> getTagsMap() {
		return tagsMap;
	}

	public void setTagsMap(HashMap<String, Double> tagsMap) {
		this.tagsMap = tagsMap;
	}

	/*
	 * give it a tag
	 */
	public void addTag(String tag,Double score){
		if(tagsMap.containsKey(tag)){
			tagsMap.put(tag, tagsMap.get(tag)+score) ;
		}else{
			tagsMap.put(tag,score) ;
		}
	}
	
	//find this person top n tag by word freq
	public List findTopTag( int n ){
	
		List<Entry<String, Double>> sortMapByValue = CollectionUtil.sortMapByValue(tagsMap, 1) ;
		n = Math.min(sortMapByValue.size(), n);
		List<String> result = new ArrayList<String>(n) ;
		for (int i = 0; i < n; i++) {
			result.add(sortMapByValue.get(i).getKey()) ;
		}
		return result ;
	}

}
