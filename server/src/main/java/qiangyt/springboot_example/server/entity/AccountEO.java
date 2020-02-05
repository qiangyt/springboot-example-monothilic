package qiangyt.springboot_example.server.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import qiangyt.springboot_example.api.vo.Account;
import qiangyt.springboot_example.api.enums.AccountRole;
import qiangyt.springboot_example.api.rnr.CreateAccountReq;
import qiangyt.springboot_example.common.bean.BeanCopyer;

@Getter
@Setter
@Entity
@Table(name="account")
@EntityListeners(AuditingEntityListener.class)
public class AccountEO {

    public static final BeanCopyer<AccountEO, Account> VO_COPYER
        = new BeanCopyer<>(AccountEO.class, Account.class, Account::new, Account[]::new) {
            @Override public Account copy(AccountEO source) {
                if (source == null) {
                    return null;
                }
                var r = super.copy(source);
                r.setRoles(AccountRole.parse(source.getRoles()));
                return r;
            }
        };

    public static final BeanCopyer<CreateAccountReq, AccountEO> REQ_COPYER
    = new BeanCopyer<>(CreateAccountReq.class, AccountEO.class, AccountEO::new, AccountEO[]::new) {
        @Override public AccountEO copy(CreateAccountReq source) {
            if (source == null) {
                return null;
            }
            var r = super.copy(source);
            r.setRoles(AccountRole.format(source.getRoles()));
            return r;
        }
    };

    @Id
    private UUID id;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 32)
    private String secondName;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "roles", nullable = false, length = 64)
    private String roles;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updatedAt", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

}
