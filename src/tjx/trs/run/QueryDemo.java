package tjx.trs.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;

public class QueryDemo {
	public static void main(String[] args) throws Exception {

		FileOutputStream fos = new FileOutputStream("D:\\语料\\SogouQ\\filter.txt");
		File[] files = new File("D:\\语料\\SogouQ\\split").listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].canRead() || !files[i].getName().endsWith("sogou")) {
				continue;
			}
			BufferedReader reader = IOUtil.getReader(new FileInputStream(files[i]), "GBK");
			String temp = null;

			while ((temp = reader.readLine()) != null) {
				String[] split = temp.split("\t");
				if (filter(split[2])) {
					fos.write(temp.getBytes());
					fos.write("\n".getBytes());
				}
			}
		}
		fos.flush();
		fos.close();
	}

	private static Forest forest = null;

	public static boolean filter(String query) throws Exception {
		if (forest == null) {
			forest = Library.makeForest("data/csdn_class.txt");
		}
		GetWord gw = new GetWord(forest, query);
		String temp = null;
		while ((temp = gw.getFrontWords()) != null) {
			return true;
		}
		return false;
	}
}
