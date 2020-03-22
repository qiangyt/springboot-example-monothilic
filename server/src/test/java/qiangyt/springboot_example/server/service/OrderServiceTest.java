package qiangyt.springboot_example.server.service;

import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.common.error.BadRequestException;
import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.entity.OrderEO;
import qiangyt.springboot_example.server.entity.ProductEO;
import qiangyt.springboot_example.server.repository.OrderRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author
 *
 */
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private OrderService target;

    @BeforeEach
    public void initMocks() {
      MockitoAnnotations.initMocks(this);
    }
    

    @Test
    public void loadOrderEO_happy() {
        var id = UUID.randomUUID();

        var expected = new OrderEO();
        expected.setId(UUID.randomUUID());
        when(this.orderRepository.findById(id)).thenReturn(Optional.of(expected));

        var actual = this.target.loadOrderEO(id);
        Assertions.assertSame(expected, actual);
    }


    @Test
    public void loadOrderEO_not_found() {
        var id = UUID.randomUUID();

        when(this.orderRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.target.loadOrderEO(id);
        }, "load order should complain that the order entity is not found");
    }

    @Test
    public void createOrder_happy() {
        var orderId = UUID.randomUUID();
        var amount = 20;

        var productId = UUID.randomUUID();
        var product = new ProductEO();
        product.setId(productId);
        product.setName("p");
        product.setAmount(100);
        when(this.productService.loadProductEO(productId)).thenReturn(product);

        var accountId = UUID.randomUUID();
        var account = new AccountEO();
        account.setId(accountId);
        account.setFirstName("f");
        account.setSecondName("s");
        when(this.accountService.loadAccountEO(accountId)).thenReturn(account);
        
        var req = new CreateOrderReq();
        req.setAmount(amount);
        req.setCustomerAccountId(accountId);
        req.setProductId(productId);

        var order = new OrderEO();
        order.setId(orderId);
        order.setAmount(amount);
        order.setCustomerAccount(account);
        order.setProduct(product);
        
        when(this.orderRepository.save(ArgumentMatchers.argThat(t -> {
            return (t.getCustomerAccount() == account
                    && t.getProduct() == product
                    && t.getAmount() == amount);
        }))).thenReturn(order);
        
        var actual = this.target.createOrder(req);
        verify(this.productService).decreaseProductAmount(product, req.getAmount());

        Assertions.assertEquals(orderId, actual.getId());
        Assertions.assertEquals("f s", actual.getCustomerName());
        Assertions.assertEquals(amount, actual.getAmount());
        Assertions.assertEquals("p", actual.getProductName());
    }



    @Test
    public void createOrder_productSoldOut() {
        var amount = 100;

        var productId = UUID.randomUUID();
        var product = new ProductEO();
        product.setId(productId);
        product.setAmount(amount - 1);
        when(this.productService.loadProductEO(productId)).thenReturn(product);

        var accountId = UUID.randomUUID();
        var account = new AccountEO();
        account.setId(accountId);
        when(this.accountService.loadAccountEO(accountId)).thenReturn(account);
        
        var req = new CreateOrderReq();
        req.setAmount(amount);
        req.setCustomerAccountId(accountId);
        req.setProductId(productId);
        
        Assertions.assertThrows(BadRequestException.class, () -> {
            this.target.createOrder(req);
        }, "create order should complain that the product is sold out");
    }

}
