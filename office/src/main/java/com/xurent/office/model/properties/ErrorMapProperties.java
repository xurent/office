package com.xurent.office.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * @author xurent
 * @date 2021/4/12 11:50
 */
@Component
@ConfigurationProperties(prefix = "message")
@Data
public class ErrorMapProperties {
    /**
     * 错误码Map
     */
    public Map<Integer, String> maps;
}
