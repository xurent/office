

package com.xurent.office.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象复制方法类
 *
 * @author xurent
 * @date 2020-04-08
 */
@Slf4j
public class BeanCopyUtils {

    private static final Map<String, BeanCopier> BEAN_COPIERS = new ConcurrentHashMap<>();

    public static void copy(final @NotNull Object srcObj, final @NotNull Object destObj) {
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        copier.copy(srcObj, destObj, null);
    }

    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }
}