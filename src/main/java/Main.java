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
import java.util.Objects;

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
            File file = new File(path);
            try (OutputStream os = new FileOutputStream(file)) {
                workbook.write(os);
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int run(String... args) {
            LOGGER.info("Application started");
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet 1");
            // 自适应宽度
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

        /**
         * 小标题的样式
         *
         * @param workbook
         * @return
         */
        public CellStyle title(Workbook workbook) {
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("黑体");
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);

            style.setAlignment(HorizontalAlignment.CENTER);          //横向居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);    //纵向居中
            style.setFont(font);

            return style;
        }

        private void setCellValue(Cell cell, Object value) {
            cell.setCellValue(Objects.toString(value, ""));
        }

        /**
         * 文字样式
         *
         * @param workbook
         * @return
         */
        public CellStyle text(Workbook workbook) {
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 12);

            style.setFont(font);

            style.setAlignment(HorizontalAlignment.LEFT);          //横向居左
            style.setVerticalAlignment(VerticalAlignment.CENTER);    //纵向居中
            return style;
        }
    }

}
