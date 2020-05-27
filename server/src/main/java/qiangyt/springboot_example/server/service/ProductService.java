package qiangyt.springboot_example.server.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.ProductAPI;
import qiangyt.springboot_example.api.rnr.CreateProductReq;
import qiangyt.springboot_example.api.vo.Product;
import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.ProductEO;
import qiangyt.springboot_example.server.queue.ProductSoldOutMessage;
import qiangyt.springboot_example.server.queue.QueueService;
import qiangyt.springboot_example.server.repository.ProductRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author
 *
 */
@Getter
@Setter
@Slf4j
@Component
public class ProductService implements ProductAPI {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private QueueService queueService;

    @Value("${app.out-of-product-notify.threshold: 10}")
    private int outOfProductNotifyThreshold;

    Product renderProduct(ProductEO entity) {
        return ProductEO.VO_COPYER.copy(entity);
    }


    @Override
    public Product getProduct(UUID productId) {
        Optional<ProductEO> entity = getProductRepository().findById(productId);
        return renderProduct(entity.get());
    }


    Product[] renderProducts(Iterable<ProductEO> entities) {
        return ProductEO.VO_COPYER.copy(entities);
    }


    public ProductEO loadProductEO(UUID productId) {
        Optional<ProductEO> entity = getProductRepository().findById(productId);
        if (!entity.isPresent()) {
            throw new NotFoundException("product(id=%s) not found", productId);
        }
        return entity.get();
    }


    @Override
    public Product createProduct(CreateProductReq request) {
        ProductEO product = new ProductEO();
        product.setId(UUID.randomUUID());
        product.setName(request.getName());
        product.setAmount(request.getAmount());

        return renderProduct(getProductRepository().save(product));
    }


    @Override
    public void deleteProduct(UUID productId) {
        ProductEO entity = loadProductEO(productId);
        getProductRepository().delete(entity);
    }

    @Override
    public Product[] findAllProducts() {
        Iterable<ProductEO> entities = getProductRepository().findAll();
        return renderProducts(entities);
    }


    public void decreaseProductAmount(ProductEO product, int amount) {
        int remainingAmount = product.getAmount() - amount;

        product.setAmount(remainingAmount);
        getProductRepository().save(product);

        if (product.getAmount() <= getOutOfProductNotifyThreshold()) {
            ProductSoldOutMessage msg = new ProductSoldOutMessage();
            msg.setProductId(product.getId());
            msg.setRemainingAmount(remainingAmount);

            getQueueService().notifyProductSoldOut(msg);
        }
    }


    public void handleProductSoldOutNotification(ProductSoldOutMessage message) {
        log.warn("product(id={}) will be sold out soon. remainingAmount={}", message.getProductId(), message.getRemainingAmount());
    }


}
