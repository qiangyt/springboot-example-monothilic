package qiangyt.springboot_example.api.rnr;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateOrderReq {

    @NotNull
    private UUID customerAccountId;
    
    @NotNull 
    private UUID productId;
    
    @Size(min = 1)
    @NotNull 
    private int amount;
    
}
