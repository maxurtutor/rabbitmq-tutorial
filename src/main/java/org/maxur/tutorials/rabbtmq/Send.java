package org.maxur.tutorials.rabbtmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>11.05.2016</pre>
 */
public class Send {

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
            String message = "Hello World!";
            for (int i = 0; i < 10000; i++) {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
