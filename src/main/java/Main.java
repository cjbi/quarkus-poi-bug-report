import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.jboss.logmanager.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author cjbi
 */
@QuarkusMain
public class Main {

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    public static void main(String[] args) {
        Quarkus.run(ExcelDataExporter.class, args);
    }

    public static class ExcelDataExporter implements QuarkusApplication {

        private void output(Workbook workbook) {
            String tempDir = System.getProperty("java.io.tmpdir");
            String path = tempDir + File.separator + "test_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + ".xls";
            LOGGER.info("------------Temp file path:" + path);
            File file = new File(path);
            try (OutputStream os = new FileOutputStream(file)) {
                workbook.write(os);
                workbook.close();
                LOGGER.info("------------Output Successful");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int run(String... args) {
            LOGGER.info("------------Application started");
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet 1");
            sheet.autoSizeColumn(1);
            sheet.setDefaultColumnWidth(20);
            Row headRow = sheet.createRow(0);
            headRow.setHeightInPoints(24);
            int cellNo = 0;
            int cellLength = 20;
            while (cellNo < cellLength) {
                Cell cell = headRow.createCell(cellNo++);
                cell.setCellStyle(title(workbook));
                cell.setCellValue("test name" + cellNo);
            }

            output(workbook);
            Quarkus.waitForExit();
            return 0;
        }

        public CellStyle title(Workbook workbook) {
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("黑体");
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);

            style.setAlignment(HorizontalAlignment.CENTER);          
            style.setVerticalAlignment(VerticalAlignment.CENTER); 
            style.setFont(font);

            return style;
        }

        public CellStyle text(Workbook workbook) {
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 12);

            style.setFont(font);

            style.setAlignment(HorizontalAlignment.LEFT);   
            style.setVerticalAlignment(VerticalAlignment.CENTER); 
            return style;
        }
    }

}
