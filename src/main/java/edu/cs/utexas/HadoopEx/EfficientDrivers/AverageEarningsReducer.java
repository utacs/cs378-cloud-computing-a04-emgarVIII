package edu.cs.utexas.HadoopEx.EfficientDrivers;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import edu.cs.utexas.HadoopEx.utils.Utils;
import edu.cs.utexas.HadoopEx.utils.FloatArrayWritable;


// (driverID -> [total_earnings, trip_duration]) --> (driverID -> money_per_in)
public class AverageEarningsReducer extends Reducer<Text, FloatArrayWritable, Text, FloatWritable> {
	public void reduce(Text key, Iterable<FloatArrayWritable> values, Context context) 
	 throws IOException, InterruptedException {
		 float total_earnings = 0.0f;
		 float total_duration = 0.0f;
		for (FloatArrayWritable val : values) {
			// check syntax here
            float[] floatArr = val.getFloatArray();
			total_earnings += floatArr[0];
			total_duration += floatArr[1];
		}
		
		float money_per_min = (total_earnings / total_duration) * 60;
		context.write(key, new FloatWritable(money_per_min));
	}
}