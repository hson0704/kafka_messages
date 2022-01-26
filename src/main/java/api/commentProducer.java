package api;

import java.util.*;
import Util.getData;
import org.apache.kafka.clients.producer.*;
import org.json.JSONObject;

public class commentProducer {

    private static final String topicName = "quickstart-events";
    private static Producer<String, String> producer;

    public static void main(String[] args) {

        startProducer();

        //Đọc dữ liệu từ server
        List<JSONObject> objects = getData.readData();

        //Gửi dữ liệu đã đọc vào topic
        int length = objects.size();
        for (int i = 0; i < length; i++) {
            producer.send(new ProducerRecord<>(topicName, Integer.toString(i), objects.get(i).toString()));
        }

        producer.close();

        System.out.println("Send complete");
    }

    //Khởi tạo Producer
    private static void startProducer() {
        //Cấu hình Producer
        Properties properties = new Properties();
        properties.put("acks", "all");
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(properties);
    }
}
