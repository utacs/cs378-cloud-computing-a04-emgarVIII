package edu.cs.utexas.HadoopEx;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;


public class TaxiAndError implements Comparable<TaxiAndError> {

        private final Text taxiID;
        private final FloatWritable error_rate;

        public TaxiAndError(Text taxiID, FloatWritable error_rate) {
            this.taxiID = taxiID;
            this.error_rate = error_rate;
        }

        public Text getTaxiID() {
            return taxiID;
        }

        public FloatWritable getErrorRate() {
            return error_rate;
        }

        /**
         * Compares two sort data objects by their value.
         * @param other
         * @return 0 if equal, negative if this < other, positive if this > other
         */
        @Override
        public int compareTo(TaxiAndError other) {
            return Float.compare(error_rate.get(), other.getErrorRate().get());
            // float diff = error_rate.get() - other.error_rate.get();
        }

        public String toString(){
            return "("+taxiID.toString() +" , "+ error_rate.toString()+")";
        }
    }
