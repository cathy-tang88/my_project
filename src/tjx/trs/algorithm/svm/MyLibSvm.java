package tjx.trs.algorithm.svm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import love.cq.util.IOUtil;

public class MyLibSvm {
	public static void main(String[] args) throws NumberFormatException, IOException {
		String vectors =null;
		BufferedReader reader = IOUtil.getReader("svm/my-tra", "UTF-8") ;
		String temp = null ;
		int categoryId = 0  ;
int all = 0 ;		
int success = 0  ;
		while((temp=reader.readLine())!=null){
			all++ ;
			String[] values = temp.split("  ") ;
			categoryId = Integer.parseInt(values[0]) ;
			vectors = values[1] ;
			String[] strs = vectors.split(" ") ;
			HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>() ;
			String[] kv = null ;
			for (String string : strs) {
				kv =string.split(":") ;
				hm.put(Integer.parseInt(kv[0]), Integer.parseInt(kv[1])) ;
			}
			double findCategory = findCategory(hm) ;
			if(findCategory==categoryId){
				success++ ;
			}
		}
System.out.println(success/(double)all);
	}

	private static svm_model model = null;
	static {
		//加载模型
		try {
			model = svm.svm_load_model("svm/my-tra.model");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据记录查找分数
	 * @param record
	 * @return
	 */
	public static double findCategory(Map<Integer,Integer> record) {
		return predictPerRecord(record, model) ;
	}
	
	
	public static double predictPerRecord(Map<Integer,Integer> record, svm_model model) {
        svm_node[] x = new svm_node[record.size()];
        Set<Entry<Integer, Integer>> entrySet = record.entrySet() ;
        int i = 0  ;
        for (Entry<Integer, Integer> entry : entrySet) {
        	 svm_node node = new svm_node();
             node.index = entry.getKey();
             node.value = entry.getValue();
             x[i] = node;
             i++ ;
		}
        double v = svm.svm_predict(model, x);
        return v;
    }
}
