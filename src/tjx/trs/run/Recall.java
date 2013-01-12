package tjx.trs.run;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import tjx.trs.pojo.URLScore;
import tjx.trs.util.StaticValue;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;

public class Recall {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader reader = IOUtil.getReader(new FileInputStream("D:\\训练集\\url正确.txt"), "GBK");
		String temp = null;
		int success = 0;
		while ((temp = reader.readLine()) != null) {

			String[] split = temp.toLowerCase().split("\t");
			//System.out.println(split[1]);

			if (filter(temp.toLowerCase(),split[1])) {
				//System.out.println(split[1]);
				success++;
				
			}else{
				System.out.println(split[0]+":"+split[1]);
			}
		}
	//	System.out.println(success);
		int error = 0;
		reader = IOUtil.getReader(new FileInputStream("D:\\训练集\\错误的样本.txt"), "GBK");
		while ((temp = reader.readLine()) != null) {
			// System.out.println(temp);
			String[] split = temp.toLowerCase().split("\t");

			if (filter(split[2].toLowerCase(),split[5].toLowerCase())) {
				error++;
				//System.out.println(split[2]+":"+split[5]);
			}
		}
		 Double R=((double) success / 500);
		 Double P=((double) success / (success + error));
		 Double F=2*P*R/(P+R);
		System.out.println("召回率=" +R);
		System.out.println("准确率=" +P );
		System.out.println("F值="+F);
	}

	private static Forest forest = null;

	public static boolean filter(String query,String url) throws Exception {
		if (forest == null) {
			forest = Library.makeForest("data/library.dic");
			// forest = Library.makeForest(IOUtil.getReader("data/计算机与自动化.txt",
			// "UTF-8")) ;
		}
		GetWord gw = new GetWord(forest, query);
		double pe1 = 1;
		double pe2 = 1;
		int size = 0;
		int tempAllFreq = 0;
		String temp = null;

		while ((temp = gw.getFrontWords()) != null) {
			pe1 = pe1 * Double.parseDouble(gw.getParam(3));
			pe2 = pe2 * (1 - Double.parseDouble(gw.getParam(3)));
			tempAllFreq = Integer.parseInt(gw.getParam(2));
			size++;
		}
		// 如果只分出一个词语。并且这个词的other频率 大于1000 那么这个串串我们夜抛弃掉 这样 婚姻匹配 0.946122608833829
		// 这种词就不会出现了
	
		double p = pe1 / (pe1 + pe2);
		
		URLScore us = StaticValue.getUrlScore(url) ;
		
		//wo xian cou he xie a  
		if(us!=null&&us.score>0.8&&us.computer>2){
		//	System.out.println(query);
			return true ;
		}
		
		if (size == 0) {
			return false;
		}

		if (p > 0.85) {
			return true;
		}
		return false;
	}
}
