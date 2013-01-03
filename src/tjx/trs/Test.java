package tjx.trs;

import java.io.BufferedReader;
import java.io.StringReader;

import javax.crypto.Cipher;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;

public class Test {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = IOUtil.getReader("data/csdn_class.txt", "UTF-8") ;
		String temp = null ;
		int id = 0  ;
		StringBuilder sb = new StringBuilder() ;
		while((temp=reader.readLine())!=null){
			id++ ;
			sb.append(temp.split("\t")[0]+"\t"+id);
			sb.append("\n") ;
		}
		
		IOUtil.Writer("data/library.dic", "utf-8", sb.toString()) ;
	}
}
