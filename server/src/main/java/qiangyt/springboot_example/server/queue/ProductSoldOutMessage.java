package qiangyt.springboot_example.server.queue;

import java.util.UUID;
import lombok.Data;

@Data
public class ProductSoldOutMessage {

    private UUID productId;

    private int remainingAmount;

}
