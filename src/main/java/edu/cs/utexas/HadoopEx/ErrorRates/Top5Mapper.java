package edu.cs.utexas.HadoopEx.ErrorRates;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import edu.cs.utexas.HadoopEx.utils.Utils;
import edu.cs.utexas.HadoopEx.utils.DataItem;
import java.util.PriorityQueue;


/**
 * Takes in the output of the ErrorRateReducer as input
 * ErrorRateReducer writes (TaxiID, ErrorRate)
 */
public class Top5Mapper extends Mapper<Text, Text, Text, FloatWritable> {
    private PriorityQueue<DataItem> pq;

    public void setup(Context context) {
        pq = new PriorityQueue<>();
    }

    /**
     * @param key: the id
     * @param value: the error rate
     */
	public void map(Text key, Text value, Context context) {
		float error_rate = Float.parseFloat(value.toString());

		pq.add(new DataItem(new Text(key), new FloatWritable(error_rate)));

		if (pq.size() > 5) {
			pq.poll();
		}
	}
	
	public void cleanup(Context context) throws IOException, InterruptedException {
		while (pq.size() > 0) {
			DataItem DataItem = pq.poll();
			context.write(DataItem.getID(), DataItem.getValue());
		}
	}
}