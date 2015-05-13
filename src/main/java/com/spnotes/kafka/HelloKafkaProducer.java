package com.spnotes.kafka;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Created by user on 8/4/14.
 */
public class HelloKafkaProducer {
    final static String TOPIC = "testconsole";

    public static void main(String[] argv) {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig producerConfig = new ProducerConfig(properties);
        Producer<String, String> producer = new Producer<String, String>(producerConfig);
        SimpleDateFormat sdf = new SimpleDateFormat();
        KeyedMessage<String, String> message = new KeyedMessage<String, String>(TOPIC,
                "Test message from java program " + sdf.format(new Date()));
        producer.send(message);
        producer.close();
    }
}
