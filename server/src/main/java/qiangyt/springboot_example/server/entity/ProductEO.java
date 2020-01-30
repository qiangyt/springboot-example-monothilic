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

import qiangyt.springboot_example.common.bean.BeanCopyer;
import qiangyt.springboot_example.api.vo.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="product")
@EntityListeners(AuditingEntityListener.class)
public class ProductEO {
    
    public static final BeanCopyer<ProductEO, Product> VO_COPYER
        = new BeanCopyer<>(ProductEO.class, Product.class, Product::new, Product[]::new);

    @Id
    private UUID id;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updatedAt", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

}
