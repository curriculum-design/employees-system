package org.cdteam.employee;

import org.cdteam.spring.cloud.starter.web.EnableCdteamWeb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

/**
 * @author lesl
 */
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@MapperScan(basePackages = {"org.cdteam.employee.mapper", "org.cdteam.employee.*.mapper"}, annotationClass = Repository.class)
@EnableCdteamWeb(basePackages = {"org.cdteam.employee.*"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
