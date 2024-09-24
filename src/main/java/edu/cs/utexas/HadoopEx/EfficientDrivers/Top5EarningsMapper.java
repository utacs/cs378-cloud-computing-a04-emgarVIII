package edu.cs.utexas.HadoopEx.EfficientDrivers;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import edu.cs.utexas.HadoopEx.utils.Utils;
import edu.cs.utexas.HadoopEx.utils.DataItem;
import java.util.PriorityQueue;


/**
 * Takes in the output of the AverageEarningsReducer as input
 * AverageEarningsReducer writes (driverID, money_per_min)
 */
public class Top5EarningsMapper extends Mapper<Text, Text, Text, FloatWritable> {
    private PriorityQueue<DataItem> pq;

    public void setup(Context context) {
        pq = new PriorityQueue<>();
    }

    /**
     * @param key: the taxIID
     * @param value: the error rate
     */
	public void map(Text key, Text value, Context context) {
		float money_per_min = Float.parseFloat(value.toString());

		pq.add(new DataItem(new Text(key), new FloatWritable(money_per_min)));

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