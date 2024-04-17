package com.cards;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class TaxiTimesMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        System.out.println("VALUE:");
        System.out.println(value);

        String[] fields = value.toString().split(",");

        String Origin = fields[16];
        String Dest = fields[17];

        // Taxi-In Time --> Destination
        // The time elapsed between wheels down and arrival at the destination airport gate.
        Integer TaxiIn = Integer.parseInt(fields[19]);

        // Taxi-Out Time --> Origin
        // The time elapsed between departure from the origin airport gate and wheels off.
        Integer TaxiOut = Integer.parseInt(fields[20]);

        context.write(new Text(Origin+"|ORIGIN"), new IntWritable(TaxiOut));
        context.write(new Text(Dest+"|DEST"), new IntWritable(TaxiIn));
    }
}
