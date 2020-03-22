package qiangyt.springboot_example.server.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class CORSConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*");
            //.allowCredentials(true).maxAge(3600);
    }

}
