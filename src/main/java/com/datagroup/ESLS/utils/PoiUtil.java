package com.datagroup.ESLS.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PoiUtil {
    /**
     * CSV文件列分隔符
     */
    private static final String CSV_COLUMN_SEPARATOR = ",";

    /**
     * CSV文件行分隔符
     */
    private static final String CSV_RN = "\r\n";

    public static HSSFWorkbook exportData2Excel(List dataList, List columns, String tableName) {
        //1.创建Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel表单
        HSSFSheet sheet = workbook.createSheet(tableName + "信息表");
        //创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        // 设置表头
        HSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            HSSFCell cell0 = headerRow.createCell(i);
            cell0.setCellValue(columns.get(i).toString());
            cell0.setCellStyle(headerStyle);
        }
        // 设置数据
        for (int i = 0; i < dataList.size(); i++) {
            Object data = dataList.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < columns.size(); j++) {
                try {
                    Cell cell = row.createCell(j);
                    String column = columns.get(j).toString();
                    boolean flag = false;
                    // 列名以id结尾且不是主键
                    if (column.charAt(column.length() - 2) == 'i' && column.charAt(column.length() - 1) == 'd' && column.length() > 2) {
                        column = column.substring(0, column.length() - 2);
                        flag = true;
                    }
                    Field field = data.getClass().getDeclaredField(column);
                    //设置对象的访问权限，保证对private的属性的访问
                    field.setAccessible(true);
                    if (field.get(data) != null && flag) {
                        Object o = field.get(data);
                        Field fieldItem = o.getClass().getDeclaredField("id");
                        fieldItem.setAccessible(true);
                        // id数据
                        cell.setCellValue(fieldItem.get(o).toString());
                        continue;
                    }
                    cell.setCellValue(field.get(data) != null ? field.get(data).toString() : "null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        setSizeColumn(sheet, columns.size());
        return workbook;
    }

    private static void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                // 当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            // Excel的长度为字节码长度*256,*1.3为了处理数字格式
            columnWidth = (int) Math.floor(columnWidth * 256 * 1.3);
            //单元格长度大于20000的话也不美观,设置个最大长度
            columnWidth = columnWidth >= 20000 ? 20000 : columnWidth;
            //设置每列长度
            sheet.setColumnWidth(columnNum, columnWidth);
        }
    }

    public static void writeToResponse(HSSFWorkbook workbook, HttpServletRequest request,
                                       HttpServletResponse response, String fileName) {
        try {
            String userAgent = request.getHeader("User-Agent");
            // 解决中文乱码问题
            String fileName1 = fileName + "-Excel" + ".xlsx";
            String newFilename = URLEncoder.encode(fileName1, "UTF8");
            // 如果没有userAgent，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
            String rtn = "filename=\"" + newFilename + "\"";
            if (userAgent != null) {
                userAgent = userAgent.toLowerCase();
                // IE浏览器，只能采用URLEncoder编码
                if (userAgent.indexOf("IE") != -1) {
                    rtn = "filename=\"" + newFilename + "\"";
                }
                // Opera浏览器只能采用filename*
                else if (userAgent.indexOf("OPERA") != -1) {
                    rtn = "filename*=UTF-8''" + newFilename;
                }
                // Safari浏览器，只能采用ISO编码的中文输出
                else if (userAgent.indexOf("SAFARI") != -1) {
                    rtn = "filename=\"" + new String(fileName1.getBytes("UTF-8"), "ISO8859-1")
                            + "\"";
                }
                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                else if (userAgent.indexOf("FIREFOX") != -1) {
                    rtn = "filename*=UTF-8''" + newFilename;
                }
            }
            String headStr = "attachment;  " + rtn;
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", headStr);
            // 响应到客户端
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportData2Csv(List dataList, List columns, OutputStream os) {
        StringBuffer buf = new StringBuffer();
        for (Object item : columns) {
            buf.append(item.toString()).append(CSV_COLUMN_SEPARATOR);
        }
        buf.append(CSV_RN);
        try {
            // 设置数据
            for (int i = 0; i < dataList.size(); i++) {
                Object data = dataList.get(i);
                for (int j = 0; j < columns.size(); j++) {
                    String column = columns.get(j).toString();
                    boolean flag = false;
                    // 列名以id结尾且不是主键
                    if (column.charAt(column.length() - 2) == 'i' && column.charAt(column.length() - 1) == 'd' && column.length() > 2) {
                        column = column.substring(0, column.length() - 2);
                        flag = true;
                    }
                    Field field = data.getClass().getDeclaredField(column);
                    //设置对象的访问权限，保证对private的属性的访问
                    field.setAccessible(true);
                    if (field.get(data) != null && flag) {
                        Object o = field.get(data);
                        Field fieldItem = o.getClass().getDeclaredField("id");
                        fieldItem.setAccessible(true);
                        buf.append(fieldItem.get(o).toString()).append(CSV_COLUMN_SEPARATOR);
                        continue;
                    }
                    buf.append(field.get(data) != null ? field.get(data).toString() : "null").append(CSV_COLUMN_SEPARATOR);
                }
                buf.append(CSV_RN);
            }
            // 写出响应
            os.write(buf.toString().getBytes("UTF-8"));
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fn = fileName + "-" + sdf.format(new Date()) + ".csv";
        // 读取字符编码
        String utf = "UTF-8";
        // 设置响应
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
    }
}
