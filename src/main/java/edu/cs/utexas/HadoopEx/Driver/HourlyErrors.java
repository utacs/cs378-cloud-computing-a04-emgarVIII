package edu.cs.utexas.HadoopEx.Driver;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/* Files import */
import edu.cs.utexas.HadoopEx.HourlyGPS.*;

public class HourlyErrors extends Configured implements Tool {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		// Task 1: file output
		// Task 2 file intermediate output
		int res = -1;
		System.out.println("Num arguments: " + args.length);
		if (args.length == 2) {
			res = ToolRunner.run(new Configuration(), new HourlyErrors(), args);
		} else if (args.length == 3) {
			res = ToolRunner.run(new Configuration(), new ErrorRatesRunner(), args);
		} else if (args.length == 4) {
			res = ToolRunner.run(new Configuration(), new EfficiencyRunner(), args);
		}


		System.exit(res);
	}

	/**
	 * 
	 */
	public int run(String args[]) {
		try {
			Configuration conf = new Configuration();

			Job job = new Job(conf, "HourlyErrors");
			job.setJarByClass(HourlyErrors.class);

			// specify a Mapper
			job.setMapperClass(HourlyErrorsMapper.class);

			// specify a Reducer
			job.setReducerClass(HourlyErrorsReducer.class);

			// specify output types
			job.setOutputKeyClass(IntWritable.class);
			job.setOutputValueClass(IntWritable.class);

			// specify input and output directories
			FileInputFormat.addInputPath(job, new Path(args[0]));
			job.setInputFormatClass(TextInputFormat.class);

			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			job.setOutputFormatClass(TextOutputFormat.class);

			return (job.waitForCompletion(true) ? 0 : 1);

		} catch (InterruptedException | ClassNotFoundException | IOException e) {
			System.err.println("Error during mapreduce job.");
			e.printStackTrace();
			return 2;
		}
	}
}
