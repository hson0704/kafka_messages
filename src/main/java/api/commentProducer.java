package api;

import java.util.*;
import Util.getData;
import org.apache.kafka.clients.producer.*;
import org.json.JSONObject;

public class commentProducer {
    public static void main(String[] args) {

        //Cấu hình Producer
        String topicName = "quickstart-events";
        Properties properties = new Properties();
        properties.put("acks", "all");
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(properties);

        List<JSONObject> objects = getData.readData();
        int length = objects.size();
        for (int i = 0; i < length; i++) {
            producer.send(new ProducerRecord<>(topicName, Integer.toString(i), objects.get(i).toString()));
        }

        producer.close();

        System.out.println("Send complete");
    }
}
