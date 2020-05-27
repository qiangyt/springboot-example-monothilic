package qiangyt.springboot_example.api;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import qiangyt.springboot_example.api.rnr.CreateProductReq;
import qiangyt.springboot_example.api.vo.Product;
import qiangyt.springboot_example.common.error.NotFoundException;


/**
 * @author
 *
 */
public interface ProductAPI {

    Product getProduct(@NotNull UUID productId);

    default Product loadProduct(@NotNull UUID productId) {
        Product r = getProduct(productId);
        if (r == null) {
            throw new NotFoundException("product(id=%s) not found", productId);
        }
        return r;
    }

    Product createProduct(@Valid CreateProductReq request);

    void deleteProduct(@NotNull UUID productId);

    Product[] findAllProducts();

}
