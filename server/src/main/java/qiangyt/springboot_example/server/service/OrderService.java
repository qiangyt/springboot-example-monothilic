package qiangyt.springboot_example.server.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.OrderAPI;
import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.OrderDetailVO;
import qiangyt.springboot_example.api.vo.OrderVO;
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


    OrderDetailVO renderOrderDetailVO(OrderEO entity) {
        return DETAIL_VO_COPYER.copy(entity);
    }


    OrderVO renderOrderVO(OrderEO entity) {
        return VO_COPYER.copy(entity);
    }


    List<OrderVO> renderOrderVOs(Iterable<OrderEO> entities) {
        return VO_COPYER.copy(entities);
    }


    public OrderVO getOrder(UUID orderId) {
        var entity = getOrderRepository().findById(orderId);
        return renderOrderVO(entity.get());
    }

    public OrderDetailVO getOrderDetail(UUID orderId) {
        var entity = getOrderRepository().findById(orderId);
        return renderOrderDetailVO(entity.get());
    }

    public OrderEO loadOrderEO(UUID orderId) {
        var entity = getOrderRepository().findById(orderId);
        if (entity.isEmpty()) {
            throw new NotFoundException("order(id=%s) not found", orderId);
        }
        return entity.get();
    }


    public OrderVO createOrder(CreateOrderReq request) {
        var customerAccount = getAccountService().loadAccountEO(request.getCustomerAccountId());
        var product = getProductService().loadProductEO(request.getProductId());

        var order = new OrderEO();
        order.setId(UUID.randomUUID());
        order.setCustomerAccount(customerAccount);
        order.setProduct(product);
        order.setAmount(request.getAmount());

        return renderOrderVO(getOrderRepository().save(order));
    }

    
    public List<OrderVO> findOrdersByCustomerAccountId(UUID customerAccountId) {
        var entities = getOrderRepository().findByCustomerAccountId(customerAccountId);
        return renderOrderVOs(entities);
    }


    public void deleteOrder(UUID orderId) {
        var entity = loadOrderEO(orderId);
        getOrderRepository().delete(entity);
    }

    public List<OrderVO> findAllOrders() {
        var entities = getOrderRepository().findAll();
        return renderOrderVOs(entities);
    }

}
