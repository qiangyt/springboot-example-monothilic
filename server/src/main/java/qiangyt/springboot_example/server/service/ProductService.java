package qiangyt.springboot_example.server.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.ProductAPI;
import qiangyt.springboot_example.api.rnr.CreateProductReq;
import qiangyt.springboot_example.api.vo.Product;
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
public class ProductService implements ProductAPI {


    @Autowired
    private ProductRepository productRepository;


    Product renderProduct(ProductEO entity) {
        return ProductEO.VO_COPYER.copy(entity);
    }


    @Override
    public Product getProduct(UUID productId) {
        var entity = getProductRepository().findById(productId);
        return renderProduct(entity.get());
    }


    List<Product> renderProducts(Iterable<ProductEO> entities) {
        return ProductEO.VO_COPYER.copy(entities);
    }


    public ProductEO loadProductEO(UUID productId) {
        var entity = getProductRepository().findById(productId);
        if (entity.isEmpty()) {
            throw new NotFoundException("product(id=%s) not found", productId);
        }
        return entity.get();
    }

    
    @Override
    public Product createProduct(CreateProductReq request) {
        var product = new ProductEO();
        product.setId(UUID.randomUUID());
        product.setName(request.getName());
        product.setAmount(request.getAmount());

        return renderProduct(getProductRepository().save(product));
    }


    @Override
    public void deleteProduct(UUID productId) {
        var entity = loadProductEO(productId);
        getProductRepository().delete(entity);
    }

    @Override
    public List<Product> findAllProducts() {
        var entities = getProductRepository().findAll();
        return renderProducts(entities);
    }
    

}
