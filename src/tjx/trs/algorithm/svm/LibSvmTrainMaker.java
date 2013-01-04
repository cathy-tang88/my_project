package tjx.trs.algorithm.svm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tjx.trs.util.StaticValue;

public class LibSvmTrainMaker {
	/**
	 * 用样本数据进行libsvm训练数据格式整理
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		HashMap<String, Integer> categorymap = StaticValue.CATEGORYMAP;
		Set<Entry<String, Integer>> entrySet = categorymap.entrySet();
		String jsons = null;
		for (Entry<String, Integer> entry : entrySet) {
			jsons = IOUtil.getReader("data/training/" + entry.getKey() + ".txt", "UTF-8").readLine();
			JSONArray parseArray = JSONArray.parseArray(jsons);
			makeTrainning(parseArray, entry.getValue());
		}
		os.flush();
		os.close();
		
		//进行模型训练
		String[] trainArgs = {"svm/my-tra"};//directory of training file
		svm_train.main(trainArgs);
		
		//测试准确率
		MyLibSvm.main(null) ;
		
	}

	static OutputStream os = null;

	static Forest forest = null;

	static {

	}
static int size = 0 ;
	private static void makeTrainning(JSONArray parseArray, int categoryId) throws Exception {
		System.out.println(categoryId+"\t"+parseArray.size());
		// TODO Auto-generated method stub
		if (os == null) {
			os = new FileOutputStream("svm/my-tra");
			forest = Library.makeForest("data/library.dic");
		}
		HashMap<Integer, Integer> hm = null;
		JSONObject obj = null;
		GetWord gw = null;
		String temp = null;
		Integer id = null;
		StringBuilder sb = null ;
		for (Object object : parseArray) {
			obj = (JSONObject) object;
			sb = new StringBuilder();
			hm = new HashMap<Integer, Integer>();
			gw = new GetWord(forest, obj.getString("title"));
			makeFrequeMap(hm, gw);

			gw = new GetWord(forest, obj.getString("content"));
			makeFrequeMap(hm, gw);
			if (hm.size() > 0) {
				sb.append(categoryId + "  ");
				TreeMap<Integer, Integer> treemap = new TreeMap<Integer,Integer>(hm);
				Set<Entry<Integer, Integer>> entrySet = treemap.entrySet();
				for (Entry<Integer, Integer> entry : entrySet) {
					sb.append(entry.getKey() + ":" + 1 + " ");
				}
				sb.append("14030:-1\n");
				os.write(sb.toString().getBytes());
			}
		}
	}

	// 分词放入
	private static void makeFrequeMap(HashMap<Integer, Integer> hm, GetWord gw) {
		String temp = null;
		Integer id = null;
		while ((temp = gw.getFrontWords()) != null) {
			id = Integer.parseInt(gw.getParam(0));
			if (hm.containsKey(id)) {
				hm.put(id, hm.get(id) + 1);
			} else {
				hm.put(id, 1);
			}
		}
	}
}
