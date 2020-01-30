package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVO {
    
    private UUID id;

    private String customerName;

    private String productName;

    private int amount;

}
