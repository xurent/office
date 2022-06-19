package com.xurent.office.model.properties;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "url")
@Getter
public class CommonProperties {

    @Value(value = "${url.resource}")
    private  String  resource;


    /**
     * 过滤白名单URL
     */
    @Value(value = "${url.whitelist}")
    private String whitelist;
}
