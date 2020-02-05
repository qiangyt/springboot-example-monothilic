package qiangyt.springboot_example.server.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import qiangyt.springboot_example.api.vo.Account;

@Getter
public class UserPrincipal extends User {

    private final Account account;

	/**
     *
     */
    private static final long serialVersionUID = -544288614901727532L;

    public UserPrincipal(Account account, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.account = account;
    }

}
