package org.maxur.tutorials.rabbtmq;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>5/12/2016</pre>
 */
@Data
public class Config {

    private String version;

    private Date released;

    private ConnectionConfig connection;

    private QueueConfig queue;

    public static Config fromResource(final String name) throws IOException {
        try(InputStream in = ClassLoader.class.getResourceAsStream(name) ) {
            return new Yaml().loadAs(in, Config.class);
        }
    }
}
