package mits;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import util.Constants;
import util.ResourceLoader;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import exception.CustomizedException;

/**
 * this class is created for Feild validation Using Selenium
 **/
public class FeildValidation {
	public static Logger logger = Logger.getLogger(FeildValidation.class);
	static ResourceLoader loader = new ResourceLoader();
	static Workbook wbookFieldvalidation;
	static XSSFSheet worksheet;
	static WritableWorkbook wwbCopyFieldvalidation;
	static Sheet shSheet;
	static Row row1;
	static XSSFRow row2;
	static Cell cellA1;
	static Cell cellA2, cellA3;
	static CellStyle cellStyle;
	static FileOutputStream fileOut;
	static XSSFWorkbook workbook;
	static String value;
	static WebElement elementName;
	static String mandatory;
	static String fieldvalue;
	static String fieldLabel;
	static org.apache.poi.ss.usermodel.Workbook wb;
	static int column;
	static int clm;
	static int cal;
	static int rowFieldvalidation;
	static String originalFilename;
	static String copyFilename;
	static int increment = 0;

	/**
	 * @param args
	 *            created by sai chander reddy gudipati for field validation
	 * @throws IOException
	 * @throws BiffException
	 */

	public static void gettingData(WebDriver driver, int row, int rowcount) throws BiffException, IOException {
		rowFieldvalidation = 1 + row;
		// System.out.println("Row value:"+rowFieldvalidation);
		if (increment == 0) {
			readExcelFieldValidation(driver);
			increment = increment + 1;
		}
		try {

			System.out.println("*/-*/**/*/*/");
			column = 6;
			clm = 3;
			cal = 1;
			for (int label = 0; label <= 50; label++) {
				WebElement element1 = driver.findElement(By.xpath(".//*[@id='pvr_widget_Property_" + label + "']/th"));
				String Style = element1.getAttribute("style");
				element1.getCssValue("");
				if (Style.equals("")) {
					fieldLabel = element1.getText();
					// System.out.println("FieldLabel: "+fieldLabel);
					if (fieldLabel.contains("*")) {
						mandatory = "M";
					}

					WebElement element = driver
							.findElement(By.xpath(".//*[@id='pvr_widget_Property_" + label + "']/td/div/div/div"));
					String id1 = element.getAttribute("id");
					// System.out.println("id:"+id1);
					String idactual = id1.substring(7, id1.length());
					if (idactual.startsWith("pvr_widget_editors_DateTimeTextBoxEditor")) {
						String idactualDate = idactual.replace("DateTimeTextBoxEditor", "extras_WidthDateTextBox");
						String value1 = driver.findElement(By.id(idactualDate)).getAttribute("value");
						String idactualTime = idactual.replace("DateTimeTextBoxEditor", "extras_WidthTimeTextBox");
						String value2 = driver.findElement(By.id(idactualTime)).getAttribute("value");
						fieldvalue = value1 + " " + value2;
						// System.out.println("Value "+fieldvalue);
						String readonly = driver.findElement(By.id(idactualDate)).getAttribute("readonly");
						if (fieldLabel.contains("*")) {
							mandatory = "M";
						} else if (readonly == null) {
							mandatory = "O";
						} else {
							mandatory = "R";
						}

					} else {
						fieldvalue = driver.findElement(By.id(idactual)).getAttribute("value");
						String readonly = driver.findElement(By.id(idactual)).getAttribute("readonly");
						if (fieldLabel.contains("*")) {
							mandatory = "M";
						}

						else if (readonly == null) {
							mandatory = "O";
						} else {
							mandatory = "R";
						}
						// System.out.println("Value "+fieldvalue);
					}
				} else {
					mandatory = "H";
					fieldvalue = " ";
					fieldLabel = "NA";
				}
				if (!mandatory.equals("H")) {
					setValueIntoCell(fieldvalue, mandatory, fieldLabel, rowFieldvalidation);
				}
				if (fieldLabel.startsWith("SSC Approver ID")) {
					if (row == rowcount) {
						closeFileFieldvalidation();
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * static int column =6; static int clm = 3; static int cal =1;
	 */
	public static void readExcelFieldValidation(WebDriver driver) {
		try {
			logger.info("Reading the Excel file");
			// reading the excel file
			wbookFieldvalidation = Workbook.getWorkbook(new File(loader.getProperty(Constants.FieldValidation)));
			wwbCopyFieldvalidation = Workbook.createWorkbook(new File(loader.getProperty(Constants.outputfile)),
					wbookFieldvalidation);
			shSheet = wwbCopyFieldvalidation.getSheet(0);
		} catch (Exception e) {
			// handling the exception
			logger.error("While reading the Excel error :" + e.getMessage());
			new CustomizedException(e, driver);
		}
	}

	public static void setValueIntoCell(String fieldvalue, String mandatory, String fieldLabel,
			int rowFieldvalidation) {
		logger.info("writing the data");

		WritableSheet wshTemp = wwbCopyFieldvalidation.getSheet(0);
		Label fieldvalues = new Label(column, rowFieldvalidation, fieldvalue);
		Label mandatoryData = new Label(clm, rowFieldvalidation, mandatory);
		Label fieldLabelData = new Label(cal, rowFieldvalidation, fieldLabel);
		try {
			wshTemp.addCell(fieldvalues);
			wshTemp.addCell(mandatoryData);
			wshTemp.addCell(fieldLabelData);
			column += 7;
			clm += 7;
			cal += 7;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public static void closeFileFieldvalidation() {
		try {
			// Closing the writable work book
			wwbCopyFieldvalidation.write();
			wwbCopyFieldvalidation.close();

			// Closing the original work book
			wbookFieldvalidation.close();
		} catch (Exception e)

		{
			e.printStackTrace();
		}
	}
}
