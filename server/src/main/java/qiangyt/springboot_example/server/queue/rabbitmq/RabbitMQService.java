package qiangyt.springboot_example.server.queue.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Setter;
import qiangyt.springboot_example.common.json.JsonHelper;
import qiangyt.springboot_example.server.queue.ProductSoldOutMessage;
import qiangyt.springboot_example.server.queue.QueueService;
import lombok.Getter;

@Setter
@Getter
@Service
public class RabbitMQService implements QueueService {

    @Autowired
    private RabbitTemplate template;

    @Value("${app.product-sold-out-notify.queue-name}")
    private String productSoldOutNotifyQueueName;

    @Override
    public void notifyProductSoldOut(ProductSoldOutMessage message) {
        var msgPayload = JsonHelper.to(message); // TODO: gRPC msg?
        getTemplate().convertAndSend(getProductSoldOutNotifyQueueName(), msgPayload);
    }


}
