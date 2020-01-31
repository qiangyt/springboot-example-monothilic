package qiangyt.springboot_example.server.queue;


public interface QueueService {

    void notifyProductSoldOut(ProductSoldOutMessage message);

}
