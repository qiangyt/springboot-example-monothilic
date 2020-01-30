package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Data;

@Data
public class Order {
    
    private UUID id;

    private String customerName;

    private String productName;

    private int amount;

}
