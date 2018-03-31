package com.mmall.common;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16 0016.
 */
@Setter
@Getter
// 当Json序列化返回数据中的key对应的值为null时，该key将不显示在数据中
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JsonData<T> implements Serializable {

    private boolean ret;

    private String msg;

    private T data;

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    public static <T> JsonData success(T data, String msg) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = data;
        jsonData.msg = msg;
        return jsonData;
    }

    public static <T> JsonData success(T data) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = data;
        return jsonData;
    }

    public static JsonData success() {
        return new JsonData(true);
    }

    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    /**
     * 序列化的时候忽略该值在页面中显示
     * @return
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.ret == true;
    }
}
