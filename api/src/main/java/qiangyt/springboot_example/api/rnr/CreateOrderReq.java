package qiangyt.springboot_example.api.rnr;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class CreateOrderReq {

    @NotNull
    private UUID customerAccountId;
    
    @NotNull 
    private UUID productId;
    
    @Min(1)
    @NotNull 
    private int amount;
    
}
