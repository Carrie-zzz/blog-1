package top.scloud.scm.conf.web;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.scloud.scm.conf.ParamConfig;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author code
 * @version V1.0
 * @Title: 基础代码
 * @Description: 基础信息
 * Restful API 访问路径: http://IP:port/{context-path}/documentation/swagger-ui.html
 * http://springfox.github.io/springfox/docs/current/#springfox-swagger2-with-spring-mvc-and-spring-boot
 */

@Configuration
@EnableSwagger2
@ComponentScan("top.scloud.scm.controller")
public class RestApiConfig extends WebMvcConfigurerAdapter {
    @Value("${swagger.enable}")
    private boolean enableSwagger;
    
    @Autowired 
    ParamConfig paramConfig;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/documentation/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/ui");
        registry.addRedirectViewController(
                "/documentation/swagger-resources/configuration/security",
                "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/documentation/swagger-resources",
                "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/documentation/swagger-ui.html**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/documentation/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        
       if (paramConfig.getNotCheckToken()==false) {
            tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string"))
                    .parameterType("header").required(false).build();
            pars.add(tokenPar.build());
        }

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .enable(enableSwagger)
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext())).select()
                .apis(RequestHandlerSelectors.basePackage("top.scloud.scm.controller"))
                .paths(PathSelectors.any()).build().globalOperationParameters(pars);
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "api_key", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/")).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope =
                new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null, "apiKey", ApiKeyVehicle.HEADER,
                "api_key", ",");
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("SCloud scm", "", "");
        return new ApiInfoBuilder().title("Spring Restful中使用Swagger2构建RESTful APIs")
                .description("Platform's REST API, all the applications could access the Object model data via JSON.").termsOfServiceUrl("No terms of service")
                .contact(contact).version("1.0.0").build();
    }
}
