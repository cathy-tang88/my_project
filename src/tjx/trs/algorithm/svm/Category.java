package tjx.trs.algorithm.svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;

import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;
import tjx.trs.util.StaticValue;

/**
 * 传入一个文章.进行类别判定
 * 
 * @author ansj
 * 
 */
public class Category {

	public static int findCategoryId(String title, String content) {
		content = content + title;
		GetWord gw = new GetWord(StaticValue.getForestId(), content);
		Integer id = null;
		TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
		while (gw.getFrontWords() != null) {
			id = Integer.parseInt(gw.getParam(0));
			if (tm.containsKey(id)) {
				tm.put(id, tm.get(id) + 1);
			} else {
				tm.put(id, 1);
			}
		}

		return (int) MyLibSvm.findCategory(tm);
	}

	public static String findCategoryName(String title, String content) {
		int categoryId = findCategoryId(title, content);
		return StaticValue.CATEGORYIDMAP.get(categoryId);
	}

	public static void main(String[] args) throws IOException {
		String title = null;
		String content = null;
		// BufferedReader reader = null;
		// int all = 0 ;
		// int success = 0 ;
		// File[] files = new
		// File("/Users/ansj/Documents/workspace/test/data/cloud/云计算/").listFiles()
		// ;
		// for (File file : files) {
		// if(file.getName().endsWith(".txt")){
		// reader = IOUtil.getReader(new FileInputStream(file), "UTF-8") ;
		// String category = findCategoryName(reader.readLine(),
		// reader.readLine());
		// reader.close() ;
		// if("cloud".equals(category)){
		// success++ ;
		// }
		// all++ ;
		// }
		// }
		//
		// System.out.println(success/(double)all);
		//
		String category = findCategoryName(
				"Amazon CEO Jeff Bezos：好年景，大挑战",
				"2012年，对Amazon的CEO Jeff Bezos来说，是个收获的好年景。11月，《财富》杂志评选其为2012年度商业精英。随后，《哈佛商业》也将其评为全球第二位最有价值CEO。注：依照《哈佛商业》的评选标准依照公司业绩和市场价值的贡献来排名：第一名还是Steve Jobs（他在过去17年都被评为第一名），苹果从1997年到2011年市值增长了3590亿美元， 为股东带来丰厚的回报，所以即使Steve Jobs已经逝去，但第一名仍然保持不变。（而作为Steve Jobs的接任者，《哈佛商业》认为Tim Cook不能完全取代Steve Jobs的地位）。    Amazon CEO Jeff Bezos  Jeff Bezos从2010年的第七名一跃成为2012年的第二名，其本身业绩非常亮眼。在他的领导下，亚马逊为股东带来12266%的投资回报，市值增加1110亿美元。尤其是大举进军云计算服务领域，获得市场定价权，更是带来新的价值增长点。但是，在亚马逊三大业务方向——在线零售、出版业、IaaS服务上，也不难看到其正在面临巨大挑战。  首先，销售税。Amazon享受了将近16年的“税务优势”将不复存在。Amazon被迫要在更多的州收取销售税，当然，这也使Amazon相对于实体零售商的竞争优势受到很大影响（资料：1992年，最高法院曾经做出裁决：如果一家公司在一个州并没有任何实体的存在，他们就可以不必征收销售税。实体店对此意见明显，认为违背公平原则，这也使得亚马逊的商品的销售价格优势比较明显。）  其次，AWS（Amazon Web Services）在IaaS方面也面临更加激烈的竞争。Rackspace、IBM、 VMware、Joyent、SoftLayer和其他竞争对手日益成熟，另一方面，在AWS希望吸引更多用户应用的同时，其数据中心稳定的服务能力也在不断被质疑。比如刚刚在圣诞期间Netflix由于AWS宕机所造成的损失。当然，还有潜藏的一方面，在AWS上成长起来的企业，如Netflix，既是Amazon的用户，却也是Amazon Prime即时视频服务的竞争对手。所以关系显然更为复杂。（这将是所有IaaS平台所面临的挑战，毕竟，IaaS，PaaS和SaaS之间的界限正在被一再打破，合纵连横时代已经到来）。  第三，最令Amazon担忧的对手，谷歌。后者正在虎视眈眈地挑战AWS的IaaS的领域，且有着令人胆颤的技术手段。（编译/郭雪梅）  《哈佛商业》如此评价Amazon CEO Jeff Bezos的业绩：");
		System.out.println(category);
	}
}
