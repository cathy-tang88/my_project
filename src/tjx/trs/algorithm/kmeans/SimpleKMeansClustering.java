package tjx.trs.algorithm.kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import love.cq.util.IOUtil;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.WeightedVectorWritable;
import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class SimpleKMeansClustering {
//	public static final double[][] points = { { 1, 1 ,3}, { 2, 1 }, { 1, 2 }, { 2, 2 }, { 3, 3 }, { 8, 8 }, { 9, 8 }, { 8, 9 }, { 9, 9 } };

	public static void writePointsToFile(List<Vector> points, String fileName, FileSystem fs, Configuration conf) throws IOException {
		Path path = new Path(fileName);
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, LongWritable.class, VectorWritable.class);
		long recNum = 0;
		VectorWritable vec = new VectorWritable();
		for (Vector point : points) {
			vec.set(point);
			writer.append(new LongWritable(recNum++), vec);
		}
		writer.close();
	}

	/**
	 * 得到所有的点
	 * @param raw
	 * @return
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static List<Vector> getPoints(String path) throws NumberFormatException, IOException {
		BufferedReader reader = IOUtil.getReader(path, "UTF-8") ;
		List<Vector> points = new ArrayList<Vector>();
		String temp = null ;
		while((temp=reader.readLine())!=null){
			String[] strs =  temp.trim().split(" ") ;
			double[] fr = new double[strs.length] ;
			for (int j = 0; j < strs.length; j++) {
				fr[j] = Double.parseDouble(strs[j]) ;
			}
			Vector vec = new RandomAccessSparseVector(fr.length);
			vec.assign(fr);
			points.add(vec);
		}
		return points;
	}

	public static void main(String args[]) throws Exception {

		int k = 4;

		List<Vector> vectors = getPoints("lda/model-final.theta");

		File testData = new File("testdata");
		if (!testData.exists()) {
			testData.mkdir();
		}
		testData = new File("testdata/points");
		if (!testData.exists()) {
			testData.mkdir();
		}

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		writePointsToFile(vectors, "testdata/points/file1", fs, conf);

		Path path = new Path("testdata/clusters/part-00000");
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, Text.class, Cluster.class);

		for (int i = 0; i < k; i++) {
			Vector vec = vectors.get(i);
			Cluster cluster = new Cluster(vec, i, new EuclideanDistanceMeasure());
			writer.append(new Text(cluster.getIdentifier()), cluster);
		}
		writer.close();

		KMeansDriver.run(conf, new Path("testdata/points"), new Path("testdata/clusters"), new Path("output"), new EuclideanDistanceMeasure(), 0.001, 10, true,
				false);

		SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path("output/" + Cluster.CLUSTERED_POINTS_DIR + "/part-m-00000"), conf);

		IntWritable key = new IntWritable();
		WeightedVectorWritable value = new WeightedVectorWritable();
		FileOutputStream fos = new FileOutputStream("testdata/result.txt") ;
		while (reader.next(key, value)) {
			fos.write((value.toString() + " belongs to cluster " + key.toString()+"\n").getBytes());
		}
		fos.flush() ;
		fos.close() ;
		reader.close();
	}

}