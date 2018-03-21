package com.mmall.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * 参数校验，配合传入对象中包含的参数所添加的注解
 * 例如，传入的参数是SysDept对象，对该对象中的成员变量进行参数校验，需要在成员变量上添加特定的注解
 * @NotNull参数不为空，@NotBlank()参数不为空，@Max()参数最大值等等
 * 依赖于javax.validation和org.hibernate引入
 * Created by Administrator on 2018/3/21 0021.
 */
public class BeanValidator {

    // 全局校验工厂
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();


    /**
     * 验证单一参数t
     * Map中的key代表字段，value代表字段出现的错误信息
     * @param t
     * @param groups
     * @param <T>
     * @return
     */
    public static <T>Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        // 参数校验结果
        Set validateResult = validator.validate(t, groups);
        if (validateResult.isEmpty()) {
            // 参数校验没有问题，返回空的Map集合
            return Collections.emptyMap();
        } else {
            // LinkedHashMap保存了数据的插入顺序，当使用iterator遍历时，会首先获得最先放入的数据
            LinkedHashMap errors = Maps.newLinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation) iterator.next();
                // 封装错误字段和错误信息
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }

    /**
     * 验证集合类型的参数，集合中可以为任意类型的参数
     * @param collection
     * @return
     */
    public static Map<String, String> validateList(Collection<?> collection) {
        // 谷歌guavas提供的类，对应pom中的guava的依赖
        // 校验参数是否为空，若为空，返回空指针异常，不为空则返回参数本身
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors;
        do {
            if (!iterator.hasNext()) {
                // 参数为空，返回空的Map
                return Collections.emptyMap();
            }
            // object代表list中的每一个值，即参数
            Object object = iterator.next();
            errors = validate(object, new Class[0]);
        } while (errors.isEmpty());
        return errors;
    }

    /**
     * Object...代表可以传入任意数量和类型的继承自Object的对象作为参数进行校验
     * @param first
     * @param objects
     * @return
     */
    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            // 将参数组成集合
            return validateList(Lists.asList(first, objects));
        } else {
            return validate(first, new Class[0]);
        }
    }


}
