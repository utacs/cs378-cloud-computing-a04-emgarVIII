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

		if (Utils.lineIsValid(value.toString())) {
			try {
				// split  CSV line into tokens
				String[] tokens = value.toString().split(",");

				// set driverID to 2nd col
				driverID.set(tokens[1]);

				total_earnings.set(Float.parseFloat(tokens[16])); // column 16
				trip_duration.set(Float.parseFloat(tokens[4]));   // column 4 (secs)

				FloatWritable[] writables = new FloatWritable[] {
						new FloatWritable(total_earnings.get()),
						new FloatWritable(trip_duration.get())
				};

				FloatArrayWritable arrayWritable = new FloatArrayWritable();
				arrayWritable.set(writables);

				// add driverID and FloatArrayWritable to the context
				context.write(driverID, arrayWritable);

			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				// just in case
				System.err.println("ERROr processing line: " + value.toString() + " // Type of error: " + e.toString());
			}
		}
	}
} 