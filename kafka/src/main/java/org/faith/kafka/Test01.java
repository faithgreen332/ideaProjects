package org.faith.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Test01 {

    @Test
    public void producer() {
        // 用 properties 的方式给 producer 实例
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092,node2:9092,node3:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "-1");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        String topic = "faith-items";
//        while (true) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, "item" + j, "val" + i);
                Future<RecordMetadata> send = producer.send(producerRecord);

                try {
                    RecordMetadata recordMetadata = send.get();
                    int partition = recordMetadata.partition();
                    long offset = recordMetadata.offset();

                    System.out.println("key:" + producerRecord.key() + " val:" + producerRecord.value() + " partition:" + partition + " offset:" + offset);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
//        }
    }

    @Test
    public void consumer() {

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092,node2:9092,node3:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "xxoo");

        // "earliest" 让 consumer 第一次启动就从 offset 从头取消息
        // "lastest" 让 consumer 第一次启动从当前的 offset 开始取消息
        // "none" 如果当前的 offset 没有，就抛出异常
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 让 consumer 自动提交 offset
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 让 offset 过 5 秒后再提交
//        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");

        // 一次让 consumer poll 多少 records
//        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        String topic = "faith-items";
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            // 批量获取
            ConsumerRecords<String, String> records = consumer.poll(Duration.ZERO);
            if (!records.isEmpty()) {
                Set<TopicPartition> partitions = records.partitions();
                for (TopicPartition partition : partitions) {
                    // 按分区批量获取
                    final List<ConsumerRecord<String, String>> pRecords = records.records(partition);

                    Iterator<ConsumerRecord<String, String>> iterator = pRecords.iterator();
                    while (iterator.hasNext()) {
                        ConsumerRecord<String, String> next = iterator.next();
                        String key = next.key();
                        String value = next.value();
                        int partition1 = next.partition();
                        long offset = next.offset();

                        System.out.println("key: " + key + " value: " + value + " partition: " + partition1 + " offset:" + offset);
                    }
                }
            }
        }
    }


    /**
     * 手动维护 offset ，批量按照 partition 维护
     */
    @Test
    public void consumerPartitionCommit() {

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092,node2:9092,node3:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "xxoo");

        // "earliest" 让 consumer 第一次启动就从 offset 从头取消息
        // "lastest" 让 consumer 第一次启动从当前的 offset 开始取消息
        // "none" 如果当前的 offset 没有，就抛出异常
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 让 consumer 自动提交 offset
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 让 offset 过 5 秒后再提交
//        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");

        // 一次让 consumer poll 多少 records
//        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        String topic = "faith-items";
        consumer.subscribe(Arrays.asList(topic));


        while (true) {
            // 批量获取
            ConsumerRecords<String, String> records = consumer.poll(Duration.ZERO);
            if (!records.isEmpty()) {
                Set<TopicPartition> partitions = records.partitions();
                for (TopicPartition partition : partitions) {
                    // 按分区批量获取
                    final List<ConsumerRecord<String, String>> pRecords = records.records(partition);

                    Iterator<ConsumerRecord<String, String>> iterator = pRecords.iterator();
                    while (iterator.hasNext()) {
                        ConsumerRecord<String, String> next = iterator.next();
                        String key = next.key();
                        String value = next.value();
                        int partition1 = next.partition();
                        long offset = next.offset();

                        System.out.println("key: " + key + " value: " + value + " partition: " + partition1 + " offset:" + offset);
                    }
                    long offset = pRecords.get(pRecords.size() - 1).offset();
                    OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(offset);
                    HashMap<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                    map.put(partition, offsetAndMetadata);
                    consumer.commitSync(map);
                }
            }
        }
    }

    /**
     * 手动维护 offset ，批量按照批量维护
     */
    @Test
    public void consumerRecordsCommit() {

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092,node2:9092,node3:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "xxoo");

        // "earliest" 让 consumer 第一次启动就从 offset 从头取消息
        // "lastest" 让 consumer 第一次启动从当前的 offset 开始取消息
        // "none" 如果当前的 offset 没有，就抛出异常
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 让 consumer 自动提交 offset
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 让 offset 过 5 秒后再提交
//        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");

        // 一次让 consumer poll 多少 records
//        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        String topic = "faith-items";
        consumer.subscribe(Arrays.asList(topic));


        while (true) {
            // 批量获取
            ConsumerRecords<String, String> records = consumer.poll(Duration.ZERO);
            if (!records.isEmpty()) {
                Set<TopicPartition> partitions = records.partitions();
                for (TopicPartition partition : partitions) {
                    // 按分区批量获取
                    final List<ConsumerRecord<String, String>> pRecords = records.records(partition);

                    Iterator<ConsumerRecord<String, String>> iterator = pRecords.iterator();
                    while (iterator.hasNext()) {
                        ConsumerRecord<String, String> next = iterator.next();
                        String key = next.key();
                        String value = next.value();
                        int partition1 = next.partition();
                        long offset = next.offset();

                        System.out.println("key: " + key + " value: " + value + " partition: " + partition1 + " offset:" + offset);
                    }

                }
                consumer.commitSync();
            }
        }
    }
}
