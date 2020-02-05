package qiangyt.springboot_example.api.rnr;

import lombok.Data;
import qiangyt.springboot_example.api.enums.AuthenticationTokenType;
import qiangyt.springboot_example.api.vo.Account;


@Data
public class SignInResp {

    private Account account;

    private String token;

    private AuthenticationTokenType tokenType;

}
