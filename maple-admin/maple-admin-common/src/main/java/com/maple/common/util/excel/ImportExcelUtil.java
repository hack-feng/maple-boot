package com.maple.common.util.excel;

import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCheckException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/9/16
 */
@Slf4j
public class ImportExcelUtil {

    private ImportExcelUtil() {
    }

    /**
     * 2003- 版本的excel
     */
    private static final String EXCEL_2003L = ".xls";
    /**
     * 2007+ 版本的excel
     */
    private static final String EXCEL_2007U = ".xlsx";

    private static final String GENERAL = "General";

    private static final String M_D_YY = "m/d/yy";

    /**
     * 将流中的Excel数据转成List<Map>
     *
     * @param in       输入流
     * @param fileName 文件名（判断Excel版本）
     * @return EXCEL转化后的数据
     */
    public static List<Map<String, String>> parseExcel(InputStream in, String fileName, Map<String, String> titleKey) throws IOException {
        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        Sheet sheet;
        Row row;
        Cell cell;
        // 返回数据
        List<Map<String, String>> ls = new ArrayList<>();
        // 遍历Excel中所有的sheet
        sheet = work.getSheetAt(0);
        if (sheet == null) {
            return ls;
        }
        // 取第一行标题
        Row topRow = sheet.getRow(0);
        if (null == topRow) {
            return ls;
        }
        row = sheet.getRow(0);
        String[] title;
        if (row == null) {
            return ls;
        }
        title = new String[row.getLastCellNum()];
        for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
            cell = row.getCell(y);
            title[y] = (String) getCellValue(cell);
        }
        // 遍历当前sheet中的所有行
        // 坑：sheet.getLastRowNum()  最后一行行标，比行数小1
        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            row = sheet.getRow(j);
            Map<String, String> m = new HashMap<>(row.getLastCellNum());
            // 遍历所有的列
            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                cell = row.getCell(y);
                String key = title[y].replace(" ", "");
                if (titleKey.get(key) == null) {
                    log.error("导入excel出现错误的标题：" + key);
                    throw new MapleCheckException(ErrorCode.OTHER_ERROR.getCode(), "excel出现错误的标题：" + key);
                }
                m.put(titleKey.get(key), String.valueOf(getCellValue(cell)).trim());
            }
            ls.add(m);
        }
        work.close();
        return ls;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr ,fileName
     * @return 返回的数据
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws IOException {
        Workbook wb;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (EXCEL_2003L.equals(fileType)) {
            // 2003-
            wb = new HSSFWorkbook(inStr);
        } else if (EXCEL_2007U.equals(fileType)) {
            // 2007+
            wb = new XSSFWorkbook(inStr);
        } else {
            throw new MapleCheckException(ErrorCode.OTHER_ERROR.getCode(), "解析的文件格式有误！");
        }
        return wb;
    }

    public static Object getCellValue(Cell cell) {
        Object value = new Object();
        // 日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
            case FORMULA:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (GENERAL.equals(cell.getCellStyle().getDataFormatString())) {
                    double cellValue = cell.getNumericCellValue();
                    value = replace(String.valueOf(cellValue));
                } else if (M_D_YY.equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    DataFormatter formatter = new DataFormatter();
                    // 直接获取到单元格的值
                    value = formatter.formatCellValue(cell);
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    public static String replace(String s) {
        char a = '.';
        if (s.indexOf(a) >= 0) {
            //去掉多余的0  
            s = s.replaceAll("0+$", "");
            //如最后一位是.则去掉  
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }
}
