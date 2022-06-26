package com.wei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author Administrator
 */
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        System.out.println();
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
