package qiangyt.springboot_example.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

import lombok.Getter;
import qiangyt.springboot_example.api.AuthAPI;
import qiangyt.springboot_example.api.rnr.SignInResp;
import qiangyt.springboot_example.common.rest.SyncRestClient;

@Getter
@Component
public class AuthAPIRestClient implements AuthAPI {

    private final SyncRestClient client;

    public AuthAPIRestClient(@Autowired RestTemplateBuilder restTemplateBuilder,
            @Value("${app.api.rest.base-url:http://localhost:8080}") String baseUrl) {
        this.client = new SyncRestClient(restTemplateBuilder, baseUrl + Paths.Account.BASE, false);
    }

    @Override
    public SignInResp signInByName(String name, String password) {
        return getClient().POST(Paths.Auth.signInByName, password, SignInResp.class, name);
    }



}
