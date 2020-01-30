package qiangyt.springboot_example.server.misc;

import qiangyt.springboot_example.api.vo.Order;
import qiangyt.springboot_example.common.bean.BeanCopyer;
import qiangyt.springboot_example.server.entity.OrderEO;


public class OrderCopyer extends BeanCopyer<OrderEO, Order> {

    public OrderCopyer() {
        super(OrderEO.class, Order.class, Order::new, Order[]::new);
    }

    public Order copy(OrderEO source) {
        var r = super.copy(source);
        if (r == null) {
            return null;
        }

        var account = source.getCustomerAccount();        
        r.setCustomerName(account.getFirstName() + " " + account.getSecondName());

        r.setProductName(source.getProduct().getName());

        return r;
    }

}
