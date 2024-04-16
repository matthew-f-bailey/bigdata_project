package com.cards;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class CardDriver {
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
        job.setJarByClass(CardDriver.class);
        job.setMapperClass(CardMapper.class);
        job.setReducerClass(CardReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
