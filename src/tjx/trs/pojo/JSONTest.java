package tjx.trs.pojo;

import com.alibaba.fastjson.JSONObject;

public class JSONTest {
	public static void main(String[] args) {
		Document doc = new Document() ;
		doc.setAuthor("ansj") ;
		doc.setTitle("tjx shi pig") ;
		doc.setBody(" you dao li ") ;
		
		//可以把一个对象转换为字符串。这个字符串可以存到文件中 。。就是对象序列化
		String jsonString = JSONObject.toJSONString(doc) ;
		System.out.println(jsonString);
		
		//反序列化
		Document parse = JSONObject.parseObject(jsonString, Document.class) ;
		System.out.println( parse.getTitle());
		
	}
}

class Document{
	private String title ;
	private String body ;
	private String author ;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}