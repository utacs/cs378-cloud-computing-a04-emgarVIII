package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import edu.cs.utexas.HadoopEx.Utils.*;


// (TaxiID, Num errors) -> (TaxiID, Error Rate)
class ErrorRateReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {
	public void reduce(Text text, Iterable<IntWritable> values, Context context) 
    throws IOException, InterruptedException {

		float total_errors = 0.0f;
		float total_data_entries = 0.0f;
		for (IntWritable num_errors : values) {
			total_errors += num_errors.get();
			total_data_entries += 4; // there are 4 total GPS data points
		}
		
		float ratio = total_errors / total_data_entries;
		context.write(text, new FloatWritable(ratio));
	}
}