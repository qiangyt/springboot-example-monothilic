package qiangyt.springboot_example.server.misc;

import qiangyt.springboot_example.api.vo.OrderDetailVO;
import qiangyt.springboot_example.common.bean.BeanCopyer;
import qiangyt.springboot_example.server.entity.OrderEO;
import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.entity.ProductEO;


public class OrderDetailCopyer extends BeanCopyer<OrderEO, OrderDetailVO> {

    public OrderDetailCopyer() {
        super(OrderEO.class, OrderDetailVO.class, OrderDetailVO::new, OrderDetailVO[]::new);
    }

    public OrderDetailVO copy(OrderEO source) {
        var r = super.copy(source);
        if (r == null) {
            return null;
        }
     
        r.setCustomerAccount(AccountEO.VO_COPYER.copy(source.getCustomerAccount()));
        r.setProduct(ProductEO.VO_COPYER.copy(source.getProduct()));

        return r;
    }

}
