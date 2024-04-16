package com.cards;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CardReducer extends Reducer<Text, IntWritable, Text, NullWritable> {

    // private Connection connection;
    // private Table table;

    // // @Override
    // protected void setup(Context context) throws IOException, InterruptedException {
    //     Configuration config = HBaseConfiguration.create();
    //     // Assuming your HBase is configured to auto-discover this might be all you need
    //     // Otherwise, you may need to specify more about how to connect to HBase/Zookeeper
    //     connection = ConnectionFactory.createConnection(config);
    //     table = connection.getTable(TableName.valueOf("your_table_name")); // Specify your table name here
    // }

    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // Agg and sum up how many of each we found
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }

        // Write out those w/ zero counts
        if (sum>0){
            System.out.println("Found Card "+key);
        // Potnetial elif for if more than 1
        } else {
            System.out.println("Missing Card "+key);
            context.write(key, NullWritable.get());
            // Example HBase Put operation
            // Put put = new Put(Bytes.toBytes(key.toString())); // Using card name as row key
            // put.addColumn(Bytes.toBytes("cards"), Bytes.toBytes("details"), Bytes.toBytes("Missing")); // Adjust column family and qualifier as needed
            // table.put(put);
        }

    }

    // @Override
    // protected void cleanup(Context context) throws IOException, InterruptedException {
    //     if (table != null) {
    //         table.close();
    //     }
    //     if (connection != null) {
    //         connection.close();
    //     }
    // }
}
