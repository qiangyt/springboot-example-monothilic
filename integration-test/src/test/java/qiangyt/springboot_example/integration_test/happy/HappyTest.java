package qiangyt.springboot_example.integration_test.happy;


import java.io.File;

import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;


public class HappyTest {

    static final Logger SERVER_LOG = LoggerFactory.getLogger("<server>");
    static final Logger RABBITMQ_LOG = LoggerFactory.getLogger("<rabbitmq>");
    static final Logger MYSQL_LOG = LoggerFactory.getLogger("<mysql>");

    @ClassRule
    public static DockerComposeContainer environment =
        new DockerComposeContainer(new File("src/test/java/qiangyt/springboot_example/integration_test/happy/docker-compose.yml"))
        .withLocalCompose(true)
        .withExposedService("rabbitmq", 5672, Wait.forListeningPort())
        .withExposedService("server", 8080, Wait.forListeningPort())
        .withExposedService("mysql", 3306, Wait.forListeningPort());

    static {
        environment.withLogConsumer("server", new Slf4jLogConsumer(SERVER_LOG))
                .withLogConsumer("mysql", new Slf4jLogConsumer(MYSQL_LOG))
                .withLogConsumer("rabbitmq", new Slf4jLogConsumer(RABBITMQ_LOG));//.followOutput(logConsumer);
    }

    @Test
    public void test() throws Exception {
        //String url = String.format("http://%s:%d", environment.getServiceHost("server", 8080), environment.getServicePort("server", 8080));

        //WorkerAPI workerAPI = new WorkerRestAPI(new RestTemplateBuilder(), url, false);

        //System.out.println("1. #################################################################################################");
        //WorkerGroup g = workerAPI.findWorkerGroupByName("not exists");
        //Assert.assertNull(g);
    }


}
