package qiangyt.springboot_example.server.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.AccountAPI;
import qiangyt.springboot_example.api.OrderAPI;
import qiangyt.springboot_example.api.ProductAPI;
import qiangyt.springboot_example.api.enums.AccountRole;
import qiangyt.springboot_example.api.rnr.CreateAccountReq;
import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.rnr.CreateProductReq;
import qiangyt.springboot_example.api.vo.Account;
import qiangyt.springboot_example.api.vo.Product;
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
    private AccountAPI accountAPI;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductAPI productAPI;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderAPI orderAPI;

    @Autowired
    private OrderRepository orderRepository;


    @PostConstruct
    public void init() {
        Account c1 = getAccountAPI().findAccountByName("admin");
        if (c1 == null) {
            var req = new CreateAccountReq();
            req.setAddress("ca");
            req.setName("admin");
            req.setFirstName("fn");
            req.setSecondName("sn");
            req.setPassword("pwd");
            req.setRoles(new AccountRole[]{AccountRole.admin});
            c1 = getAccountAPI().createAccount(req);
        }

        Product p1;
        if (getProductRepository().count() == 0) {
            var req = new CreateProductReq();
            req.setAmount(99);
            req.setName("pn");
            p1 = getProductAPI().createProduct(req);
        } else {
            var productId = getProductRepository().findAll().iterator().next().getId();
            p1 = getProductAPI().loadProduct(productId);
        }

        if (getOrderRepository().count() == 0) {
            var req = new CreateOrderReq();
            req.setAmount(3);
            req.setCustomerAccountId(c1.getId());
            req.setProductId(p1.getId());

            getOrderAPI().createOrder(req);
        }
    }

}
