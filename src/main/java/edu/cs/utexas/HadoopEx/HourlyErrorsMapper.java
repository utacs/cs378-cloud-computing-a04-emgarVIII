package edu.cs.utexas.HadoopEx;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HourlyErrorsMapper extends Mapper<Object, Text, IntWritable, IntWritable> {

	// Create a counter and initialize with 1
	private final IntWritable num_errors = new IntWritable(0);
	// Create a hadoop text object to store words
	private IntWritable pickup_hour = new IntWritable(0);
	private IntWritable dropoff_hour = new IntWritable(0);

	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
		
		if (lineIsValid(value.toString())) {
			String[] tokens = value.toString().split(",");
			pickup_hour.set(Integer.parseInt(tokens[2]));
			dropoff_hour.set(Integer.parseInt(tokens[3]));
			
			num_errors.set(numErrorsInLine(tokens));
			context.write(pickup_hour, num_errors);
			if (pickup_hour.get() != dropoff_hour.get()) {
				context.write(dropoff_hour, num_errors);
			}
		}
	}

	private boolean lineIsValid(String line) {
		String[] tokens = line.toString().split(",");
		boolean tokenCount = tokens.length == 17;
		boolean gps_vals_valid = false;
		try {
			for (int i = 6; i <= 9; i++) {
				// Gps value can either be an empty string or a float
				if (!tokens[i].equals("")) {
					Float gps_val = Float.parseFloat(tokens[i]);
				}
			}
			gps_vals_valid = true;
		} catch (NumberFormatException e) {
			return false; // value is not convertible to float
		}
			
		return tokenCount && gps_vals_valid;
	}

	private int numErrorsInLine(String[] tokens) {
		int num_errors = 0;
		try {
			for (int i = 6; i <= 9; i++) {
				String gps_val = tokens[i];
				if (gps_val.equals("") || Float.parseFloat(gps_val) == 0.0) {
					num_errors++;
				}
			}
		} catch (NumberFormatException e) {

		}

		return num_errors;
	}
}