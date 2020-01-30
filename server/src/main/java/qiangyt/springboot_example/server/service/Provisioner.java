package qiangyt.springboot_example.server.service;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.entity.OrderEO;
import qiangyt.springboot_example.server.entity.ProductEO;
import qiangyt.springboot_example.server.repository.AccountRepository;
import qiangyt.springboot_example.server.repository.OrderRepository;
import qiangyt.springboot_example.server.repository.ProductRepository;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@Component
@Profile("test")
public class Provisioner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void init() {
        AccountEO c1;
        if (getAccountRepository().count() == 0) {
            c1 = new AccountEO();
            c1.setAddress("ca");
            c1.setFirstName("fn");
            c1.setSecondName("sn");
            c1.setId(UUID.randomUUID());
            c1 = getAccountRepository().save(c1);

        } else {
            c1 = getAccountRepository().findAll().iterator().next();
        }

        ProductEO p1;
        if (getProductRepository().count() == 0) {
            p1 = new ProductEO();
            p1.setAmount(99);
            p1.setName("pn");
            p1.setId(UUID.randomUUID());
            p1 = getProductRepository().save(p1);

        } else {
            p1 = getProductRepository().findAll().iterator().next();
        }

        OrderEO o1;
        if (getOrderRepository().count() == 0) {
            o1 = new OrderEO();
            o1.setAmount(3);
            o1.setCustomerAccount(c1);
            o1.setProduct(p1);
            o1.setId(UUID.randomUUID());

            o1 = getOrderRepository().save(o1);
        } else {
            o1 = getOrderRepository().findAll().iterator().next();
        }
    }

}
