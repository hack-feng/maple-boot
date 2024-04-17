package com.maple.common.util.excel.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.*;

/**
 * 定义导出excel的样式主题
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/9/12
 */
@Getter
@AllArgsConstructor
public enum ExportExcelTheme {

    /**
     * 默认主题
     */
    DEFAULT("DEFAULT",
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(0, 0, 0), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(0, 0, 0), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(156, 155, 155), new DefaultIndexedColorMap())
    ),

    /**
     * 蓝色主题
     */
    BLUE("BLUE",
            new XSSFColor(new Color(91, 155, 213), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(221, 235, 247), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(189, 215, 238), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(0, 0, 0), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap())
    ),

    /**
     * 绿色主题
     */
    GREEN("GREEN",
            new XSSFColor(new Color(112, 173, 71), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(226, 239, 218), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(198, 224, 180), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(0, 0, 0), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap())
    ),

    /**
     * 橙色主题
     */
    ORANGE("ORANGE",
            new XSSFColor(new Color(237, 125, 49), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(252, 228, 214), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(248, 203, 173), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(0, 0, 0), new DefaultIndexedColorMap()),
            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap())
    );

    private final String code;

    /**
     * 标题栏背景色
     */
    private final XSSFColor titleBackgroundColor;

    /**
     * 标题栏字体颜色
     */
    private final XSSFColor titleFontColor;

    /**
     * 数据隔行色，浅
     */
    private final XSSFColor contentOneColor;

    /**
     * 数据隔行色，深
     */
    private final XSSFColor contentTwoColor;

    /**
     * 内容字体颜色
     */
    private final XSSFColor contentFrontColor;

    /**
     * 边框颜色
     */
    private final XSSFColor borderColor;


}
