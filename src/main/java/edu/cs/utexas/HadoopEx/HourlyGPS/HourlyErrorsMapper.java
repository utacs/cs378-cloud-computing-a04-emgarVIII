package edu.cs.utexas.HadoopEx.HourlyGPS;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import edu.cs.utexas.HadoopEx.utils.Utils;

public class HourlyErrorsMapper extends Mapper<Object, Text, IntWritable, IntWritable> {

	// Create a counter and initialize with 1
	private final IntWritable num_errors = new IntWritable(0);
	// Create a hadoop text object to store words
	private IntWritable pickup_hour = new IntWritable(0);
	private IntWritable dropoff_hour = new IntWritable(0);

	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
		
		if (Utils.lineIsValid(value.toString())) {
			String[] tokens = value.toString().split(",");
			
			// Extract the pickup and dropoff hours
			pickup_hour.set(Utils.extractHoursFromDate(tokens[2]));
			dropoff_hour.set(Utils.extractHoursFromDate(tokens[3]));

			num_errors.set(Utils.numErrorsInLine(tokens));
			context.write(pickup_hour, num_errors);
			if (pickup_hour.get() != dropoff_hour.get()) {
				context.write(dropoff_hour, num_errors);
			}
		}
	}
}