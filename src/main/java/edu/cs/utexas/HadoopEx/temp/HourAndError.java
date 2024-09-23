package edu.cs.utexas.HadoopEx;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;


public class HourAndError implements Comparable<HourAndError> {

        private final IntWritable hour;
        private final IntWritable num_errors;

        public HourAndError(IntWritable hour, IntWritable num_errors) {
            this.hour = hour;
            this.num_errors = num_errors;
        }

        public IntWritable getHour() {
            return hour;
        }

        public IntWritable getNumErrors() {
            return num_errors;
        }

        /**
         * Compares two sort data objects by their value.
         * @param other
         * @return 0 if equal, negative if this < other, positive if this > other
         */
        @Override
        public int compareTo(HourAndError other) {
            float diff = num_errors.get() - other.num_errors.get();
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            }
            return 0;
        }

        public String toString(){

            return "("+hour.toString() +" , "+ num_errors.toString()+")";
        }
    }
