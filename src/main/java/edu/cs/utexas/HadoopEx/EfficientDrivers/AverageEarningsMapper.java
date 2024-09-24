package edu.cs.utexas.HadoopEx.EfficientDrivers;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import edu.cs.utexas.HadoopEx.utils.Utils;
import edu.cs.utexas.HadoopEx.utils.FloatArrayWritable;

// DataEntry -> (driverID -> [total_earnings, trip_duration])
public class AverageEarningsMapper extends Mapper<Object, Text, Text,  FloatArrayWritable> {
	private FloatWritable total_earnings = new FloatWritable(0);
	private FloatWritable trip_duration = new FloatWritable(0);
	private Text driverID = new Text();
			
	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
			
            String[] tokens = null;
			if (Utils.lineIsValid(value.toString()))
				tokens = value.toString().split(",");
				driverID.set(tokens[1]);
				total_earnings.set(Float.parseFloat(tokens[16]));
				trip_duration.set(Float.parseFloat(tokens[4])); // Trip time in secs
				
				// make array of FloatWritable
				FloatWritable[] writables = new FloatWritable[2];
				writables[0] = new FloatWritable(total_earnings.get());
				writables[1] = new FloatWritable(trip_duration.get());
				
				// make instance of custom FloatArrayWritable class and set it with the array we made above
				FloatArrayWritable arrayWritable = new FloatArrayWritable();
				arrayWritable.set(writables);
				
				
				context.write(driverID, arrayWritable); // Look into how to correctly initialize ArrayWritable
	}
} 