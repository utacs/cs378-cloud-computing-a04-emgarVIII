package edu.cs.utexas.HadoopEx.ErrorRates;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import edu.cs.utexas.HadoopEx.utils.Utils;

// Data entry --> (TaxiID, Num Errors)
public class ErrorRateMapper extends Mapper<Object, Text, Text, IntWritable> {
	private IntWritable num_errors = new IntWritable(0);
	private Text taxiID = new Text();
	
	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
		
		if (Utils.lineIsValid(value.toString())) {
			String[] tokens = value.toString().split(",");
			taxiID.set(tokens[0]);
			num_errors.set(Utils.numErrorsInLine(tokens));

			context.write(taxiID, num_errors);
		}
	}
}