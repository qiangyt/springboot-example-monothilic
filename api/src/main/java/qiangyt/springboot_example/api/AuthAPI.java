package qiangyt.springboot_example.api;

import qiangyt.springboot_example.api.rnr.SignInResp;


/**
 * @author
 *
 */
public interface AuthAPI {

    SignInResp signInByName(String name, String password);


}
