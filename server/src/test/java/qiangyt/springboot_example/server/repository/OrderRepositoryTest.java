package qiangyt.springboot_example.server.repository;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.entity.OrderEO;
import qiangyt.springboot_example.server.entity.ProductEO;

/**
 * @author
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

	@Autowired
    private OrderRepository repository;


    @Test
	public void findByCustomerAccountId_happy() {
        var account1 = new AccountEO(); {
            account1.setId(UUID.randomUUID());
            account1.setAddress("a1");
            account1.setFirstName("f1");
            account1.setSecondName("s1"); 
            account1 = this.entityManager.persist(account1);
        }

        var account2 = new AccountEO(); {
            account2.setId(UUID.randomUUID());
            account2.setAddress("a2");
            account2.setFirstName("f2");
            account2.setSecondName("s2");  
            account2 = this.entityManager.persist(account2);
        }

        var product = new ProductEO(); {
            product.setId(UUID.randomUUID());
            product.setAmount(99);
            product.setName("p");  
            product = this.entityManager.persist(product);
        }

        var expected = new OrderEO(); {
            expected.setId(UUID.randomUUID());
            expected.setCustomerAccount(account1);
            expected.setProduct(product);
            expected = this.entityManager.persist(expected);
        }

        var actual = this.repository.findByCustomerAccountId(account1.getId());
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(expected, actual.get(0));
    }

    @Test
	public void findByCustomerAccountId_accountId_not_matches() {
        var account = new AccountEO(); {
            account.setId(UUID.randomUUID());
            account.setAddress("a");
            account.setFirstName("f");
            account.setSecondName("s");
            account = this.entityManager.persist(account);
        }

        var product = new ProductEO(); {
            product.setId(UUID.randomUUID());
            product.setAmount(99);
            product.setName("p");
            product = this.entityManager.persist(product);
        }

        var expected = new OrderEO(); {
            expected.setId(UUID.randomUUID());
            expected.setCustomerAccount(account);
            expected.setProduct(product);
            expected = this.entityManager.persist(expected);
        }

        //var order2 = this.entityManager.find(OrderEO.class, order.getId());

        var actual = this.repository.findByCustomerAccountId(UUID.randomUUID());
        Assert.assertTrue(actual.isEmpty());
    }

}
