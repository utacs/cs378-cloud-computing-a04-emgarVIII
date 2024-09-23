package edu.cs.utexas.HadoopEx;

import org.apache.hadoop.io.IntWritable;
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



public class HourlyTop5 extends  Reducer<Text, IntWritable, IntWritable, IntWritable> {

    private PriorityQueue<HourAndError> pq = new PriorityQueue<HourAndError>(5);
    private Logger logger = Logger.getLogger(HourlyTop5.class);

//    public void reduce(Text key, Iterable<IntWritable> values, Context context)
//            throws IOException, InterruptedException {


//        // A local counter just to illustrate the number of values here!
//         int counter = 0 ;

//        // size of values is 1 because key only has one distinct value
//        for (IntWritable value : values) {
//            counter = counter + 1;
//            logger.info("Reducer Text: counter is " + counter);
//            logger.info("Reducer Text: Add this item  " + new HourAndError(key, value).toString());

//            pq.add(new HourAndError(new Text(key), new IntWritable(value.get()) ) );

//            logger.info("Reducer Text: " + key.toString() + " , Count: " + value.toString());
//            logger.info("PQ Status: " + pq.toString());
//        }

//        // keep the priorityQueue size <= heapSize
//        while (pq.size() > 5) {
//            pq.poll();
//        }
//    }
    /**
     * Takes in the topK from each mapper and calculates the overall topK
     * @param hour
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    public void reduce(IntWritable hour, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {


        // A local counter just to illustrate the number of values here!
            int counter = 0 ;

        // size of values is 1 because key only has one distinct value
        for (IntWritable value : values) {
            counter = counter + 1;
            logger.info("Reducer Text: counter is " + counter);
            logger.info("Reducer Text: Add this item  " + new HourAndError(hour, value).toString());

            pq.add(new HourAndError(new IntWritable(hour.get()), new IntWritable(value.get()) ) );

            logger.info("Reducer Text: " + hour.toString() + " , Count: " + value.toString());
            logger.info("PQ Status: " + pq.toString());
        }

        // keep the priorityQueue size <= heapSize
        while (pq.size() > 5) {
            pq.poll();
        }
    }


    public void cleanup(Context context) throws IOException, InterruptedException {
        logger.info("TopKReducer cleanup cleanup.");
        logger.info("pq.size() is " + pq.size());

        List<HourAndError> values = new ArrayList<HourAndError>(5);

        while (pq.size() > 0) {
            values.add(pq.poll());
        }

        logger.info("values.size() is " + values.size());
        logger.info(values.toString());

        // reverse so they are ordered in descending order
        Collections.reverse(values);

        for (HourAndError value : values) {
            context.write(value.getHour(), value.getNumErrors());
            logger.info("TopKReducer - Top-10 Words are:  " + value.getHour() + "  Count:"+ value.getNumErrors());
        }
    }
}