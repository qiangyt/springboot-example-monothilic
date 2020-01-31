package qiangyt.springboot_example.server.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

  @Bean
  public Queue productSoldOutNotifyQueue(@Value("${app.product-sold-out-notify.queue-name}") String queueName) {
		return new Queue(queueName);
	}

}
