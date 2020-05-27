package qiangyt.springboot_example.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.Getter;
import qiangyt.springboot_example.api.enums.AccountRole;
import qiangyt.springboot_example.api.vo.Account;
import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.service.AccountService;


/**
 * Based on https://github.com/Smith-Cruise/Spring-Boot-Security-JWT-SPA
 *          org.inlighting.security.security.UserDetailsServiceImpl
 */
@Service
@Getter
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AccountEO accountEO = getAccountService().findAccountEntityByName(userName);
        if (accountEO == null) {
            throw new UsernameNotFoundException("user " + userName + " not found");
        }
        Account account = AccountEO.VO_COPYER.copy(accountEO);

        return new UserPrincipal(account, userName, accountEO.getPassword(), AccountRole.toGrantedAuthorities(account.getRoles()));
    }
}
