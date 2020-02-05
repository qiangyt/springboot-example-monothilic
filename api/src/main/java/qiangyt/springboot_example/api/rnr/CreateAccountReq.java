package qiangyt.springboot_example.api.rnr;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import qiangyt.springboot_example.api.enums.AccountRole;

@Data
public class CreateAccountReq {

    @Size(max = 32)
    @NotBlank
    private String name;

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

    @NotEmpty
    private AccountRole[] roles;

}
