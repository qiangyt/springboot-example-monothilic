package qiangyt.springboot_example.server.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="`order`")
@EntityListeners(AuditingEntityListener.class)
public class OrderEO {

    @Id
    private UUID id;

    @JoinColumn(name = "customer_account_id", nullable = false)
    @ManyToOne
    private AccountEO customerAccount;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private ProductEO product;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updatedAt", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

}
