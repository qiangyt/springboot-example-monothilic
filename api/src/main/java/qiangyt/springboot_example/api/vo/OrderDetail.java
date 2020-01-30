package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderDetail {
    
    private UUID id;

    private Account customerAccount;

    private Product product;

    private int amount;

}
