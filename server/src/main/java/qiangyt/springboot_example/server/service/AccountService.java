package qiangyt.springboot_example.server.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.vo.AccountVO;
import qiangyt.springboot_example.common.error.NotFoundException;
import qiangyt.springboot_example.server.entity.AccountEO;
import qiangyt.springboot_example.server.repository.AccountRepository;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@Component
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    AccountVO renderAccountVO(AccountEO entity) {
        return AccountEO.VO_COPYER.copy(entity);
    }


    public AccountEO loadAccountEO(UUID accountId) {
        var entity = getAccountRepository().findById(accountId);
        if (entity.isEmpty()) {
            throw new NotFoundException("account(id=%s) not found", accountId);
        }
        return entity.get();
    }
    

}
