package com.maple.common.util.excel.bean;

import lombok.Data;

import java.util.List;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/9/12
 */
@Data
public class ExportSheetBean {

    /**
     * sheet页名称，如果一个excel有多个sheet页，该值不可以重复
     * 使用域：sheet页
     */
    private String sheetName;

    /**
     * 如果为空，不受保护，任意修改；
     * 如果有值，则受保护，传入的数据为密码
     * 使用域：sheet页
     */
    private String protectSheet;

    /**
     * 单sheet页对应多table
     * 使用域：sheet页
     */
    private List<ExportTableBean> list;
}
