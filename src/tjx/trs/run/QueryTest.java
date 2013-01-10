package tjx.trs.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

import love.cq.util.IOUtil;

public class QueryTest {
	public static void main(String[] args) throws Exception {
		
		String temp = null;
		int success = 0 ;
		int error = 0 ;
		BufferedReader reader = IOUtil.getReader("data/query/success.txt", "gbk");
		while ((temp = reader.readLine()) != null) {
			temp = temp.toLowerCase() ;
			if(QueryDemo.filter(temp)){
				success++ ;
			}else{
				System.out.println(temp);
			}
		}
		reader = IOUtil.getReader("data/query/error.txt", "gbk");
		while ((temp = reader.readLine()) != null) {
			temp = temp.toLowerCase() ;
			if(QueryDemo.filter(temp)){
				System.out.println(temp);
				error++ ;
			}
		}
		System.out.println(success);
		System.out.println(error);
		double ps =(success/(double)500);
		System.out.println("召回率:"+ps);
		double pe =(success/(double)(success+error)) ;
		System.out.println("准确率:"+pe);
		
		System.out.println(2*ps*pe/(ps+pe));
		reader.close() ;
	}
}
