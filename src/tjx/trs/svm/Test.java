package tjx.trs.svm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map.Entry;

import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.util.newWordFind.LearnTool;

import love.cq.util.IOUtil;
import love.cq.util.StringUtil;

import com.alibaba.fastjson.JSONArray;

public class Test {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		String text = IOUtil.getReader("svm/training/ml.txt", "UTF-8").readLine() ;
		JSONArray array = JSONArray.parseArray(text) ;
		LearnTool learn = new LearnTool() ;
		String temp = null ;
		for (int i = 0; i < array.size(); i++) {
			temp = array.getJSONObject(i).get("title").toString()+array.getJSONObject(i).get("content").toString() ;
			NlpAnalysis.paser(temp, learn) ;
		}
		List<Entry<String, Double>> topTree = learn.getTopTree(1000) ;
		for (Entry<String, Double> entry : topTree) {
			if(StringUtil.isNotBlank(entry.getKey().replace(" ", ""))){
				System.out.println(entry.getKey().trim().length()+"\t"+entry.getKey());
			}
			
		}
	}
}
