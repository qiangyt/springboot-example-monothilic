package qiangyt.springboot_example.server.service;

import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.OrderEO;
import qiangyt.springboot_example.server.repository.OrderRepository;

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
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService target;
    
    
    @Before 
    public void initMocks() {
      MockitoAnnotations.initMocks(this);
    }
    

    @Test
    public void loadOrderEO_happy() {
        var id = UUID.randomUUID();

        var expected = new OrderEO();
        expected.setId(UUID.randomUUID());
        when(this.orderRepository.findById(id)).thenReturn(Optional.of(expected));

        var actual = this.target.loadOrderEO(id);
        Assert.assertSame(expected, actual);
    }


    @Test(expected = NotFoundException.class)
    public void loadOrderEO_not_found() {
        var id = UUID.randomUUID();

        when(this.orderRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        this.target.loadOrderEO(id);
    }

}
