package org.maxur.tutorials.rabbtmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>11.05.2016</pre>
 */
@Slf4j
public final class Recv {

    private static final String QUEUE_NAME = "hello";

    private Recv() {
    }

    public static void main(String[] argv) {
        try (RabbitMQChannel channel = new RabbitMQChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicConsume(QUEUE_NAME, true, makeConsumer(channel));
            pressAnyKeyToContinue();
        }  catch (RuntimeException | IOException | TimeoutException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static DefaultConsumer makeConsumer(final RabbitMQChannel channel) {
        return new DefaultConsumer(channel.get()) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body
            ) throws IOException {
                String message = new String(body, "UTF-8");
                log.info(" [x] Received '" + message + "'");
            }
        };
    }

    private static void pressAnyKeyToContinue() {
        log.info(" [*] Waiting for messages. To exit press any key");
        try {
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
