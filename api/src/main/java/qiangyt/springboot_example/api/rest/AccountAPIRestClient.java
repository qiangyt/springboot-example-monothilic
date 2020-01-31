package qiangyt.springboot_example.api.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

import lombok.Getter;
import qiangyt.springboot_example.api.AccountAPI;
import qiangyt.springboot_example.api.rnr.CreateAccountReq;
import qiangyt.springboot_example.api.vo.Account;
import qiangyt.springboot_example.common.rest.SyncRestClient;

@Getter
@Component
public class AccountAPIRestClient implements AccountAPI {

    private final SyncRestClient client;
        
    public AccountAPIRestClient(@Autowired RestTemplateBuilder restTemplateBuilder,
                                @Value("${app.api.rest.base-url:http://localhost:8080}") String baseUrl ) {
        this.client = new SyncRestClient(restTemplateBuilder, baseUrl + Paths.Account.BASE, false);
    }

    @Override
    public Account getAccount(UUID accountId) {
        return getClient().GET(Paths.Account.getAccount, Account.class, accountId);
    }

    @Override
    public Account createAccount(CreateAccountReq request) {
        return getClient().POST(Paths.Account.createAccount, request, Account.class);
    }

    @Override
    public void deleteAccount(UUID accountId) {
        getClient().DELETE(Paths.Account.deleteAccount, void.class, accountId);
    }

    @Override
    public Account[] findAllAccounts() {
        return getClient().GET(Paths.Account.findAllAccounts, Account[].class);
    }



}
