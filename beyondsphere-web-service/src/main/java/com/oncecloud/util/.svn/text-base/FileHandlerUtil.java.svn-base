package com.beyondsphere.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.beyondsphere.entity.OCLog;
import com.beyondsphere.model.LogConstant;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class FileHandlerUtil {
  
    public static String exportFile(List<OCLog> logList, String filename, String downType) {  
        //此处模拟数据库读出的数据。在真正的项目中。我们可以通过在session中保存的前端数据集合替换这里  
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        if ("PDF".equals(downType)) {  
            try {  
                FileOutputStream out = new FileOutputStream("src/main/webapp/file/log.pdf");  
                Document document = new Document(PageSize.A4, 50, 50, 50, 50);  
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                PdfWriter.getInstance(document, out);  
                document.open();  
                // 创建PDF表格  
                PdfPTable table = new PdfPTable(4);  
                // 设置pdf表格的宽度  
                table.setTotalWidth(500);  
                // 设置是否要固定其宽度  
                table.setLockedWidth(true);  
                // 表头字体  
                Font thfont = new Font();  
                // 设置表头字体的大小  
                thfont.setSize(7);  
                // 设置表头字体的样式  
                thfont.setStyle(Font.BOLD);  
                Font tdfont = new Font();  
                tdfont.setSize(7);  
                tdfont.setStyle(Font.NORMAL);  
                // 设置水平对齐方式  
                table.setHorizontalAlignment(Element.ALIGN_MIDDLE);  
                // 设置table的header  
                table.addCell(new Paragraph("logId", thfont));  
                table.addCell(new Paragraph("logObject", thfont));  
                table.addCell(new Paragraph("logAction", thfont));  
                table.addCell(new Paragraph("logInfo", thfont));  
                table.addCell(new Paragraph("logTime", thfont));  
                // 循环设置table的每一行  
                for (int i = 0; i < logList.size(); i++) {  
                    OCLog log = (OCLog) logList.get(i);  
                    table.addCell(new Paragraph(log.getLogId().toString(), tdfont));  
                    table.addCell(new Paragraph(LogConstant.logObject.values()[log.getLogObject()]
    								.toString(), tdfont));  
                    table.addCell(new Paragraph(LogConstant.logAction.values()[log.getLogAction()]
    								.toString(), tdfont));  
                    table.addCell(new Paragraph(log.getLogInfo(), tdfont));  
                    table.addCell(new Paragraph(format.format(log.getLogTime()), tdfont));
                }  
                document.add(table);  
                document.close();  
                baos.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        } else if ("CSV".equals(downType)) { 
        	OutputStreamWriter writer;
        	//FileWriter writer;
            String sep = ",";  
            try {  
                writer = new OutputStreamWriter(new FileOutputStream(new File("src/main/webapp/file/log.csv"), true)); 
                writer.write("logId");  
                writer.write(sep);  
                writer.write("logObject");  
                writer.write(sep);  
                writer.write("logAction");  
                writer.write(sep);  
                writer.write("logInfo");  
                writer.write(sep);  
                writer.write("logTime");  
                writer.write(sep); 
                writer.write(System.getProperty("line.separator"));  
                for (int i = 0; i < logList.size(); i++) {  
                    OCLog log = (OCLog) logList.get(i);  
                    writer.write(log.getLogId().toString());  
                    writer.write((sep + "/t"));  
                    writer.write(LogConstant.logObject.values()[log.getLogObject()]
    								.toString());  
                    writer.write((sep + "/t"));  
                    writer.write(LogConstant.logAction.values()[log.getLogAction()]
    								.toString());  
                    writer.write((sep + "/t"));  
                    writer.write(log.getLogInfo());  
                    writer.write((sep + "/t"));  
                    writer.write(format.format(log.getLogTime()));  
                    writer.write((sep + "/t"));  
                    writer.write(sep);  
                    writer.write(System.getProperty("line.separator"));  
                }  
                writer.flush();  
                //out.cloute();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        } else if (downType.equals("Excel")) {  
            try {  
                Label l = null;  
                // 写sheet名称  
                WorkbookSettings setting = new WorkbookSettings();  
                setting.setEncoding("utf-8");
                WritableWorkbook wbook = Workbook.createWorkbook(new File(  
                        "src/main/webapp/file/log.xls"), setting);  
                WritableSheet sheet = wbook.createSheet("log", 0);
                jxl.write.WritableFont wfc4 = new jxl.write.WritableFont(  
                        WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,  
                        jxl.format.UnderlineStyle.NO_UNDERLINE,  
                        jxl.format.Colour.BLACK);  
                jxl.write.WritableCellFormat wcfFC4 = new jxl.write.WritableCellFormat(  
                        wfc4);  
                wcfFC4.setBackground(jxl.format.Colour.LIGHT_GREEN);  

                int col = 0;  
                sheet.setColumnView(col, 12);  
                l = new Label(col, 0, "logId", wcfFC4);  
                sheet.addCell(l);  
                col++;  
                sheet.setColumnView(col, 12);  
                l = new Label(col, 0, "logObject", wcfFC4);  
                sheet.addCell(l);  
                col++;  
                sheet.setColumnView(col, 12);  
                l = new Label(col, 0, "logAction", wcfFC4);  
                sheet.addCell(l);  
                col++;  
                sheet.setColumnView(col, 12);  
                l = new Label(col, 0, "logInfo", wcfFC4);  
                sheet.addCell(l);  
                col++;  
                sheet.setColumnView(col, 12);  
                l = new Label(col, 0, "logTime", wcfFC4);  
                sheet.addCell(l);  
  
                // 设置字体样式  
                jxl.write.WritableFont wfc5 = new jxl.write.WritableFont(  
                        WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,  
                        jxl.format.UnderlineStyle.NO_UNDERLINE,  
                        jxl.format.Colour.BLACK);  
                jxl.write.WritableCellFormat wcfFC5 = new jxl.write.WritableCellFormat(  
                        wfc5);  
                for (int i = 0; i < logList.size(); i++) {  
                    OCLog log = (OCLog) logList.get(i);  
                    int j = 0;  
                    l = new Label(j, i + 1, log.getLogId().toString(), wcfFC5);  
                    sheet.addCell(l);  
                    j++;  
                    l = new Label(j, i + 1, LogConstant.logObject.values()[log.getLogObject()]
    								.toString(), wcfFC5);  
                    sheet.addCell(l);  
                    j++;  
                    l = new Label(j, i + 1, LogConstant.logAction.values()[log.getLogAction()]
    								.toString(), wcfFC5);  
                    sheet.addCell(l);  
                    j++;  
                    l = new Label(j, i + 1, log.getLogInfo(), wcfFC5);  
                    sheet.addCell(l);  
                    j++;  
                    l = new Label(j, i + 1, format.format(log.getLogTime()), wcfFC5);  
                    sheet.addCell(l);  
                    j++;  
                }  
                // 写入流中  
                wbook.write();  
                wbook.close();  
  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return filename;  
    }  
}
