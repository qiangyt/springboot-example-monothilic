package qiangyt.springboot_example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"qiangyt.springboot_example.server"})
@EntityScan("qiangyt.springboot_example.server.entity")
@EnableJpaRepositories(basePackages = "qiangyt.springboot_example.server.repository")
@EnableJpaAuditing
@EnableAsync
public class MonothilicApplication {

  public static void main(String[] args) {
    SpringApplication.run(MonothilicApplication.class, args);
  }


}
