package qiangyt.springboot_example.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import qiangyt.springboot_example.api.rnr.CreateAccountReq;
import qiangyt.springboot_example.api.vo.Account;
import qiangyt.springboot_example.common.error.NotFoundException;


/**
 * @author
 *
 */
public interface AccountAPI {

    Account getAccount(@NotNull UUID accountId);

    default Account loadAccount(@NotNull UUID accountId) {
        var r = getAccount(accountId);
        if (r == null) {
            throw new NotFoundException("account(id=%s) not found", accountId);
        }
        return r;
    }

    Account createAccount(@Valid CreateAccountReq request);

    void deleteAccount(@NotNull UUID accountId);

    List<Account> findAllAccounts();

}
