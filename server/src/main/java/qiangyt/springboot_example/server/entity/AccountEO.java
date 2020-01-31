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
import qiangyt.springboot_example.common.bean.BeanCopyer;

@Getter
@Setter
@Entity
@Table(name="account")
@EntityListeners(AuditingEntityListener.class)
public class AccountEO {

    public static final BeanCopyer<AccountEO, Account> VO_COPYER 
        = new BeanCopyer<>(AccountEO.class, Account.class, Account::new, Account[]::new);

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

    @Column(name = "password", length = 32, nullable = false)
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
