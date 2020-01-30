package qiangyt.springboot_example.server.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import qiangyt.springboot_example.api.ProductAPI;
import qiangyt.springboot_example.api.rest.Paths;
import qiangyt.springboot_example.api.rnr.CreateProductReq;
import qiangyt.springboot_example.server.misc.ExposedViaSpringfox;
import qiangyt.springboot_example.api.vo.Product;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@ExposedViaSpringfox
@RestController
@RequestMapping(path = Paths.Product.BASE, produces = APPLICATION_JSON_UTF8_VALUE)
public class ProductRestController {

    @Autowired
    private ProductAPI productAPI;


    @PostMapping(path = Paths.Product.createProduct, consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody CreateProductReq request) {
        return getProductAPI().createProduct(request);
    }

    
    @DeleteMapping(path = Paths.Product.deleteProduct, consumes = "*")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID productId) {
        getProductAPI().deleteProduct(productId);
    }

    
    @GetMapping(path = Paths.Product.getProduct, consumes = "*")
    public Product getProduct(@PathVariable UUID productId) {
        return getProductAPI().getProduct(productId);
    }


    @GetMapping(path = Paths.Product.findAllProducts, consumes = "*")
    public List<Product> findAllProducts() {
        return getProductAPI().findAllProducts();
    }

}
