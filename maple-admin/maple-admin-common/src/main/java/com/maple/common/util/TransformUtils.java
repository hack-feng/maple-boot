package com.maple.common.util;


import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/1/7
 */
public class TransformUtils {

    private TransformUtils() {
        
    }

    private static final Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    /**
     * 单个对象之间转换
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return MAPPER.map(source, destinationClass);
    }

    /**
     * 集合对象之间转换
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            destinationList.add(MAPPER.map(sourceObject, destinationClass));
        }
        return destinationList;
    }

    public static void map(Object source, Object destination) {
        MAPPER.map(source, destination);
    }
}
