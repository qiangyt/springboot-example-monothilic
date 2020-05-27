package qiangyt.springboot_example.server.service;

import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.Order;
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
        UUID id = UUID.randomUUID();

        OrderEO expected = new OrderEO();
        expected.setId(UUID.randomUUID());
        when(this.orderRepository.findById(id)).thenReturn(Optional.of(expected));

        OrderEO actual = this.target.loadOrderEO(id);
        Assertions.assertSame(expected, actual);
    }


    @Test
    public void loadOrderEO_not_found() {
        UUID id = UUID.randomUUID();

        when(this.orderRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.target.loadOrderEO(id);
        }, "load order should complain that the order entity is not found");
    }

    @Test
    public void createOrder_happy() {
        UUID orderId = UUID.randomUUID();
        int amount = 20;

        UUID productId = UUID.randomUUID();
        ProductEO product = new ProductEO();
        product.setId(productId);
        product.setName("p");
        product.setAmount(100);
        when(this.productService.loadProductEO(productId)).thenReturn(product);

        UUID accountId = UUID.randomUUID();
        AccountEO account = new AccountEO();
        account.setId(accountId);
        account.setFirstName("f");
        account.setSecondName("s");
        when(this.accountService.loadAccountEO(accountId)).thenReturn(account);

        CreateOrderReq req = new CreateOrderReq();
        req.setAmount(amount);
        req.setCustomerAccountId(accountId);
        req.setProductId(productId);

        OrderEO order = new OrderEO();
        order.setId(orderId);
        order.setAmount(amount);
        order.setCustomerAccount(account);
        order.setProduct(product);

        when(this.orderRepository.save(ArgumentMatchers.argThat(t -> {
            return (t.getCustomerAccount() == account
                    && t.getProduct() == product
                    && t.getAmount() == amount);
        }))).thenReturn(order);

        Order actual = this.target.createOrder(req);
        verify(this.productService).decreaseProductAmount(product, req.getAmount());

        Assertions.assertEquals(orderId, actual.getId());
        Assertions.assertEquals("f s", actual.getCustomerName());
        Assertions.assertEquals(amount, actual.getAmount());
        Assertions.assertEquals("p", actual.getProductName());
    }



    @Test
    public void createOrder_productSoldOut() {
        int amount = 100;

        UUID productId = UUID.randomUUID();
        ProductEO product = new ProductEO();
        product.setId(productId);
        product.setAmount(amount - 1);
        when(this.productService.loadProductEO(productId)).thenReturn(product);

        UUID accountId = UUID.randomUUID();
        AccountEO account = new AccountEO();
        account.setId(accountId);
        when(this.accountService.loadAccountEO(accountId)).thenReturn(account);

        CreateOrderReq req = new CreateOrderReq();
        req.setAmount(amount);
        req.setCustomerAccountId(accountId);
        req.setProductId(productId);

        Assertions.assertThrows(BadRequestException.class, () -> {
            this.target.createOrder(req);
        }, "create order should complain that the product is sold out");
    }

}
