package qiangyt.springboot_example.server.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Based on https://github.com/Smith-Cruise/Spring-Boot-Security-JWT-SPA
 *          org.inlighting.security.Main
 */
public class PasswordHelper {

    public static void main(String[] args) throws Exception {
        var encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("smith123"));
    }
}
