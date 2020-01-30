package qiangyt.springboot_example.server.misc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableWebMvc
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringfoxConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Swagger UI: <host>:<port>/swagger-ui.html
    registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .select().apis(RequestHandlerSelectors.withClassAnnotation(ExposedViaSpringfox.class)).build()
        .useDefaultResponseMessages(false)
        .forCodeGeneration(true);
  }

  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder()
        .title("Monothilic Springboot Example API")
        .version("1.0.0")
        .termsOfServiceUrl("https://github.com/qiangyt/monothilic-springboot-example.git")
        .contact(new Contact("Yiting Qiang", null, "qiangyt@wxcount.com"))
        .license("APL")
        .build();
  }

}
