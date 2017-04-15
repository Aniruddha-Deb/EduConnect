package com.educonnect.admin.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.educonnect.admin.Constants;

public class EditPanel extends JPanel {

	private static final long serialVersionUID = 8043632711448308358L;
	
	private JTabbedPane tabbedPane = null;
	private ImageIcon icon = null;
	private FileInputStream xlsxFIS = null;
	
	public EditPanel() {
		super();
		super.setBackground( Color.WHITE );
		super.setLayout( new BorderLayout() );
		tabbedPane = new JTabbedPane();
		super.add( tabbedPane, BorderLayout.CENTER );		
	}
	
	private Image getScaledImage( Image srcImg, int w, int h ){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	public void load() {
		icon = new ImageIcon( "src/main/resources/db.png", "lool" );
		Image img = icon.getImage();
		Image newImg = getScaledImage( img, 16, 16 );
		icon = new ImageIcon( newImg );
		
		try {
			xlsxFIS = new FileInputStream( new File( Constants.XLSX_FILE_PATH ) );
			XSSFWorkbook workbook = new XSSFWorkbook( xlsxFIS );
			Iterator<XSSFSheet> sheetIterator = workbook.iterator();
		
			while( sheetIterator.hasNext() ) {
				XSSFSheet sheet = sheetIterator.next();
				JPanel sheetPanel = new JPanel();
				String sheetName = sheet.getSheetName();

				System.out.println( sheet.getPhysicalNumberOfRows() );
				System.out.println( sheet.getRow(0).getPhysicalNumberOfCells() ); 
				
				String[] columnNames = new String[sheet.getRow(0).getPhysicalNumberOfCells()];
				String[][] tableData = new String[sheet.getPhysicalNumberOfRows()-1]
												 [sheet.getRow(0).getPhysicalNumberOfCells()];
				Row tableHeaders = sheet.getRow(0);
				Iterator<Cell> tableHeaderIterator = tableHeaders.cellIterator();
				
				for( int i=0; tableHeaderIterator.hasNext(); i++ ) {
					columnNames[i] = tableHeaderIterator.next().getStringCellValue();
				}

				for( int i=0, c=1; c<sheet.getPhysicalNumberOfRows(); i++, c++ ) {
					
					Row r = sheet.getRow( c );
					
					for( int j=0; j<r.getPhysicalNumberOfCells(); j++ ) {
						
						Cell cell = r.getCell( j );
						
						if( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) {
							// quite hacky :P
							tableData[i][j] = cell.getNumericCellValue() + "";
						}
						else if( cell.getCellType() == Cell.CELL_TYPE_STRING ) {
							tableData[i][j] = cell.getStringCellValue();
						}
					}
				}
				
				JTable table = new JTable( tableData, columnNames );
				JScrollPane scrollPane = new JScrollPane( table );
				table.setFillsViewportHeight( true );
				sheetPanel.add( scrollPane );
				
				tabbedPane.addTab( sheetName, icon, sheetPanel, sheetName );
			}
			
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
}
