package qiangyt.springboot_example.server.controller;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import qiangyt.springboot_example.api.AccountAPI;
import qiangyt.springboot_example.api.rest.Paths;
import qiangyt.springboot_example.api.rnr.CreateAccountReq;
import qiangyt.springboot_example.api.rnr.SignInResp;
import qiangyt.springboot_example.server.config.ExposedViaSpringfox;
import qiangyt.springboot_example.api.vo.Account;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@ExposedViaSpringfox
@RestController
@RequestMapping(path = Paths.Account.BASE, produces = APPLICATION_JSON_UTF8_VALUE, consumes = "*")
public class AccountRestController {

    @Autowired
    private AccountAPI accountAPI;

    @PostMapping(path = Paths.Account.signInByName, consumes = APPLICATION_JSON_UTF8_VALUE)
    public SignInResp signInByName(@PathVariable String name, @RequestBody @NotBlank String password) {
        return getAccountAPI().signInByName(name, password);
    }

    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(path = Paths.Account.createAccount, consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody CreateAccountReq request) {
        return getAccountAPI().createAccount(request);
    }

    //@IsAdmin
    @DeleteMapping(path = Paths.Account.deleteAccount)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable UUID accountId) {
        getAccountAPI().deleteAccount(accountId);
    }

    @GetMapping(path = Paths.Account.findAccountByName)
    public Account findAccountByName(@PathVariable String name) {
        return getAccountAPI().findAccountByName(name);
    }

    //@IsAdmin
    @GetMapping(path = Paths.Account.getAccount)
    public Account getAccount(@PathVariable UUID accountId) {
        return getAccountAPI().getAccount(accountId);
    }

    //@IsAdmin
    @GetMapping(path = Paths.Account.findAllAccounts)
    public Account[] findAllAccounts() {
        return getAccountAPI().findAllAccounts();
    }

}
