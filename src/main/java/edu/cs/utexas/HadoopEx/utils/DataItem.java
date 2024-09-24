package edu.cs.utexas.HadoopEx.utils;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;


public class DataItem implements Comparable<DataItem> {

        private final Text id;
        private final FloatWritable value;

        public DataItem(Text id, FloatWritable value) {
            this.id = id;
            this.value = value;
        }

        public Text getID() {
            return id;
        }

        public FloatWritable getValue() {
            return value;
        }

        /**
         * Compares two sort data objects by their value.
         * @param other
         * @return 0 if equal, negative if this < other, positive if this > other
         */
        @Override
        public int compareTo(DataItem other) {
            return Float.compare(value.get(), other.getValue().get());
            // float diff = error_rate.get() - other.error_rate.get();
        }

        public String toString(){
            return "("+id.toString() +" , "+ value.toString()+")";
        }
    }
