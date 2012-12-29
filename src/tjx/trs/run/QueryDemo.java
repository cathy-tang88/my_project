package tjx.trs.run;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;

public class QueryDemo {
	public static void main(String[] args) throws Exception {
		String query = "java虚拟机下载" ;
		System.out.println(query+" 是否和计算机相关: "+filter(query));
		query = "巨蟹座是2b" ;
		System.out.println(query+" 是否和计算机相关: "+filter(query));
	}
	
	public static boolean filter(String query) throws Exception{
		Forest forest = Library.makeForest("data/csdn_class.txt") ;
		GetWord gw = new GetWord(forest, query) ;
		String temp = null ;
		while((temp=gw.getFrontWords())!=null){
			return true ;
		}
		return false ;
	}
}
