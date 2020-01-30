package qiangyt.springboot_example.server.service;

import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.ProductEO;
import qiangyt.springboot_example.server.repository.ProductRepository;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

}
