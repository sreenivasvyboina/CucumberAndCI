package mits;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constants;
import util.ResourceLoader;

public class ReadandWriteExcel {
	 static ResourceLoader loader = new ResourceLoader();
	 static HSSFWorkbook workbook;
	 static HSSFWorkbook myWorkBook;
	 static HSSFSheet sheet;
	 static HSSFRow row;
	 static HSSFCell cell;
	 static HSSFSheet mySheet;
	 static HSSFRow myRow;
	 static HSSFCell myCell ;
	 static int fCell = 0;
	 static int lCell = 0;
	 static int fRow = 0;
	 static int lRow = 0;
    public static void copyExcel() throws IOException {
       FileInputStream bis = new FileInputStream(loader.getProperty(Constants.FieldValidation));
       workbook = new HSSFWorkbook(bis);
       myWorkBook = new HSSFWorkbook();
        int sheets = workbook.getNumberOfSheets();
        
        for (int iSheet = 0; iSheet < sheets; iSheet++) {
            sheet = workbook.getSheetAt(iSheet);
            if (sheet != null) {
                mySheet = myWorkBook.createSheet("Testing");
                fRow = sheet.getFirstRowNum();
                lRow = sheet.getLastRowNum();
                for (int iRow = fRow; iRow <= lRow; iRow++) {
                    row = sheet.getRow(iRow);
                    myRow = mySheet.createRow(iRow);
                    if (row != null) {
                        fCell = row.getFirstCellNum();
                        lCell = row.getLastCellNum();
                        for (int iCell = fCell; iCell < lCell; iCell++) {
                            cell = row.getCell(iCell);
                            myCell = myRow.createCell(iCell);
                            if (cell != null) {
                                myCell.setCellType(cell.getCellType());
                                switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_BLANK:
                                    myCell.setCellValue("");
                                    break;

                                case HSSFCell.CELL_TYPE_BOOLEAN:
                                    myCell.setCellValue(cell.getBooleanCellValue());
                                    break;

                                case HSSFCell.CELL_TYPE_ERROR:
                                    myCell.setCellErrorValue(cell.getErrorCellValue());
                                    break;

                                case HSSFCell.CELL_TYPE_FORMULA:
                                    myCell.setCellFormula(cell.getCellFormula());
                                    break;

                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    myCell.setCellValue(cell.getNumericCellValue());
                                    break;

                                case HSSFCell.CELL_TYPE_STRING:
                                    myCell.setCellValue(cell.getStringCellValue());
                                    break;
                                default:
                                    myCell.setCellFormula(cell.getCellFormula());
                                }
                            }
                        }
                    }
                }
            }
        }
        bis.close();
        FileOutputStream bos = new FileOutputStream(loader.getProperty(Constants.outputfile), true);
        myWorkBook.write(bos);
        bos.close();
        System.out.println("file has been closed");
    }
    public static void main(String[] args) throws IOException {
		copyExcel();
	}
}
