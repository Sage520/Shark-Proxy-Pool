package run.sage.shark;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动应用程序
 *
 * @author Sage
 * @date 2023/02/03
 */
@Slf4j
@EnableScheduling
@EnableMongoAuditing
@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(BootApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");

        log.info("\n----------------------------------------------------------\n\t" +
                "Application Shark-Proxy-Platform is running! Access URLs:\n\t" +
                "Local:    \thttp://localhost:" + port + "\n\t" +
                "External: \thttp://" + ip + ":" + port + "\n" +
                "----------------------------------------------------------");
    }
}
