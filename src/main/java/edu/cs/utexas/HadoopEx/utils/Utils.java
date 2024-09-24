package edu.cs.utexas.HadoopEx.utils;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Utils {
	public static boolean lineIsValid(String line) {
        // 1. Check if each line has correct number of commas and is the format of each line is correct
        String[] tokens = line.split(",");
		boolean tokenCount = tokens.length == 17; 
		boolean fareFloatConvertible = false, totalAmtValid = false, lessThan500 = false, durationValid = false;

		try {
            // 2. Check if all money values (indicies 11-16) can be converted to a float number (like total amount).
			Float tripDuration = Float.parseFloat(tokens[4]);
            Float fare_amt = Float.parseFloat(tokens[11]);
            Float surcharge = Float.parseFloat(tokens[12]);
            Float mtaTax = Float.parseFloat(tokens[13]);
            Float tip = Float.parseFloat(tokens[14]);
            Float tolls = Float.parseFloat(tokens[15]);
            Float total_amt = Float.parseFloat(tokens[16]);
            fareFloatConvertible = true;
            
            Float sum = fare_amt + surcharge + mtaTax + tip + tolls;
            Float epsilon = 0.001f;

            // 3. Check if the total amount of each line is correct total sum of other amounts. For example, the provided
            totalAmtValid = Math.abs((total_amt - sum)) <= epsilon;
            // 4. Remove all Taxi rides (data lines) that have a total amount bigger than 500 USD
            lessThan500 = total_amt < 500;
			durationValid = tripDuration > 0;
        } catch (Exception e) {
            // System.out.println("Exception happened: " + e);
            fareFloatConvertible = false;
        }
		return tokenCount && fareFloatConvertible && totalAmtValid && lessThan500 && durationValid;
    }

	public static int numErrorsInLine(String[] tokens) {
		int num_errors = 0;
		try {
			for (int i = 6; i <= 9; i++) {
				String gps_val = tokens[i];
				if (gps_val.equals("") || Float.parseFloat(gps_val) == 0.0) {
					num_errors++;
				}
			}
		} catch (NumberFormatException e) {

		}

		return num_errors;
	}

	/**
	 * @param dateTime: Index 2/3 of previous csv line
     *    	represented as "2013-01-01 00:00:00"
	 * 
	 */
	public static int extractHoursFromDate(String dateTime) {
		String[] tokens = dateTime.split(" ");
		String hours = tokens[1].split(":")[0];
		return Integer.parseInt(hours);
	}
}