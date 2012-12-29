package tjx.trs.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 提供了网页下载的工具包
 * 
 * @author tjx
 * 
 */
public class PageDown {

	/**
	 * 下载一个网页的内容去除html
	 * 
	 * @param url
	 * @return
	 */
	public static String getText(String url) {
		Document document = getDocument(url) ;
		if(document!=null){
			return document.text() ;
		}
		return null;
	}

	/**
	 * 返回jsonp的文档
	 * @param url
	 * @return
	 */
	public static Document getDocument(String url) {
		Document document = null;
		try {
			document = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)").referrer("www.test.com").get();
			return document;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 下载一个网页的内容包含html
	 * 
	 * @param url
	 * @return
	 */
	public static String getHtml(String url) {
		Document document = getDocument(url) ;
		if(document!=null){
			return document.html() ;
		}
		return null;
	}

	public static void main(String[] args) {
		Document document = getDocument("http://www.csdn.net/article/2012-12-28/2813230-JavaScript-Developer-Survey-2012") ;
	}

}
