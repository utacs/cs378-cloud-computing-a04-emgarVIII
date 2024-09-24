package edu.cs.utexas.HadoopEx.utils;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.FloatWritable;

import org.apache.hadoop.io.Writable;


public class FloatArrayWritable extends ArrayWritable {
	 public FloatArrayWritable() {
        super(FloatWritable.class);
    }

    // Get float array
    public float[] getFloatArray() {
        Writable[] writableArray = get();
        float[] floatArray = new float[writableArray.length];

        for (int i = 0; i < writableArray.length; i++) {
            floatArray[i] = ((FloatWritable) writableArray[i]).get();
        }
        return floatArray;
    }
}