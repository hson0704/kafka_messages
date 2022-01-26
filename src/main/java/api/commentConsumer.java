package api;

import java.time.Duration;
import java.util.*;

import database.Entity.Comment;
import org.apache.kafka.clients.consumer.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.json.JSONObject;

public class commentConsumer {

    private static SessionFactory factory;
    private static KafkaConsumer<String, String> consumer;

    public static void main(String[] args) {

        startConsumer();

        startORMdb();

        //Kiểm tra và ghi dữ liệu vào database
        while (true) {
            ConsumerRecords<String, String> messages = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> message : messages) {
                Session session = factory.getCurrentSession();
                Comment tmp = new Comment(new JSONObject(message.value()));
                try {
                    session.getTransaction().begin();

                    //Check xem bản ghi đã tồn tại chưa
                    Comment record = getRecord(session, tmp.getId());
                    if (record == null) {
                        session.save(tmp);
                    } else {
                        session.evict(record);
                        int temp = record.getCount();
                        record.setCount(++temp);
                        record.setCreat_at(new Date());
                        session.update(record);
                    }

                    session.getTransaction().commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    session.getTransaction().rollback();
                }
            }
        }
    }

    //Kiểm tra xem bản ghi có trong database không
    private static Comment getRecord(Session session, String id) {
        String sql = "Select e from " + Comment.class.getName() + " e "
                + " where e.id='" + id + "'" ;
        Query<Comment> query = session.createQuery(sql);
        Comment temp;
        try {
            temp = query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return temp;
    }

    //Khởi tạo Hibernate ORM
    private static void startORMdb() {
        try {
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.out.println("Khong the khoi tao sessionFactory Object. " + ex);
        }
    }

    //Khởi tạo Consumer
    private static void startConsumer() {
        //Cấu hình Consumer
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "comment");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("auto.offset.reset", "latest");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("quickstart-events"));
    }

}
