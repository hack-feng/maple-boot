package com.maple.common.util.excel;

import com.maple.common.util.excel.bean.ExportTableBean;
import com.maple.common.util.excel.bean.ExportExcelTheme;
import com.maple.common.util.excel.bean.GradeBean;
import com.maple.common.util.excel.bean.UserBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/9/12
 */
public class ExportExcelTitle {
    @Getter
    @AllArgsConstructor
    public enum UserExcel {

        /**
         * 表头信息
         */
        ORDER("序号", "order", 8),
        CLASS_NAME("班级名称", "className", 15),
        USER_NAME("姓名", "userName", 15),
        SEX("性别", "sex", 10),
        AGE("年龄", "age", 10),
        REMARK("兴趣爱好", "remark", 15),
        REMARK2("兴趣爱好", "remark2", 20),
        ;

        /**
         * 中文标题
         */
        private final String titleCn;

        /**
         * 查出的值对应Map的key
         */
        private final String valueKey;

        /**
         * 对应的列宽
         */
        private final Integer titleWidth;

        /**
         * 获取excel对应的数据
         */
        public static ExportTableBean getValue(List<UserBean> list, String excelName, ExportExcelTheme theme) {
            List<String> titleCn = new ArrayList<>();
            List<String> valueKey = new ArrayList<>();
            List<Integer> titleWidth = new ArrayList<>();
            for (UserExcel excel : UserExcel.values()) {
                titleCn.add(excel.titleCn);
                valueKey.add(excel.valueKey);
                titleWidth.add(excel.titleWidth);
            }
            ExportTableBean tableBean = new ExportTableBean();
            tableBean.setHeaders(titleCn);
            tableBean.setTitle(excelName);
            tableBean.setKeys(valueKey);
            tableBean.setWidth(titleWidth);
            tableBean.setTheme(theme == null ? ExportExcelTheme.DEFAULT : theme);
            tableBean.setDataList(beanToMap(list));
            tableBean.setHorizontalMergerColumnHeaders(Collections.singletonList("兴趣爱好"));
            tableBean.setVerticalMergerColumnHeaders(Collections.singletonList("班级名称"));
            return tableBean;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum GradeExcel {

        /**
         * 表头信息
         */
        ORDER("序号", "order", 8),
        CLASS_NAME("班级名称", "className", 15),
        USER_NAME("姓名", "userName", 15),
        CHAIN_GRADE("语文", "chinaGrade", 10),
        MATH_GRADE("数学", "mathGrade", 10),
        ENGLISH_GRADE("英语", "englishGrade", 10),
        ;

        /**
         * 中文标题
         */
        private final String titleCn;

        /**
         * 查出的值对应Map的key
         */
        private final String valueKey;

        /**
         * 对应的列宽
         */
        private final Integer titleWidth;

        /**
         * 获取excel对应的数据
         */
        public static ExportTableBean getValue(List<GradeBean> list, String excelName, ExportExcelTheme theme) {
            List<String> titleCn = new ArrayList<>();
            List<String> valueKey = new ArrayList<>();
            List<Integer> titleWidth = new ArrayList<>();
            for (GradeExcel excel : GradeExcel.values()) {
                titleCn.add(excel.titleCn);
                valueKey.add(excel.valueKey);
                titleWidth.add(excel.titleWidth);
            }

            ExportTableBean tableBean = new ExportTableBean();
            tableBean.setHeaders(titleCn);
            tableBean.setTitle(excelName);
            tableBean.setKeys(valueKey);
            tableBean.setWidth(titleWidth);
            tableBean.setTheme(theme == null ? ExportExcelTheme.DEFAULT : theme);
            tableBean.setDataList(beanToMap(list));
            tableBean.setVerticalMergerColumnHeaders(Collections.singletonList("班级名称"));
            return tableBean;
        }
    }

    /**
     * 将实体类转成map形式，方便导出excel取值
     *
     * @param list 待转换的数据
     * @return 转换后的数据
     */
    private static List<Map<String, Object>> beanToMap(List<?> list) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return dataList;
        }
        for (Object obj : list) {
            Class<?> clazz = obj.getClass();
            Map<String, Object> map = new HashMap<>(32);
            while (clazz != null) {
                //获得类的的属性名 数组
                Field[] fields = clazz.getDeclaredFields();
                try {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        map.put(field.getName(), field.get(obj));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                clazz = clazz.getSuperclass();
            }
            dataList.add(map);
        }
        return dataList;
    }
}
