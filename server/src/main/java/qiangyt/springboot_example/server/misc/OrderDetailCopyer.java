package qiangyt.springboot_example.server.misc;

import qiangyt.springboot_example.api.vo.OrderDetail;
import qiangyt.springboot_example.common.bean.BeanCopyer;
import qiangyt.springboot_example.server.entity.OrderEO;
import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.entity.ProductEO;


public class OrderDetailCopyer extends BeanCopyer<OrderEO, OrderDetail> {

    public OrderDetailCopyer() {
        super(OrderEO.class, OrderDetail.class, OrderDetail::new, OrderDetail[]::new);
    }

    public OrderDetail copy(OrderEO source) {
        OrderDetail r = super.copy(source);
        if (r == null) {
            return null;
        }

        r.setCustomerAccount(AccountEO.VO_COPYER.copy(source.getCustomerAccount()));
        r.setProduct(ProductEO.VO_COPYER.copy(source.getProduct()));

        return r;
    }

}
