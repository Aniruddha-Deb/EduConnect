package com.sensei.poc;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelReader {

	@SuppressWarnings("deprecation")
	public static void main( String[] args ) throws Exception{
		
		FileInputStream fis = new FileInputStream( new File( "/Users/Sensei/temp/xssftest.xlsx" ) );
		XSSFWorkbook workbook = new XSSFWorkbook( fis );
		XSSFSheet sheet = workbook.getSheetAt( 0 );
		
		Iterator<Row> rowIterator = sheet.iterator();
		
		while( rowIterator.hasNext() ) {
			Row r = rowIterator.next();
			
			Iterator<Cell> cellIterator = r.cellIterator();
			
			while( cellIterator.hasNext() ) {
				Cell c = cellIterator.next();
				
				if( c.getCellTypeEnum() == CellType.STRING ) {
					System.out.print( c.getStringCellValue() + "  |  " );
				}
				else if( c.getCellTypeEnum() == CellType.NUMERIC ) {
					System.out.print( c.getNumericCellValue() + "  |  " );
				}
			}
			System.out.println();
		}
		
		workbook.close();
		fis.close();
	}

}
