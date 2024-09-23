package edu.cs.utexas.HadoopEx;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import edu.cs.utexas.HadoopEx.Utils;
import java.util.PriorityQueue;

/**
 * Takes in the output of the ErrorRateReducer as input
 * ErrorRateReducer writes (TaxiID, ErrorRate)
 */
class Top5Mapper extends Mapper<Text, Text, Text, FloatWritable> {
    private PriorityQueue<TaxiAndError> pq;

    public void setup(Context context) {
        pq = new PriorityQueue<>();
    }

    /**
     * @param key: the taxIID
     * @param value: the error rate
     */
	public void map(Text key, Text value, Context context) {
		float error_rate = Float.parseFloat(value.toString());

		pq.add(new TaxiAndError(new Text(key), new FloatWritable(error_rate)));

		if (pq.size() > 5) {
			pq.poll();
		}
	}
	
	public void cleanup(Context context) throws IOException, InterruptedException {
		while (pq.size() > 0) {
			TaxiAndError taxiAndError = pq.poll();
			context.write(taxiAndError.getTaxiID(), taxiAndError.getErrorRate());
		}
	}
}