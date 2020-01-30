package qiangyt.springboot_example.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.OrderDetailVO;
import qiangyt.springboot_example.api.vo.OrderVO;
import qiangyt.springboot_example.common.error.NotFoundException;


/**
 * @author
 *
 */
public interface OrderAPI {

    OrderVO getOrder(@NotNull UUID orderId);

    default OrderVO loadOrder(@NotNull UUID orderId) {
        var r = getOrder(orderId);
        if (r == null) {
            throw new NotFoundException("order(id=%s) not found", orderId);
        }
        return r;
    }

    OrderDetailVO getOrderDetail(@NotNull UUID orderId);

    OrderVO createOrder(@Valid CreateOrderReq request);
    
    List<OrderVO> findOrdersByCustomerAccountId(@NotNull UUID customerAccountId);

    void deleteOrder(@NotNull UUID orderId);

    List<OrderVO> findAllOrders();

}
