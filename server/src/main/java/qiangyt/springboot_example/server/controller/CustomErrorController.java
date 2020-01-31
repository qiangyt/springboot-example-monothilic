/**
 *
 */
package qiangyt.springboot_example.server.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qiangyt.springboot_example.common.json.JsonHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Based on https://github.com/Smith-Cruise/Spring-Boot-Security-JWT-SPA
 *          org.inlighting.security.util.JwtUtil
 */
@RestController
public class CustomErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        var msg = request.getAttribute("javax.servlet.error.message");
        return JsonHelper.to(msg);
    }
}
