package com.cards;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Comparator;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TaxiTimesReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {

    // PriorityQueues to track the top 3 airports for each condition
    private PriorityQueue<Airport> shortestDestinations;
    private PriorityQueue<Airport> longestDestinations;
    private PriorityQueue<Airport> shortestOrigins;
    private PriorityQueue<Airport> longestOrigins;

    // Initialization of the PriorityQueues
    @Override
    protected void setup(Context context) {
        shortestDestinations = new PriorityQueue<>(Comparator.comparingDouble(Airport::getAvgTime));
        longestDestinations = new PriorityQueue<>(3, (a1, a2) -> Double.compare(a2.getAvgTime(), a1.getAvgTime()));
        shortestOrigins = new PriorityQueue<>(Comparator.comparingDouble(Airport::getAvgTime));
        longestOrigins = new PriorityQueue<>(3, (a1, a2) -> Double.compare(a2.getAvgTime(), a1.getAvgTime()));
    }

    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // Aggregate and sum up how many values we found
        int totalMinutes = 0;
        int count = 0;
        for (IntWritable val : values) {
            count++;
            totalMinutes += val.get();
        }
        double averageTime = (double) totalMinutes / count;

        // Split the key into airport and type (origin or destination)
        String[] fields = key.toString().split("\\|");
        String airport = fields[0];
        boolean isDestination = "DEST".equals(fields[1]);

        // Create an airport object
        Airport airportObj = new Airport(airport, averageTime);

        // Track the top 3 airports based on each condition
        if (isDestination) {
            updateTop3List(shortestDestinations, longestDestinations, airportObj, true);
        } else {
            updateTop3List(shortestOrigins, longestOrigins, airportObj, false);
        }
    }

    private void updateTop3List(PriorityQueue<Airport> shortestPQ, PriorityQueue<Airport> longestPQ, Airport airportObj, boolean isDestination) {
        // Update the shortest priority queue
        shortestPQ.add(airportObj);
        if (shortestPQ.size() > 3) {
            shortestPQ.poll();
        }

        // Update the longest priority queue
        longestPQ.add(airportObj);
        if (longestPQ.size() > 3) {
            longestPQ.poll();
        }
    }

    // Custom Airport class to represent an airport with an average time
    static class Airport {
        private final String name;
        private final double avgTime;

        Airport(String name, double avgTime) {
            this.name = name;
            this.avgTime = avgTime;
        }

        public double getAvgTime() {
            return avgTime;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Airport airport = (Airport) obj;
            return name.equals(airport.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    // Clean up code to output results and other final tasks
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // Output top 3 shortest destinations
        context.write(new Text("Top 3 shortest average time at destination airports:"), new DoubleWritable(0.0));
        while (!shortestDestinations.isEmpty()) {
            Airport airport = shortestDestinations.poll();
            context.write(new Text(airport.name), new DoubleWritable(airport.avgTime));
        }

        // Output top 3 longest destinations
        context.write(new Text("Top 3 longest average time at destination airports:"),  new DoubleWritable(0.0));
        while (!longestDestinations.isEmpty()) {
            Airport airport = longestDestinations.poll();
            context.write(new Text(airport.name), new DoubleWritable(airport.avgTime));
        }

        // Output top 3 shortest origins
        context.write(new Text("Top 3 shortest average time at origin airports:"), new DoubleWritable(0.0));
        while (!shortestOrigins.isEmpty()) {
            Airport airport = shortestOrigins.poll();
            context.write(new Text(airport.name), new DoubleWritable(airport.avgTime));
        }

        // Output top 3 longest origins
        context.write(new Text("Top 3 longest average time at origin airports:"),  new DoubleWritable(0.0));
        while (!longestOrigins.isEmpty()) {
            Airport airport = longestOrigins.poll();
            context.write(new Text(airport.name), new DoubleWritable(airport.avgTime));
        }
    }
}
