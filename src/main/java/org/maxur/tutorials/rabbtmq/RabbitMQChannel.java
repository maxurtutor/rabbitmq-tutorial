package org.maxur.tutorials.rabbtmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>12.05.2016</pre>
 */
@Slf4j
class RabbitMQChannel implements Closeable {

    @Delegate(excludes = Closeable.class)
    private Channel channel;

    private Connection connection;

    RabbitMQChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("docker.local");
        factory.setPort(32769);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    @Override
    public void close() throws IOException {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            log.error(e.getMessage(), e);
        }
    }

    Channel get() {
        return channel;
    }
}
