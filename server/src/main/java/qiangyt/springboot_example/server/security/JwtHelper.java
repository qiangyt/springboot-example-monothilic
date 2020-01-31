/**
 *
 */
package qiangyt.springboot_example.server.security;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;


/**
 * Based on https://github.com/Smith-Cruise/Spring-Boot-Security-JWT-SPA
 *          org.inlighting.security.util.JwtUtil
 */
public class JwtHelper {
    
    private final static long EXPIRE_TIME = 5 * 60 * 1000;

    public static String sign(String username, String secret) {
        var expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;//TODO
        }
    }

    
    public static boolean verify(String token, String username, String secret) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            /*var jwt = */verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;//TODO
        }
    }

    
    public static String getUsername(String token) {
        try {
            var jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null; //TODO
        }
    }

}
