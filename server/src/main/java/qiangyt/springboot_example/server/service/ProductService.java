package qiangyt.springboot_example.server.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.vo.ProductVO;
import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.ProductEO;
import qiangyt.springboot_example.server.repository.ProductRepository;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@Component
public class ProductService {


    @Autowired
    private ProductRepository productRepository;


    ProductVO renderProductVO(ProductEO entity) {
        return ProductEO.VO_COPYER.copy(entity);
    }


    public ProductEO loadProductEO(UUID productId) {
        var entity = getProductRepository().findById(productId);
        if (entity.isEmpty()) {
            throw new NotFoundException("product(id=%s) not found", productId);
        }
        return entity.get();
    }
    

}
