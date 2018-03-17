package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

/**
 * json对象和类对象的转换
 * Created by Administrator on 2018/3/17 0017.
 */
@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    /**
     * Object to String
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T t) {
        if (t == null) {
            return null;
        }
        try {
            return t instanceof String ? (String) t : objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            log.error("parse obj to String exception,error:{}", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> tTypeReference) {
        if (str == null || tTypeReference == null) {
            return null;
        }
        try {
            return (T) (tTypeReference.getType().equals(String.class) ? str : (String) objectMapper.readValue(str, tTypeReference));
        } catch (Exception e) {
            log.error("parse String to Object Exception,String:{},TypeReference<T>:{},error:{}",str, tTypeReference.getType(), e);
            return null;
        }
    }
}
