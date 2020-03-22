package qiangyt.springboot_example.server.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import qiangyt.springboot_example.api.AuthAPI;
import qiangyt.springboot_example.api.rest.Paths;
import qiangyt.springboot_example.api.rnr.SignInResp;
import qiangyt.springboot_example.server.config.ExposedViaSpringfox;
import lombok.Getter;


/**
 * @author
 *
 */
@Getter
@ExposedViaSpringfox
@RestController
@RequestMapping(path = Paths.Auth.BASE, produces = APPLICATION_JSON_UTF8_VALUE, consumes = "*")
public class AuthRestController {

    @Autowired
    private AuthAPI authAPI;

    @PostMapping(path = Paths.Auth.signInByName, consumes = APPLICATION_JSON_UTF8_VALUE)
    public SignInResp signInByName(@PathVariable String name, @RequestBody @NotBlank String password) {
        return getAuthAPI().signInByName(name, password);
    }

}
