package com.spnotes.kafka.consumer;

public class ConsumerGroupTest {
    final static String TOPIC = "psntopic";
    final static String ZOOKEEPER = "localhost:2181";
    final static String GROUPID = "psngroup";
    final static int NUM_OF_THREADS = 2;

    public static void main(String[] args) {
        ConsumerGroup example = new ConsumerGroup(ZOOKEEPER, GROUPID, TOPIC);
        example.run(NUM_OF_THREADS);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {

        }
        //example.shutdown();
    }
}
