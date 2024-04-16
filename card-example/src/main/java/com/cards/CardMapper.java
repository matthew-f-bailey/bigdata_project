package com.cards;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class CardMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private ObjectMapper mapper = new ObjectMapper();

    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // Write all zeros
        for (String s: SUITS) {
            for (String val: VALUES) {
                context.write(
                    new Text(s +":"+ val),
                    new IntWritable(0)
                );
            }
        }

        // Write out 1s to be summed up in reducer
        JsonNode rootNode = mapper.readTree(value.toString());
        for (JsonNode node : rootNode) {
            String suit = node.path("suit").asText();
            String cardValue = node.path("value").asText();
            String card = suit + ":" + cardValue;
            context.write(new Text(card), new IntWritable(1));
        }

    }
}
