package qiangyt.springboot_example.api.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import qiangyt.springboot_example.common.misc.StringHelper;

public enum AccountRole {
    customer, 
    admin;

    public final Collection<GrantedAuthority> grantedAuthorities
        = Collections.unmodifiableCollection(AuthorityUtils.createAuthorityList(name()));
    
        
    public static Collection<GrantedAuthority> parseAsGrantedAuthorities(String roles) {
        var roleNames = new HashSet<String>();
        for (var role: parse(roles)) {
            roleNames.add(role.name());
        }

        return AuthorityUtils.createAuthorityList(roleNames.toArray(new String[roleNames.size()]));
    }


    public static AccountRole[] parse(String roles) {
        if (StringHelper.isBlank(roles)) {
            return new AccountRole[0];
        }

        var r = new ArrayList<AccountRole>();
        for (var roleName : roles.split(",")) {
            if (roleName == null) {
                continue;
            }
            roleName = roleName.trim();
            if (roleName.isEmpty()) {
                continue;
            }

            var role = valueOf(roleName);
            if (role == null) {
                continue; //TODO: warning
            }
            
            r.add(role);
        }

        return r.toArray(new AccountRole[r.size()]);
    }

}
