package tjx.trs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import love.cq.util.IOUtil;

public class Readword {
	public static void main(String[] args) throws IOException {
		String temp=null;
		BufferedReader reader = IOUtil.getReader("data//word.txt", "UTF-8");
		int m=1;
		int w=0;
		
		while ((temp = reader.readLine()) != null) {
			String[] a=temp.split("\t");
			int n=0;
			
			m++;
			for(int i=1;i<a.length;i++){
				
				Integer numberInteger=Integer.parseInt(a[i]);
		 n+=numberInteger;
				
			}
			w+=n;
			System.out.println(m+"\t"+n);
	}
		System.out.println("w="+w);
}
	
}
