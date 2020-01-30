package qiangyt.springboot_example.api.rest;

public interface Paths {

    final String BASE = "/api";

    public static interface Order {

        final String BASE = Paths.BASE + "/orders";

        final String getOrder               = "{orderId}";
        final String getOrderDetail         = "{orderId}?detail=true";
        final String createOrder            = "?customerAccountId={customerAccountId}&productId={productId}&amount={amount}";
        final String findOrdersByCustomerAccountId = "?customerAccountId={customerAccountId}";
        final String deleteOrder            = "{orderId}";
        final String findAllOrders          = "";
    }
    
}
