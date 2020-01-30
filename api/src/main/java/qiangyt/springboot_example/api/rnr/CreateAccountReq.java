package qiangyt.springboot_example.api.rnr;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class CreateAccountReq {

    @Size(max = 32)
    @NotBlank
    private String firstName;
    
    @Size(max = 32)
    @NotBlank 
    private String secondName;
    
    @Size(max = 32)
    @NotBlank 
    private String password;
    
    @Size(max = 100)
    @NotBlank 
    private String address;
    
}
