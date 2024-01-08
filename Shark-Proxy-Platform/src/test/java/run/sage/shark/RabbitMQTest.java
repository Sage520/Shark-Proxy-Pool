package run.sage.shark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.sage.shark.common.constant.RabbitConstants;
import run.sage.shark.project.entity.to.FetchLogAddTo;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMQTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send() {
        for (int i = 0; i < 20; i++) {
            FetchLogAddTo fetchLogAddTo = new FetchLogAddTo();

            fetchLogAddTo.setSource("system");
            fetchLogAddTo.setValue(100);

            rabbitTemplate.convertAndSend(RabbitConstants.MQ_FETCH_LOG_EXCHANGE, RabbitConstants.MQ_FETCH_LOG_ADD_QUEUE_KEY, fetchLogAddTo);
        }
    }

}
