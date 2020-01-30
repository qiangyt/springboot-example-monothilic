package qiangyt.springboot_example.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.OrderDetail;
import qiangyt.springboot_example.api.vo.Order;
import qiangyt.springboot_example.common.error.NotFoundException;


/**
 * @author
 *
 */
public interface OrderAPI {

    Order getOrder(@NotNull UUID orderId);

    default Order loadOrder(@NotNull UUID orderId) {
        var r = getOrder(orderId);
        if (r == null) {
            throw new NotFoundException("order(id=%s) not found", orderId);
        }
        return r;
    }

    OrderDetail getOrderDetail(@NotNull UUID orderId);

    Order createOrder(@Valid CreateOrderReq request);
    
    List<Order> findOrdersByCustomerAccountId(@NotNull UUID customerAccountId);

    void deleteOrder(@NotNull UUID orderId);

    List<Order> findAllOrders();

}
