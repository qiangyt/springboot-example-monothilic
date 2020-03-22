package qiangyt.springboot_example.api.rest;

public interface Paths {

    final String BASE = "/api";

    public static interface Order {

        final String BASE = Paths.BASE + "/orders";

        final String getOrder               = "{orderId}";
        final String getOrderDetail         = "/detail/{orderId}";
        final String createOrder            = "";
        final String findOrdersByCustomerAccountId = "query/byCustomerAccountId";
        final String deleteOrder            = "{orderId}";
        final String findAllOrders          = "all";
    }

    public static interface Auth {

        final String BASE = Paths.BASE + "/auth";
        final String signInByName = "signin/byName/{name}";
    }

    public static interface Account {

        final String BASE = Paths.BASE + "/accounts";

        final String getAccount               = "{accountId}";
        final String findAccountByName        = "query/byName/{name}";
        final String createAccount            = "";
        final String deleteAccount            = "{accountId}";
        final String findAllAccounts          = "all";
    }

    public static interface Product {

        final String BASE = Paths.BASE + "/products";

        final String getProduct               = "{productId}";
        final String createProduct            = "";
        final String deleteProduct            = "{productId}";
        final String findAllProducts          = "all";
    }

}
