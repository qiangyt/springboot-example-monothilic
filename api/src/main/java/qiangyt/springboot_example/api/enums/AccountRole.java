package qiangyt.springboot_example.api.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import qiangyt.springboot_example.common.misc.StringHelper;

public enum AccountRole {
    customer,
    admin;

    public final Collection<GrantedAuthority> grantedAuthorities
        = Collections.unmodifiableCollection(AuthorityUtils.createAuthorityList(name()));


    public static Collection<GrantedAuthority> toGrantedAuthorities(AccountRole[] roles) {
        Set<String> roleNames = new HashSet<>();
        for (AccountRole role: roles) {
            roleNames.add(role.name());
        }
        return AuthorityUtils.createAuthorityList(roleNames.toArray(new String[roleNames.size()]));
    }

    public static String format(AccountRole[] roles) {
        return StringHelper.join(", ", roles);
    }

    public static AccountRole[] parse(String roles) {
        if (StringHelper.isBlank(roles)) {
            return new AccountRole[0];
        }

        List<AccountRole> r = new ArrayList<>();
        for (String roleName : roles.split(",")) {
            if (roleName == null) {
                continue;
            }
            roleName = roleName.trim();
            if (roleName.isEmpty()) {
                continue;
            }

            AccountRole role = valueOf(roleName);
            if (role == null) {
                continue; //TODO: warning
            }

            r.add(role);
        }

        return r.toArray(new AccountRole[r.size()]);
    }

}
