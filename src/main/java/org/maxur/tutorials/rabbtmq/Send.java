package org.maxur.tutorials.rabbtmq;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>11.05.2016</pre>
 */
@Slf4j
public final class Send {

    private static final String QUEUE_NAME = "hello";

    private Send() {
    }

    public static void main(String[] argv) {
        try (RabbitMQChannel channel = new RabbitMQChannel()){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            for (int i = 0; i < 10000; i++) {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                log.info(" [x] Sent '" + message + "'");
            }
        } catch (RuntimeException | IOException | TimeoutException e) {
            log.error(e.getMessage(), e);
        }
    }
}
