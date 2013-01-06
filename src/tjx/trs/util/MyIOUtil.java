package tjx.trs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import love.cq.util.IOUtil;

public class MyIOUtil {
	public static String getContent(File file){
		BufferedReader reader = null ;
		StringBuilder sb = new StringBuilder() ;
		try {
			reader = IOUtil.getReader(new FileInputStream(file), "GBK") ;
			String temp = null ;
			while((temp=reader.readLine())!=null){
				sb.append(temp) ;
				sb.append(" ") ;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close() ;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb.toString() ;
	}
	
	
	public static void main(String[] args) {
		
	}
}
