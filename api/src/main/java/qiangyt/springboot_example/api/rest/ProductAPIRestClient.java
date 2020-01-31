package qiangyt.springboot_example.api.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

import lombok.Getter;
import qiangyt.springboot_example.api.ProductAPI;
import qiangyt.springboot_example.api.rnr.CreateProductReq;
import qiangyt.springboot_example.api.vo.Product;
import qiangyt.springboot_example.common.rest.SyncRestClient;

@Getter
@Component
public class ProductAPIRestClient implements ProductAPI {

    private final SyncRestClient client;
        
    public ProductAPIRestClient(@Autowired RestTemplateBuilder restTemplateBuilder,
                                @Value("${app.api.rest.base-url:http://localhost:8080}") String baseUrl ) {
        this.client = new SyncRestClient(restTemplateBuilder, baseUrl + Paths.Product.BASE, false);
    }

    @Override
    public Product getProduct(UUID productId) {
        return getClient().GET(Paths.Product.getProduct, Product.class, productId);
    }

    @Override
    public Product createProduct(CreateProductReq request) {
        return getClient().POST(Paths.Product.createProduct, request, Product.class);
    }

    @Override
    public void deleteProduct(UUID productId) {
        getClient().DELETE(Paths.Product.deleteProduct, void.class, productId);
    }

    @Override
    public Product[] findAllProducts() {
        return getClient().GET(Paths.Product.findAllProducts, Product[].class);
    }



}
