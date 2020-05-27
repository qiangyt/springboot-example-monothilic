package qiangyt.springboot_example.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.AuthAPI;
import qiangyt.springboot_example.api.enums.AuthenticationTokenType;
import qiangyt.springboot_example.api.rnr.SignInResp;
import qiangyt.springboot_example.server.security.JwtHelper;
import qiangyt.springboot_example.server.security.UserPrincipal;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@Component
public class AuthService implements AuthAPI {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public SignInResp signInByName(String name, String password) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(name, password);
        Authentication authResult = getAuthenticationManager().authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(authResult);

        UserPrincipal user = (UserPrincipal) authResult.getPrincipal();

        String token = JwtHelper.sign(user.getUsername(), user.getPassword());

        SignInResp r = new SignInResp();
        r.setAccount(user.getAccount());
        r.setTokenType(AuthenticationTokenType.bearer);
        r.setToken(token);

        return r;
    }

}
