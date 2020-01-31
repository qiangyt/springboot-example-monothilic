package qiangyt.springboot_example.server.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.OrderAPI;
import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.OrderDetail;
import qiangyt.springboot_example.api.vo.Order;
import qiangyt.springboot_example.common.error.BadRequestException;
import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.OrderEO;
import qiangyt.springboot_example.server.repository.OrderRepository;
import qiangyt.springboot_example.server.misc.OrderCopyer;
import qiangyt.springboot_example.server.misc.OrderDetailCopyer;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@Component
public class OrderService implements OrderAPI {

    public static final OrderCopyer VO_COPYER = new OrderCopyer();

    public static final OrderDetailCopyer DETAIL_VO_COPYER = new OrderDetailCopyer();

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;


    OrderDetail renderOrderDetail(OrderEO entity) {
        return DETAIL_VO_COPYER.copy(entity);
    }


    Order renderOrder(OrderEO entity) {
        return VO_COPYER.copy(entity);
    }


    List<Order> renderOrders(Iterable<OrderEO> entities) {
        return VO_COPYER.copy(entities);
    }


    @Override
    public Order getOrder(UUID orderId) {
        var entity = getOrderRepository().findById(orderId);
        return renderOrder(entity.get());
    }


    @Override
    public OrderDetail getOrderDetail(UUID orderId) {
        var entity = getOrderRepository().findById(orderId);
        return renderOrderDetail(entity.get());
    }

    public OrderEO loadOrderEO(UUID orderId) {
        var entity = getOrderRepository().findById(orderId);
        if (entity.isEmpty()) {
            throw new NotFoundException("order(id=%s) not found", orderId);
        }
        return entity.get();
    }


    @Override
    public Order createOrder(CreateOrderReq request) {
        var customerAccount = getAccountService().loadAccountEO(request.getCustomerAccountId());
        var product = getProductService().loadProductEO(request.getProductId());

        int amount = request.getAmount();
        int remainingAmount = product.getAmount() - amount;
        if (remainingAmount <= 0) {
            throw new BadRequestException("product %s is sold out", product.getName());
        }

        var order = new OrderEO();
        order.setId(UUID.randomUUID());
        order.setCustomerAccount(customerAccount);
        order.setProduct(product);
        order.setAmount(amount);

        getProductService().decreaseProductAmount(product, amount);
        
        return renderOrder(getOrderRepository().save(order));
    }

    
    @Override
    public List<Order> findOrdersByCustomerAccountId(UUID customerAccountId) {
        var entities = getOrderRepository().findByCustomerAccountId(customerAccountId);
        return renderOrders(entities);
    }


    @Override
    public void deleteOrder(UUID orderId) {
        var entity = loadOrderEO(orderId);
        getOrderRepository().delete(entity);
    }


    @Override
    public List<Order> findAllOrders() {
        var entities = getOrderRepository().findAll();
        return renderOrders(entities);
    }

}
