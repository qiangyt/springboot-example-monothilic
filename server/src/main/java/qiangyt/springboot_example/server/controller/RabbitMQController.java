package qiangyt.springboot_example.server.controller;

import lombok.extern.slf4j.Slf4j;
import qiangyt.springboot_example.common.json.JsonHelper;
import qiangyt.springboot_example.server.queue.ProductSoldOutMessage;
import qiangyt.springboot_example.server.service.ProductService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Slf4j
@Component
public class RabbitMQController {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app.product-sold-out-notify.queue-name}")
    public void onProductSoldOutNotificationReceived(String messageText) {
      if (log.isDebugEnabled()) {
        log.debug("receive product-sold-out message: {}", messageText);
      }

      ProductSoldOutMessage msg = JsonHelper.from(messageText, ProductSoldOutMessage.class);

      //TODO: configure the queue, to post to a dead letter queue after retry several times
      try {
        getProductService().handleProductSoldOutNotification(msg);
      } catch (Exception ex) {
        log.error("failed to handle received product-sold-out message: " + messageText, ex);
      }
    }

}
