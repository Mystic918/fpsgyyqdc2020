package app.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfficeUtil {

    public static List<String[]> xlsxToList(InputStream is, int startRow) {
        List<String[]> lists = new ArrayList();
        try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            int rowLength = 0;
            for (Row r : sheet) {
                if(r.getRowNum() == 0){
                    rowLength = r.getLastCellNum();
                }
                if (r.getRowNum() < startRow) continue;
                String[] item = new String[rowLength];
                for (int i = 0; i < rowLength; i++) {
                    Cell cell = r.getCell(i);
                    if(cell == null){
                        item[i] = "";
                        continue;
                    }
                    if(cell.getCellType().toString().equals("NUMERIC")){
                        if(DateUtil.isCellDateFormatted(cell)){
                            Date date = cell.getDateCellValue();
                            DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                            item[i] = formater.format(date);
                        }else if(String.valueOf(cell.getNumericCellValue()).contains(".")){
                            DecimalFormat df = new DecimalFormat("#.##");
                            item[i] = df.format(cell.getNumericCellValue());
                        }else{
                            item[i] = (cell + "").trim();
                        }
                    }else{
                        item[i] = (cell + "").trim();
                    }
                    item[i] = item[i].replaceAll("[\\p{C}]", "");
                }
                lists.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lists;
    }

    public static File createXlsx(String[] header, String[] style, List<String[]> body, String filename) {
        String rootPth = OfficeUtil.class.getResource("/public").getPath();
        File file = new File(rootPth + "/temp/" + filename + ".xlsx");

        initTemp();

        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("sheet1");
            XSSFRow row1 = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                XSSFCell cell = row1.createCell(i);
                cell.setCellValue(header[i]);
            }
            XSSFCellStyle cellStyle = wb.createCellStyle();
            for (int i = 0; i < body.size(); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                String[] items = body.get(i);
                for (int j = 0; j < items.length; j++) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(items[j]);


                    XSSFDataFormat format = wb.createDataFormat();
                    if (style[j] == null || style[j].equals("")) {
                        style[j] = "TEXT";
                    }
                    if (style[j].equals("TEXT")) {
                        cellStyle.setDataFormat(format.getFormat("@"));
                    }
                    cell.setCellStyle(cellStyle);
                }
            }

            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return file;
    }

    public static void initTemp() {
        String rootPth = OfficeUtil.class.getResource("/public").getPath();
        File file = new File(rootPth + "/temp/");

        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }

        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                delFile(new File(file, children[i]));
            }
        }
    }

    public static void delFile(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                delFile(new File(file, children[i]));
            }
        }
        file.delete();
    }
}
