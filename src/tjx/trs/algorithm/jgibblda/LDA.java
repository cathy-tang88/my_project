/*
 * Copyright (C) 2007 by
 * 
 * 	Xuan-Hieu Phan
 *	hieuxuan@ecei.tohoku.ac.jp or pxhieu@gmail.com
 * 	Graduate School of Information Sciences
 * 	Tohoku University
 * 
 *  Cam-Tu Nguyen
 *  ncamtu@gmail.com
 *  College of Technology
 *  Vietnam National University, Hanoi
 *
 * JGibbsLDA is a free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * JGibbsLDA is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JGibbsLDA; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */

package tjx.trs.algorithm.jgibblda;

import org.kohsuke.args4j.*;

public class LDA {

	public static void main(String args[]) {
		// 输入命令字符串数组
		// 训练
		String[] cmd = { "-est", "-alpha", "0.5", "-beta", "0.1", "-ntopics", "10", "-niters", "20", "-savestep", "2", "-twords", "20", "-dir","lda/" ,"-dfile",
				"LDA.txt" };
		// 测试
		// String[] cmd = {"-inf","-dir", "D://", "-model", "model-final",
		// "-niters", "30", "-twords", "20", "-dfile",
		// "\\workspace\\JavaLDA\\LDATest.txt"};
		LDACmdOption option = new LDACmdOption();
		CmdLineParser parser = new CmdLineParser(option);

		try {
			if (cmd.length == 0) {
				showHelp(parser);
				return;
			}

			parser.parseArgument(cmd);

			if (option.est || option.estc) {
				Estimator estimator = new Estimator();
				estimator.init(option);
				estimator.estimate();
			} else if (option.inf) {
				Inferencer inferencer = new Inferencer();
				inferencer.init(option);

				Model newModel = inferencer.inference();

				for (int i = 0; i < newModel.phi.length; ++i) {
					// phi: K * V
					System.out.println("-----------------------\ntopic" + i + " : ");
					for (int j = 0; j < 10; ++j) {
						System.out.println(inferencer.globalDict.id2word.get(j) + "\t" + newModel.phi[i][j]);
					}
				}
			}
		} catch (CmdLineException cle) {
			System.out.println("Command line error: " + cle.getMessage());
			showHelp(parser);
			return;
		} catch (Exception e) {
			System.out.println("Error in main: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	public static void showHelp(CmdLineParser parser) {
		System.out.println("LDA [options ...] [arguments...]");
		parser.printUsage(System.out);
	}

}
