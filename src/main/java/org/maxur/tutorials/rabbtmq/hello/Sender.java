package org.maxur.tutorials.rabbtmq.hello;

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
public final class Sender {

    private static final String QUEUE_NAME = "hello";

    private Sender() {
    }

    public static void main(String[] argv) {
        try (EndPoint channel = EndPoint.channel(Config.fromResource("/config.yaml").getConnection(), QUEUE_NAME)) {
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
