package qiangyt.springboot_example.server.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import qiangyt.springboot_example.api.AccountAPI;
import qiangyt.springboot_example.api.rnr.CreateAccountReq;
import qiangyt.springboot_example.api.vo.Account;
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
public class AccountService implements AccountAPI {

    @Autowired
    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    Account renderAccount(AccountEO entity) {
        return AccountEO.VO_COPYER.copy(entity);
    }


    @Override
    public Account getAccount(UUID accountId) {
        var entity = getAccountRepository().findById(accountId);
        return renderAccount(entity.get());
    }

    @Override
    public Account findAccountByName(String name) {
        var entity = findAccountEntityByName(name);
        return renderAccount(entity);
    }


    public AccountEO findAccountEntityByName(String name) {
        return getAccountRepository().findByName(name);
    }


    Account[] renderAccounts(Iterable<AccountEO> entities) {
        return AccountEO.VO_COPYER.copy(entities);
    }


    public AccountEO loadAccountEO(UUID accountId) {
        var entity = getAccountRepository().findById(accountId);
        if (entity.isEmpty()) {
            throw new NotFoundException("account(id=%s) not found", accountId);
        }
        return entity.get();
    }

    @Override
    public Account createAccount(CreateAccountReq request) {
        var account = AccountEO.REQ_COPYER.copy(request);
        account.setId(UUID.randomUUID());
        account.setPassword(getPasswordEncoder().encode(request.getPassword()));

        return renderAccount(getAccountRepository().save(account));
    }


    @Override
    public void deleteAccount(UUID accountId) {
        var entity = loadAccountEO(accountId);
        getAccountRepository().delete(entity);
    }

    @Override
    public Account[] findAllAccounts() {
        var entities = getAccountRepository().findAll();
        return renderAccounts(entities);
    }


}
