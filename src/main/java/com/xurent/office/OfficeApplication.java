package com.xurent.office;

import com.xurent.office.exception.pleiades.MixPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

@ServletComponentScan
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@PropertySource(value = "classpath:message.yml", factory = MixPropertySourceFactory.class)
public class OfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeApplication.class, args);
    }

}
