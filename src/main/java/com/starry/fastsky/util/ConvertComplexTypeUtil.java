package com.starry.fastsky.util;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ConvertComplexTypeUtil
 * Description: 数据类型转化工具类
 *
 * @author: starryfei
 * @date: 2019-01-15 11:10
 **/
public class ConvertComplexTypeUtil {
    /**
     * 通过浏览器发送过来的参数转化为方法对应的数据类型，包括基本类型和自定义类型，自定义类型通过创建对象，给对象赋值实现
     * @param parameters
     * @param queryStringDecoder
     * @return
     */
    public static Object[] convertDataType(Class<?>[] parameters, QueryStringDecoder queryStringDecoder){
        if (parameters.length == 0) {
            return null;
        }
        Object[] object = new Object[parameters.length];
        Map<String, List<String>> decodeParameters = queryStringDecoder.parameters();
        List<String> values = new ArrayList<>();
        decodeParameters.forEach((key, value) -> values.add(value.get(0)));
        queryStringDecoder.parameters();
        for(int i=0; i<parameters.length; i++) {
            if (parameters[i].isPrimitive() || parameters[i].equals(String.class)) {
                Object obj = customType(parameters[i], values.get(i));
                object[i] = obj;
            } else { //自定义类型
                Class<?> par = parameters[i];
                try {
                    // 创建对象实例
                    Object obj = par.newInstance();
                    // 给对象实例的参数赋值
                    decodeParameters.forEach((key,value)->{
                        Field field = null;
                        try {
                            field = par.getDeclaredField(key);
                            field.setAccessible(true);
                            field.set(obj, customType(field.getType(), value.get(0)));
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                   object[i] = obj;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return object;
    }

    /**
     * 基本数据类型
     * @param parameter
     * @param data
     * @return
     */
    private static Object customType(Class<?> parameter, String data) {
        if (parameter.equals(int.class) || parameter.equals(Integer.class)) {
            return Integer.parseInt(data);
        } else if (parameter.equals(Double.class) || parameter.equals(double.class)) {
            return Double.parseDouble(data);
        } else if (parameter.equals(Float.class) || parameter.equals(float.class)) {
            return Float.parseFloat(data);
        } else if (parameter.equals(Long.class) || parameter.equals(long.class)) {
            return Long.parseLong(data);
        } else if (parameter.equals(Boolean.class) || parameter.equals(boolean.class)) {
            return Boolean.parseBoolean(data);
        } else if (parameter.equals(Short.class) || parameter.equals(short.class)) {
            return Short.parseShort(data);
        } else if (parameter.equals(Byte.class) || parameter.equals(byte.class)) {
            return Byte.parseByte(data);
        } else if (parameter.equals(BigDecimal.class)) {
            return new BigDecimal(data);
        } else { // String
            return data;
        }
    }
}
