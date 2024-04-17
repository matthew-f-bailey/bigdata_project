package com.cards;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TaxiTimesDriver {
    public static void main(String[] args) throws Exception {

        Path input = new Path("/input");
        Path output = new Path("/output");

        System.out.println("!!!!!!!!!!!!! INTO MAPREDUCE !!!!");
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(output)) {
            fs.delete(output, true); // true to delete recursively
            System.out.println("Path deleted.");
        } else {
            System.out.println("Path does not exist.");
        }

        if (otherArgs.length < 2) {
            System.err.println("Usage: cardfinder <in> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "Missing Cards Finder");
        job.setJarByClass(TaxiTimesDriver.class);
        job.setMapperClass(TaxiTimesMapper.class);
        job.setReducerClass(TaxiTimesReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);

        // Execute the job and wait for it to complete
        boolean success = job.waitForCompletion(true);

        if (success) {
            System.out.println("Job completed successfully");

            // Read data from the output path (optional)
            FSDataInputStream inputStream = fs.open(output);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Output: " + line);
            }

            reader.close();
        } else {
            System.out.println("Job failed");
        }

        // Exit the program with the appropriate status code
        System.exit(success ? 0 : 1);
    }
}
