package com.maple.common.util.excel.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/13
 */
@Data
public class ExportTableBean {
    /**
     * 表格标题名
     * 使用域：table
     */
    private String title;

    /**
     * 表格属性列名List。如果需要序号，则在该数组第一个位置为'序号'，否则不生成序号
     * 使用域：table
     */
    private List<String> headers;

    /**
     * 数据Map集合中的对应的key，key在List中的位置与headers相对应。如果headers需要序号，则keys=headers.size-1
     * 使用域：table
     */
    private List<String> keys;

    /**
     * 对应单元格的宽度，表示多少个字符
     * 使用域：table
     */
    private List<Integer> width;

    /**
     * 需要显示的数据集合，集合内的数组必须为map，对应keys的值。
     * 使用域：table
     */
    private List<Map<String, Object>> dataList;

    /**
     * 水平（横向）合并的列 (仅用于标题区域)
     * Headers不为空时，该Headers一样且连续，将会自动横向合并
     * 注意：仅用于标题，数据不会自动合并
     * 使用域：table标题区域
     */
    private List<String> horizontalMergerColumnHeaders;

    /**
     * 垂直（竖向）合并的列 (仅用于数据区域)
     * Headers不为空时，该Headers的数据一样，将会自动竖向合并
     * 注意：重名列也会开启合并，多列同时开启合并时，后续列合并，不会根据前面列合并的结果进行处理，所有数据均会参与合并
     * 使用域：table数据区域
     */
    private List<String> verticalMergerColumnHeaders;

    /**
     * 生成excel的主题颜色
     * 使用域：sheet页
     */
    private ExportExcelTheme theme;
}
