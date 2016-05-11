package org.maxur.tutorials.rabbtmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>11.05.2016</pre>
 */
public class Recv {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws IOException, TimeoutException {
        Channel channel = null;
        Connection connection = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("docker.local");
            factory.setPort(32776);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(
                        String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body
                ) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);

        } finally {
/*            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }*/
        }
    }
}
