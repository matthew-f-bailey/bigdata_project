package com.cards;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class OnScheduleMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private ObjectMapper mapper = new ObjectMapper();

    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] fields = value.toString().split(",");
        Integer Year = Integer.parseInt(fields[0]);
        Integer Month = Integer.parseInt(fields[1]);
        Integer DayofMonth = Integer.parseInt(fields[2]);
        Integer DayOfWeek = Integer.parseInt(fields[3]);
        Integer DepTime = Integer.parseInt(fields[4]);
        Integer CRSDepTime = Integer.parseInt(fields[5]);
        Integer ArrTime = Integer.parseInt(fields[6]);
        Integer CRSArrTime = Integer.parseInt(fields[7]);
        String UniqueCarrier = fields[8];
        Integer FlightNum = Integer.parseInt(fields[9]);
        String TailNum = fields[10];
        Integer ActualElapsedTime = Integer.parseInt(fields[11]);
        Integer CRSElapsedTime = Integer.parseInt(fields[12]);
        Integer AirTime = Integer.parseInt(fields[13]);
        Integer ArrDelay = Integer.parseInt(fields[14]);
        Integer DepDelay = Integer.parseInt(fields[15]);
        String Origin = fields[16];
        String Dest = fields[17];
        Integer Distance = Integer.parseInt(fields[18]);
        Integer TaxiIn = Integer.parseInt(fields[19]);
        Integer TaxiOut = Integer.parseInt(fields[20]);
        Integer Cancelled = Integer.parseInt(fields[21]);
        Integer CancellationCode = Integer.parseInt(fields[22]);
        Integer Diverted = Integer.parseInt(fields[23]);
        Integer CarrierDelay = Integer.parseInt(fields[24]);
        Integer WeatherDelay = Integer.parseInt(fields[25]);
        Integer NASDelay = Integer.parseInt(fields[26]);
        Integer SecurityDelay = Integer.parseInt(fields[27]);
        Integer LateAircraftDelay = Integer.parseInt(fields[28]);

        if (ArrDelay > 10){
            System.out.print(UniqueCarrier);
            context.write(new Text(UniqueCarrier), new IntWritable(1));
        }
    }
}
