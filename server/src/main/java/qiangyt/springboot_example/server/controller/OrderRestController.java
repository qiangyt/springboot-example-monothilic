package qiangyt.springboot_example.server.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import qiangyt.springboot_example.api.OrderAPI;
import qiangyt.springboot_example.api.rest.Paths;
import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.OrderDetail;
import qiangyt.springboot_example.server.config.ExposedViaSpringfox;
import qiangyt.springboot_example.server.security.IsCustomer;
import qiangyt.springboot_example.api.vo.Order;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@ExposedViaSpringfox
@RestController
@RequestMapping(path = Paths.Order.BASE, produces = APPLICATION_JSON_UTF8_VALUE)
public class OrderRestController {

    @Autowired
    private OrderAPI orderAPI;


    @IsCustomer
    @PostMapping(path = Paths.Order.createOrder, consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody CreateOrderReq request) {
        return getOrderAPI().createOrder(request);
    }


    //@IsUser
    @GetMapping(path = Paths.Order.findOrdersByCustomerAccountId, consumes = "*")
    public Order[] findOrdersByCustomerAccountId(@RequestParam UUID customerAccountId) {
        return getOrderAPI().findOrdersByCustomerAccountId(customerAccountId);
    }

    
    //@IsAdmin
    @DeleteMapping(path = Paths.Order.deleteOrder, consumes = "*")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable UUID orderId) {
        getOrderAPI().deleteOrder(orderId);
    }

    
    //@IsUser
    @GetMapping(path = Paths.Order.getOrder, consumes = "*")
    public Order getOrder(@PathVariable UUID orderId) {
        return getOrderAPI().getOrder(orderId);
    }

    
    //@IsUser
    @GetMapping(path = Paths.Order.getOrderDetail, consumes = "*")
    public OrderDetail getOrderDetail(@PathVariable UUID orderId) {
        return getOrderAPI().getOrderDetail(orderId);
    }

    //@IsAdmin
    @GetMapping(path = Paths.Order.findAllOrders, consumes = "*")
    public Order[] findAllOrders() {
        return getOrderAPI().findAllOrders();
    }

}
