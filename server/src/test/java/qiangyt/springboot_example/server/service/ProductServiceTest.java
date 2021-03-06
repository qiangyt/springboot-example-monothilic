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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


    @BeforeEach
    public void initMocks() {
      MockitoAnnotations.initMocks(this);
    }


    @Test
    public void loadProduct_happy() {
        UUID id = UUID.randomUUID();

        ProductEO expected = new ProductEO();
        expected.setId(UUID.randomUUID());
        when(this.productRepository.findById(id)).thenReturn(Optional.of(expected));

        ProductEO actual = this.target.loadProductEO(id);
        Assertions.assertSame(expected, actual);
    }


    @Test
    public void loadProduct_not_found() {
        UUID id = UUID.randomUUID();

        when(this.productRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.target.loadProductEO(id);
        }, "load product should complain that the product entity is not found");
    }


    @Test
    public void decreaseProductAmount_notSoldout() {
        UUID productId = UUID.randomUUID();
        int orderAmount = 8;
        int threshold = 10;
        int productAmount = threshold + orderAmount + 1;

        ProductEO product = new ProductEO();
        product.setId(productId);
        product.setAmount(productAmount);

        when(this.productRepository.save(product)).thenReturn(product);

        this.target.setOutOfProductNotifyThreshold(threshold);
        this.target.decreaseProductAmount(product, orderAmount);

        Assertions.assertEquals(productAmount - orderAmount, product.getAmount());

        verify(this.queueService, never()).notifyProductSoldOut(any());
    }


    @Test
    public void decreaseProductAmount_soldout() {
        UUID productId = UUID.randomUUID();
        int orderAmount = 8;
        int threshold = 10;
        int productAmount = threshold + orderAmount;

        ProductEO product = new ProductEO();
        product.setId(productId);
        product.setAmount(productAmount);

        when(this.productRepository.save(product)).thenReturn(product);

        this.target.setOutOfProductNotifyThreshold(threshold);
        this.target.decreaseProductAmount(product, orderAmount);

        Assertions.assertEquals(productAmount - orderAmount, product.getAmount());

        verify(this.queueService, times(1))
            .notifyProductSoldOut(ArgumentMatchers.argThat(msg -> {
                return msg.getProductId().equals(productId)
                    && msg.getRemainingAmount() == (productAmount - orderAmount);
            }));
    }

}
