package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailVO {
    
    private UUID id;

    private AccountVO customerAccount;

    private ProductVO product;

    private int amount;

}
