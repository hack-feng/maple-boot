package com.maple.common.util.excel;

import com.maple.common.util.excel.bean.ExportTableBean;
import com.maple.common.util.excel.bean.ExportExcelTheme;
import com.maple.common.util.excel.bean.ExportSheetBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/9/12
 */
public class ExportExcelUtil {

    private ExportExcelUtil() {
    }

    /**
     * 这是一个通用的方法，导出excel2007版，后缀为.xlsx。
     * 单Sheet页导出
     *
     * @param table excel基础信息
     * @param out   可以将EXCEL文档导出到本地文件或者网络中
     */
    public static void exportExcel(ExportTableBean table, OutputStream out) {
        List<ExportTableBean> exportExcelBeans = new ArrayList<>();
        exportExcelBeans.add(table);
        ExportSheetBean sheetBean = new ExportSheetBean();
        sheetBean.setSheetName("sheet1");
        sheetBean.setList(exportExcelBeans);
        exportExcelOneSheetMoreTable(sheetBean, out);
    }

    /**
     * 这是一个通用的方法，导出excel2007版，后缀为.xlsx。
     *
     * @param table     excel基础信息
     * @param sheetName sheet自定义名称
     * @param passWord  sheet页加密密码，无需加密传null即可
     * @param out       可以将EXCEL文档导出到本地文件或者网络中
     */
    public static void exportExcel(ExportTableBean table, String sheetName, String passWord, OutputStream out) {
        List<ExportTableBean> exportExcelBeans = new ArrayList<>();
        exportExcelBeans.add(table);
        ExportSheetBean sheetBean = new ExportSheetBean();
        sheetBean.setSheetName(sheetName);
        sheetBean.setProtectSheet(passWord);
        sheetBean.setList(exportExcelBeans);
        exportExcelOneSheetMoreTable(sheetBean, out);
    }

    /**
     * 这是一个通用的方法，导出excel2007版，后缀为.xlsx。
     * 多Sheet页导出，多sheet页中对应多table
     *
     * @param out 可以将EXCEL文档导出到本地文件或者网络中
     */
    public static void exportExcelOneSheetMoreTable(ExportSheetBean sheet, OutputStream out) {
        // 声明一个工作薄
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            creatMoreTableSheet(sheet, workbook, 1);
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这是一个通用的方法，导出excel2007版，后缀为.xlsx。
     * 多Sheet页导出，多sheet页中对应多table
     *
     * @param out 可以将EXCEL文档导出到本地文件或者网络中
     */
    public static void exportExcelMoreSheetMoreTable(List<ExportSheetBean> list, OutputStream out) {
        // 声明一个工作薄
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            for (int i = 0; i < list.size(); i++) {
                creatMoreTableSheet(list.get(i), workbook, i);
            }
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建excel
     */
    private static void creatMoreTableSheet(ExportSheetBean sheetBean, XSSFWorkbook workbook, int i) {
        List<ExportTableBean> list = sheetBean.getList();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(sheetBean.getSheetName() == null ? "sheet" + i : sheetBean.getSheetName());
        // 设置表格默认列宽度为16个字节
        sheet.setDefaultColumnWidth(16);

        int listCount = 0;
        for (ExportTableBean table : list) {
            // 设置主题样式，默认DEFAULT
            ExportExcelTheme excelTheme = table.getTheme();
            if (excelTheme == null) {
                excelTheme = ExportExcelTheme.DEFAULT;
            }
            XSSFCellStyle titleStyle = createTitleStyle(workbook,
                    excelTheme.getTitleBackgroundColor(),
                    excelTheme.getTitleFontColor(),
                    excelTheme.getBorderColor());
            XSSFCellStyle titleStyleOne = createLineStyle(workbook,
                    excelTheme.getContentOneColor(),
                    excelTheme.getContentFrontColor(),
                    excelTheme.getBorderColor());
            XSSFCellStyle titleStyleTwo = createLineStyle(workbook,
                    excelTheme.getContentTwoColor(),
                    excelTheme.getContentFrontColor(),
                    excelTheme.getBorderColor());

            // 产生表格标题行
            createTitle(table, sheet, titleStyle, listCount);

            // 产生表格数据
            createExcelData(table, sheet, titleStyleOne, titleStyleTwo, listCount);

            // 与下一个表格之间空出两行
            listCount = listCount + table.getDataList().size() + 2;
        }

        // 设置sheet页是否加密
        if (StringUtils.isNotBlank(sheetBean.getProtectSheet())) {
            sheet.protectSheet(sheetBean.getProtectSheet());
        }
    }

    /**
     * 设置表格的标题
     */
    private static void createTitle(ExportTableBean table, XSSFSheet sheet, XSSFCellStyle titleStyle, int listCount) {
        // 用于标志横向合并标题
        Integer horizontalNum = null;
        Object horizontalValue = null;
        XSSFRow row = sheet.createRow(listCount);
        for (int i = 0; i < table.getHeaders().size(); i++) {
            sheet.setColumnWidth(i, 256 * table.getWidth().get(i) + 184);
            XSSFCell cell = row.createCell(i);
            // 设置标题样式
            cell.setCellStyle(titleStyle);

            XSSFRichTextString text = new XSSFRichTextString(table.getHeaders().get(i));
            cell.setCellValue(text);

            // 横向合并标题
            if (table.getHorizontalMergerColumnHeaders() != null
                    && table.getHorizontalMergerColumnHeaders().contains(table.getHeaders().get(i))) {
                if (horizontalNum == null) {
                    horizontalNum = i;
                    horizontalValue = table.getHeaders().get(i);
                } else {
                    // 当前列与前一列不相等，开启合并，并重新赋值
                    if (!horizontalValue.equals(table.getHeaders().get(i))) {
                        if (i - horizontalNum > 1) {
                            CellRangeAddress cra = new CellRangeAddress(listCount, listCount, horizontalNum, i - 1);
                            sheet.addMergedRegion(cra);
                        }
                        horizontalNum = i;
                        horizontalValue = table.getHeaders().get(i);
                        // 当前为最后一列，开启合并
                    } else if (i == table.getHeaders().size() - 1 && i - horizontalNum >= 1) {
                        CellRangeAddress cra = new CellRangeAddress(listCount, listCount, horizontalNum, i);
                        sheet.addMergedRegion(cra);
                    }
                }
            } else {
                if (horizontalNum != null && i - horizontalNum > 1) {
                    CellRangeAddress cra = new CellRangeAddress(listCount, listCount, horizontalNum, i - 1);
                    sheet.addMergedRegion(cra);
                } else {
                    horizontalNum = null;
                    horizontalValue = null;
                }
            }
        }
    }

    /**
     * 设置表格的数据内容
     */
    private static void createExcelData(ExportTableBean table, XSSFSheet sheet, XSSFCellStyle titleStyleOne, XSSFCellStyle titleStyleTwo, int listCount) {
        // 用于标注竖向合并数据
        Integer[] verticalNum = new Integer[table.getKeys().size()];
        Object[] verticalValue = new Object[table.getKeys().size()];
        //循环放置表格中的值
        for (int i = 0; i < table.getDataList().size(); i++) {
            int line = i + listCount + 1;
            XSSFRow row = sheet.createRow(line);
            //产生序号，1,2,3,4,5...的递增序号，不需要，header去掉‘序号’就可以了
            if ("序号".equals(table.getHeaders().get(0))) {
                XSSFCell cell = row.createCell(0);
                // 设置隔行样式
                if (i % 2 == 0) {
                    cell.setCellStyle(titleStyleOne);
                } else {
                    cell.setCellStyle(titleStyleTwo);
                }
                cell.setCellValue(i + 1 + "");
            }

            Map<String, Object> obj = table.getDataList().get(i);
            for (int j = 0; j < table.getKeys().size(); j++) {
                if ("order".equals(table.getKeys().get(j))) {
                    continue;
                }
                XSSFCell cell = row.createCell(j);
                // 设置隔行样式
                if (i % 2 == 0) {
                    cell.setCellStyle(titleStyleOne);
                } else {
                    cell.setCellStyle(titleStyleTwo);
                }
                if (obj.get(table.getKeys().get(j)) == null) {
                    cell.setCellValue("");
                } else {
                    cell.setCellValue(obj.get(table.getKeys().get(j)) + "");
                }

                // 纵向合并数据
                if (table.getVerticalMergerColumnHeaders() != null
                        && table.getVerticalMergerColumnHeaders().contains(table.getHeaders().get(j))) {
                    if (verticalNum[j] == null) {
                        verticalNum[j] = line;
                        verticalValue[j] = obj.get(table.getKeys().get(j));
                    } else {
                        // 当前列与前一列不相等，开启合并之前数据，并重新赋值
                        if (verticalValue[j] != null && !verticalValue[j].equals(obj.get(table.getKeys().get(j)))) {
                            if (line - verticalNum[j] > 1) {
                                CellRangeAddress cra = new CellRangeAddress(verticalNum[j], line - 1, j, j);
                                sheet.addMergedRegion(cra);
                            }
                            verticalNum[j] = line;
                            verticalValue[j] = obj.get(table.getKeys().get(j));
                            // 当前为最后一列，开启合并
                        } else if (i == table.getDataList().size() - 1 && line - verticalNum[j] >= 1) {
                            CellRangeAddress cra = new CellRangeAddress(verticalNum[j], line, j, j);
                            sheet.addMergedRegion(cra);
                        }
                    }
                } else {
                    if (verticalNum[j] != null && line - verticalNum[j] > 1) {
                        CellRangeAddress cra = new CellRangeAddress(verticalNum[j], line - 1, j, j);
                        sheet.addMergedRegion(cra);
                    } else {
                        verticalNum[j] = null;
                        verticalValue[j] = null;
                    }
                }
            }
        }
    }

    /**
     * 设置标题的样式
     */
    private static XSSFCellStyle createTitleStyle(XSSFWorkbook workbook, XSSFColor bgColor, XSSFColor fontColor, XSSFColor borderColor) {
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(bgColor);
        createBorder(style, borderColor);
        // 生成一个字体
        short fontSize = 13;
        XSSFFont font = createFont(workbook, fontColor, fontSize, true);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    /**
     * 设置样式
     */
    private static XSSFCellStyle createLineStyle(XSSFWorkbook workbook, XSSFColor bgColor, XSSFColor fontColor, XSSFColor borderColor) {
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(bgColor);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        createBorder(style, borderColor);

        createBorder(style, borderColor);
        // 生成一个字体
        short fontSize = 12;
        XSSFFont font = createFont(workbook, fontColor, fontSize, false);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    /**
     * 设置边框样式
     */
    private static void createBorder(XSSFCellStyle style, XSSFColor borderColor) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(borderColor);
    }

    /**
     * 生成字体样式
     *
     * @return 字体样式
     */
    private static XSSFFont createFont(XSSFWorkbook workbook, XSSFColor color, short fontSize, boolean isBold) {
        XSSFFont font = workbook.createFont();
        font.setColor(color);
        font.setFontHeightInPoints(fontSize);
        font.setBold(isBold);
        return font;
    }


    /**
     * 根据浏览器处理导出文件的名字
     *
     * @param exportFileName 文件名字
     */
    public static void updateNameUnicode(HttpServletRequest request, HttpServletResponse response, String exportFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel");
        //根据浏览器类型处理文件名称
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        String firefox = "firefox";
        //若是火狐
        if (agent.contains(firefox)) {
            exportFileName = new String(exportFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else {//其他浏览器
            exportFileName = java.net.URLEncoder.encode(exportFileName, "UTF-8");
        }
        //保存导出的excel的名称
        response.setHeader("Content-Disposition", "attachment;filename=" + exportFileName + ".xlsx");
    }


    public static void testExcel(OutputStream out) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("测试");

            String[] name = {"NO_FILL",
                    "SOLID_FOREGROUND",
                    "FINE_DOTS",
                    "ALT_BARS",
                    "SPARSE_DOTS",
                    "THICK_HORZ_BANDS",
                    "THICK_VERT_BANDS",
                    "THICK_BACKWARD_DIAG",
                    "THICK_FORWARD_DIAG",
                    "BIG_SPOTS",
                    "BRICKS",
                    "THIN_HORZ_BANDS",
                    "THIN_VERT_BANDS",
                    "THIN_BACKWARD_DIAG",
                    "THIN_FORWARD_DIAG",
                    "SQUARES",
                    "DIAMONDS",
                    "LESS_DOTS",
                    "LEAST_DOTS"};

            String[] name2 = {
                    "GENERAL",
                    "LEFT",
                    "CENTER",
                    "RIGHT",
                    "FILL",
                    "JUSTIFY",
                    "CENTER_SELECTION",
                    "DISTRIBUTED"
            };

            String[] name3 = {
                    "TOP",
                    "CENTER",
                    "BOTTOM",
                    "JUSTIFY",
                    "DISTRIBUTED"
            };


            HorizontalAlignment[] aaa = HorizontalAlignment.values();
            VerticalAlignment[] bbb = VerticalAlignment.values();
            for (int i = 0; i < 19; i++) {
                sheet.setColumnWidth(i, 256 * 20 + 184);
                XSSFRow row = sheet.createRow(i);
                row.setHeight((short) (30 * 20));
                // 生成一个样式
                XSSFCellStyle style = workbook.createCellStyle();
                style.setFillPattern(FillPatternType.forInt(i));
                XSSFCell cell = row.createCell(0);
                cell.setCellStyle(style);

                style.setFillForegroundColor(new XSSFColor(new Color(237, 125, 49), new DefaultIndexedColorMap()));
                XSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(name[i]);
                if (i < 8) {
                    XSSFCellStyle style2 = workbook.createCellStyle();
                    style2.setAlignment(aaa[i]);
                    XSSFCell cell2 = row.createCell(3);
                    cell2.setCellStyle(style2);
                    cell2.setCellValue("水平对齐方式");

                    XSSFCell cell3 = row.createCell(4);
                    cell3.setCellValue(name2[i]);
                }

                if (i < 5) {
                    XSSFCellStyle style3 = workbook.createCellStyle();
                    style3.setVerticalAlignment(bbb[i]);
                    XSSFCell cell2 = row.createCell(6);
                    cell2.setCellStyle(style3);
                    cell2.setCellValue("垂直对齐方式");

                    XSSFCell cell3 = row.createCell(7);
                    cell3.setCellValue(name3[i]);
                }
            }
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
