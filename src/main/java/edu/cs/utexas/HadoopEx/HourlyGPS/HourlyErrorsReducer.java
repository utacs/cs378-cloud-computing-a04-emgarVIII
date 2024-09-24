package edu.cs.utexas.HadoopEx.HourlyGPS;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HourlyErrorsReducer extends  Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

   public void reduce(IntWritable hour, Iterable<IntWritable> values, Context context)
           throws IOException, InterruptedException {
	   
       int total_errors = 0;
       
       for (IntWritable num_errors : values) {
           total_errors += num_errors.get();
       }
       
       context.write(hour, new IntWritable(total_errors));
   }
}