package qiangyt.springboot_example.server.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import qiangyt.springboot_example.api.OrderAPI;
import qiangyt.springboot_example.api.rest.Paths;
import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.OrderDetailVO;
import qiangyt.springboot_example.api.vo.OrderVO;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@RestController
@RequestMapping(path = Paths.Order.BASE, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
public class OrderRestController {

    @Autowired
    private OrderAPI orderAPI;


    @PostMapping(path = Paths.Order.createOrder)
    public OrderVO createOrder(@RequestBody CreateOrderReq request) {
        return getOrderAPI().createOrder(request);
    }


    @GetMapping(path = Paths.Order.findOrdersByCustomerAccountId)
    public List<OrderVO> findOrdersByCustomerAccountId(@PathVariable UUID customerAccountId) {
        return getOrderAPI().findOrdersByCustomerAccountId(customerAccountId);
    }

    
    @DeleteMapping(path = Paths.Order.deleteOrder)
    public void deleteOrder(@PathVariable UUID orderId) {
        getOrderAPI().deleteOrder(orderId);
    }

    
    @GetMapping(path = Paths.Order.getOrder)
    public OrderVO getOrder(@PathVariable UUID orderId) {
        return getOrderAPI().getOrder(orderId);
    }

    
    @GetMapping(path = Paths.Order.getOrderDetail)
    public OrderDetailVO getOrderDetail(@PathVariable UUID orderId) {
        return getOrderAPI().getOrderDetail(orderId);
    }

    @GetMapping(path = Paths.Order.findAllOrders)
    public List<OrderVO> findAllOrders() {
        return getOrderAPI().findAllOrders();
    }

}
