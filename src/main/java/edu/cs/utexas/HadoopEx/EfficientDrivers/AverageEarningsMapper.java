package edu.cs.utexas.HadoopEx.EfficientDrivers;

import java.io.IOException;

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

		if (value != null && !value.toString().trim().isEmpty()) {
			String[] tokens = value.toString().split(",");

			// extra safety check
			if (tokens.length > 16 && Utils.lineIsValid(value.toString())) {
				try {
					driverID.set(tokens[1]);
					total_earnings.set(Float.parseFloat(tokens[16]));
					trip_duration.set(Float.parseFloat(tokens[4]));

					FloatWritable[] writables = new FloatWritable[2];
					writables[0] = total_earnings;
					writables[1] = trip_duration;

					FloatArrayWritable arrayWritable = new FloatArrayWritable();
					arrayWritable.set(writables);

					// outpt key-value pair (driverID, [total_earnings, trip_duration])
					context.write(driverID, arrayWritable);
				} catch(Exception e) {
					System.out.println("EXCEPTION IN AverageEarningsMapper.java "+ e);
				}
			}
		}
	}
}