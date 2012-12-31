package tjx.trs;

import java.io.BufferedReader;
import java.io.StringReader;

import javax.crypto.Cipher;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;

public class Test {
	public static void main(String[] args) throws Exception {
		 /**
         * 词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
         */
        String dic = "中国\t1\tzg\n人名\t2\n中国人民\t4\n人民\t3\n孙健\t5\nCSDN\t6\njava\t7\njava学习\t10\n";
        
        
       //构造tire树
        
        Forest forest = Library.makeForest(new BufferedReader(new StringReader(dic)));

   
        String content = "中国人名识别是中国人民的一个骄傲.孙健人民在CSDN中学到了很多最早iteye是java学习笔记叫javaeye但是java123只是一部分";
        GetWord udg = forest.getWord(content);

        String temp = null;
        while ((temp = udg.getFrontWords()) != null)
       
        	
            System.out.println(temp + "\t\t" + udg.getParam(0) + "\t\t" + udg.getParam(1)+"\t\t" + udg.getParam(3));

	}
}
