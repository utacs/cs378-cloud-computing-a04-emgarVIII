package edu.cs.utexas.HadoopEx.EfficientDrivers;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Iterator;

import edu.cs.utexas.HadoopEx.utils.DataItem;


/**
 * Extracts top 5 taxiID's based on their error rates.
 */
public class Top5EarningsReducer extends  Reducer<Text, FloatWritable, Text, FloatWritable> {

    private PriorityQueue<DataItem> pq = new PriorityQueue<DataItem>(5);
    // private Logger logger = Logger.getLogger(Top5EarningsReducer.class);

    /**
     * Takes in the top 5 from each mapper and calculates the overall top 5
     * @param text
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
   public void reduce(Text key, Iterable<FloatWritable> values, Context context)
           throws IOException, InterruptedException {

       for (FloatWritable money_per_min : values) {
           pq.add(new DataItem(new Text(key), new FloatWritable(money_per_min.get())));
       }

       // keep the priorityQueue size <= heapSize
       while (pq.size() > 10) {
           pq.poll();
       }
   }


    public void cleanup(Context context) throws IOException, InterruptedException {
        List<DataItem> values = new ArrayList<DataItem>(10);

        while (pq.size() > 0) {
            values.add(pq.poll());
        }

        // reverse so they are ordered in descending order
        Collections.reverse(values);

        for (DataItem value : values) {
            context.write(value.getID(), value.getValue());
        }
    }

}