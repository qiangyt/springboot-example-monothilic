package qiangyt.springboot_example.server.misc;

import qiangyt.springboot_example.api.vo.OrderVO;
import qiangyt.springboot_example.common.bean.BeanCopyer;
import qiangyt.springboot_example.server.entity.OrderEO;


public class OrderCopyer extends BeanCopyer<OrderEO, OrderVO> {

    public OrderCopyer() {
        super(OrderEO.class, OrderVO.class, OrderVO::new, OrderVO[]::new);
    }

    public OrderVO copy(OrderEO source) {
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
