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
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.io.FloatWritable;

import edu.cs.utexas.HadoopEx.ErrorRates.*;
import edu.cs.utexas.HadoopEx.EfficientDrivers.*;


public class EfficiencyRunner extends Configured implements Tool {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new ErrorRatesRunner(), args);
		System.exit(res);
	}

	/**
	 * 
	 */
	public int run(String args[]) {
		try {
		// System.out.println("File: " + args[0] + ", output of Map-Reduce task: " + args[1] + ", output of topK task: " + args[2]);
			Configuration conf = new Configuration();

			System.out.println("Creating job error rates");
			Job job = new Job(conf, "EfficiencyRunner");
			job.setJarByClass(EfficiencyRunner.class);

            // Pre-top5 Mapper/Reducers
			// specify a Mapper
			job.setMapperClass(AverageEarningsMapper.class);

			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			// specify a Reducer
			job.setReducerClass(AverageEarningsReducer.class);

			// specify output types
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(FloatWritable.class);

			// specify input and output directories
			FileInputFormat.addInputPath(job, new Path(args[0])); // taxi-data-small.csv
			job.setInputFormatClass(TextInputFormat.class);

			FileOutputFormat.setOutputPath(job, new Path(args[1]));  // intermediate-folder/
			job.setOutputFormatClass(TextOutputFormat.class);
        
            if (!job.waitForCompletion(true)) {
				return 1;
			}
            
			Job job2 = new Job(conf, "TopK");
			job2.setJarByClass(EfficiencyRunner.class);

			// specify a Mapper
			job2.setMapperClass(Top5EarningsMapper.class);

			// specify a Reducer
			job2.setReducerClass(Top5EarningsReducer.class);

			// specify output types
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(FloatWritable.class);

			// set the number of reducer to 1
			job2.setNumReduceTasks(1);

			// specify input and output directories
			FileInputFormat.addInputPath(job2, new Path(args[1])); // intermediate-folder/
			job2.setInputFormatClass(KeyValueTextInputFormat.class);

			FileOutputFormat.setOutputPath(job2, new Path(args[2])); // output/
			job2.setOutputFormatClass(TextOutputFormat.class);

			return (job2.waitForCompletion(true) ? 0 : 1);
		} catch (InterruptedException | ClassNotFoundException | IOException e) {
			System.err.println("Error during mapreduce job.");
			e.printStackTrace();
			return 2;
		}
	}
}
