package com.cards;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Comparator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class OnScheduleReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {

    // Define a max-heap (priority queue) to keep track of the top three keys with the largest values
    private PriorityQueue<KeyValue> maxHeap = new PriorityQueue<>(3, new Comparator<KeyValue>() {
        @Override
        public int compare(KeyValue kv1, KeyValue kv2) {
            // Compare values (percentages) in descending order
            return Double.compare(kv1.value, kv2.value);
        }
    });

    // Define a custom class to hold key-value pairs
    private static class KeyValue {
        Text key;
        double value;

        KeyValue(Text key, double value) {
            this.key = key;
            this.value = value;
        }
    }

    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // Agg and sum up how many of each we found
        int lates = 0;
        int total = 0;
        for (IntWritable val : values) {
            total++;
            lates += val.get();
        }
        double percentage = (double) lates / total;
        // Add the key-value pair to the max-heap
        maxHeap.add(new KeyValue(new Text(key), percentage));

        // If the heap size exceeds three, remove the element with the smallest value
        if (maxHeap.size() > 3) {
            maxHeap.poll();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // Emit the top three keys with the largest percentages (late flights)
        while (!maxHeap.isEmpty()) {
            KeyValue kv = maxHeap.poll();
            context.write(kv.key, new DoubleWritable(kv.value));
        }
    }

}
