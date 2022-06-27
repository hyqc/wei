package com.wei.core.config;

import com.wei.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2API文档的配置
 *
 * @author macro
 * @date 2018/4/26
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.wei.*.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(this.globalHeaders())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("管理后台-wei")
                .description("管理后台-wei")
                .version("0.1")
                .build();
    }

    private List<Parameter> globalHeaders(){
        ParameterBuilder builder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        builder.name(jwtTokenUtil.getTokenHead()).description("Bearer Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        parameters.add(builder.build());
        return parameters;
    }
}