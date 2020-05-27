package qiangyt.springboot_example.server.service;

import qiangyt.springboot_example.api.vo.Account;
import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.repository.AccountRepository;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void initMocks() {
      MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadAccountEO_happy() {
        UUID id = UUID.randomUUID();

        AccountEO expected = new AccountEO();
        expected.setId(UUID.randomUUID());
        when(this.accountRepository.findById(id)).thenReturn(Optional.of(expected));

        AccountEO actual = this.target.loadAccountEO(id);
        Assertions.assertSame(expected, actual);
    }


    @Test
    public void loadAccountEO_not_found() {
        UUID id = UUID.randomUUID();

        when(this.accountRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.target.loadAccountEO(id);
          }, "loadAccountEO should complain the entity not found");
    }

    @Test
    public void getAccount_happy() {
        UUID id = UUID.randomUUID();

        AccountEO expected = new AccountEO();
        expected.setId(UUID.randomUUID());
        when(this.accountRepository.findById(id)).thenReturn(Optional.of(expected));

        Account actual = this.target.getAccount(id);
        Assertions.assertSame(expected.getId(), actual.getId());
    }

}
