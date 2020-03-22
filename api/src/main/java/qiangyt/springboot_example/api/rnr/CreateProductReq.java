package qiangyt.springboot_example.api.rnr;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class CreateProductReq {

    @Size(max = 32)
    @NotBlank
    private String name;
    
    @Min(1)
    @NotNull 
    private int amount;
    
}
