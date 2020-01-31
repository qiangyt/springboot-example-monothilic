package qiangyt.springboot_example.server.service;

import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.ProductEO;
import qiangyt.springboot_example.server.queue.QueueService;
import qiangyt.springboot_example.server.repository.ProductRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author
 *
 */
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private QueueService queueService;

    @InjectMocks
    private ProductService target;
    
    
    @Before 
    public void initMocks() {
      MockitoAnnotations.initMocks(this);
    }


    @Test
    public void loadProduct_happy() {
        var id = UUID.randomUUID();

        var expected = new ProductEO();
        expected.setId(UUID.randomUUID());
        when(this.productRepository.findById(id)).thenReturn(Optional.of(expected));

        var actual = this.target.loadProductEO(id);
        Assert.assertSame(expected, actual);
    }


    @Test(expected = NotFoundException.class)
    public void loadProduct_not_found() {
        var id = UUID.randomUUID();

        when(this.productRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        this.target.loadProductEO(id);
    }


    @Test
    public void decreaseProductAmount_notSoldout() {
        var productId = UUID.randomUUID();
        var orderAmount = 8;
        var threshold = 10;
        var productAmount = threshold + orderAmount + 1;

        var product = new ProductEO();
        product.setId(productId);
        product.setAmount(productAmount);

        when(this.productRepository.save(product)).thenReturn(product);

        this.target.setOutOfProductNotifyThreshold(threshold);
        this.target.decreaseProductAmount(product, orderAmount);

        Assert.assertEquals(productAmount - orderAmount, product.getAmount());

        verify(this.queueService, never()).notifyProductSoldOut(any());
    }


    @Test
    public void decreaseProductAmount_soldout() {
        var productId = UUID.randomUUID();
        var orderAmount = 8;
        var threshold = 10;
        var productAmount = threshold + orderAmount;

        var product = new ProductEO();
        product.setId(productId);
        product.setAmount(productAmount);

        when(this.productRepository.save(product)).thenReturn(product);

        this.target.setOutOfProductNotifyThreshold(threshold);
        this.target.decreaseProductAmount(product, orderAmount);

        Assert.assertEquals(productAmount - orderAmount, product.getAmount());

        verify(this.queueService, times(1))
            .notifyProductSoldOut(ArgumentMatchers.argThat(msg -> {
                return msg.getProductId().equals(productId)
                    && msg.getRemainingAmount() == (productAmount - orderAmount);
            }));
    }

}
