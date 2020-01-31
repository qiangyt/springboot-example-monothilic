package qiangyt.springboot_example.server.service;

import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.repository.AccountRepository;

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
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService target;
    
    @Before 
    public void initMocks() {
      MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadAccountEO_happy() {
        var id = UUID.randomUUID();

        var expected = new AccountEO();
        expected.setId(UUID.randomUUID());
        when(this.accountRepository.findById(id)).thenReturn(Optional.of(expected));

        var actual = this.target.loadAccountEO(id);
        Assert.assertSame(expected, actual);
    }


    @Test(expected = NotFoundException.class)
    public void loadAccountEO_not_found() {
        var id = UUID.randomUUID();

        when(this.accountRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        this.target.loadAccountEO(id);
    }

    @Test
    public void getAccount_happy() {
        var id = UUID.randomUUID();

        var expected = new AccountEO();
        expected.setId(UUID.randomUUID());
        when(this.accountRepository.findById(id)).thenReturn(Optional.of(expected));

        var actual = this.target.getAccount(id);
        Assert.assertSame(expected.getId(), actual.getId());
    }

}
