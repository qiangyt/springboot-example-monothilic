package qiangyt.springboot_example.api.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

import lombok.Getter;
import qiangyt.springboot_example.api.OrderAPI;
import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.Order;
import qiangyt.springboot_example.api.vo.OrderDetail;
import qiangyt.springboot_example.common.rest.SyncRestClient;

@Getter
@Component
public class OrderAPIRestClient implements OrderAPI {

    private final SyncRestClient client;

    public OrderAPIRestClient(@Autowired RestTemplateBuilder restTemplateBuilder,
            @Value("${app.api.rest.base-url:http://localhost:8080}") String baseUrl) {
        this.client = new SyncRestClient(restTemplateBuilder, baseUrl + Paths.Order.BASE, false);
    }

    @Override
    public Order getOrder(UUID orderId) {
        return getClient().GET(Paths.Order.getOrder, Order.class, orderId);
    }

    @Override
    public Order createOrder(CreateOrderReq request) {
        return getClient().POST(Paths.Order.createOrder, request, Order.class);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        getClient().DELETE(Paths.Order.deleteOrder, void.class, orderId);
    }

    @Override
    public Order[] findAllOrders() {
        return getClient().GET(Paths.Order.findAllOrders, Order[].class);
    }

    @Override
    public Order[] findOrdersByCustomerAccountId(UUID customerAccountId) {
        return getClient().GET(Paths.Order.findOrdersByCustomerAccountId + "?customerAccountId={customerAccountId}", Order[].class, customerAccountId);
    }

    @Override
    public OrderDetail getOrderDetail(UUID orderId) {
        return getClient().GET(Paths.Order.getOrderDetail, OrderDetail.class);
    }

}
