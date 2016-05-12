package org.maxur.tutorials.rabbtmq.hello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.maxur.tutorials.rabbtmq.Config;
import org.maxur.tutorials.rabbtmq.EndPoint;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>11.05.2016</pre>
 */
@Slf4j
public final class Receiver {

    private static final String QUEUE_NAME = "hello";

    private Receiver() {
    }

    public static void main(String[] argv) {
        try (EndPoint channel = EndPoint.channel(Config.fromResource("/config.yaml").getConnection(), QUEUE_NAME)) {
            channel.basicConsume(QUEUE_NAME, true, new Consumer(channel));
            pressAnyKeyToContinue();
        }  catch (RuntimeException | IOException | TimeoutException e) {
            log.error(e.getMessage(), e);
        }
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

    private static class Consumer extends DefaultConsumer {

        Consumer(Channel channel) {
            super(channel);
        }

        /**
         * Called when consumer is registered.
         */
        @Override
        public void handleConsumeOk(String consumerTag) {
            log.info(" [*] Consumer " + consumerTag + " registered");
        }


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
    }
}
