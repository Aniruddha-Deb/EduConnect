package com.educonnect.admin.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.UIConstants;

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
	
	public void load() {
		icon = new ImageIcon( "src/main/resources/db.png", "lool" );

		try {
			xlsxFIS = new FileInputStream( new File( Constants.XLSX_FILE_PATH ) );
			XSSFWorkbook workbook = new XSSFWorkbook( xlsxFIS );
			Iterator<XSSFSheet> sheetIterator = workbook.iterator();
		
			while( sheetIterator.hasNext() ) {
				XSSFSheet sheet = sheetIterator.next();
				JPanel sheetPanel = new JPanel( new BorderLayout() );
				sheetPanel.setBackground( Color.WHITE );
				String sheetName = sheet.getSheetName();

				System.out.println( sheet.getPhysicalNumberOfRows() );
				System.out.println( sheet.getRow(0).getPhysicalNumberOfCells() ); 
				
				String[] columnNames = new String[sheet.getRow(0).getPhysicalNumberOfCells()];
				String[][] tableData = new String[100]
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
							tableData[i][j] = (int)cell.getNumericCellValue() + "";
						}
						else if( cell.getCellType() == Cell.CELL_TYPE_STRING ) {
							tableData[i][j] = cell.getStringCellValue();
						}
					}
				}
				
				JTable table = new JTable( tableData, columnNames );
				table.setDefaultRenderer( Object.class, new CustomRenderer( table ) );
				table.setGridColor( Color.GRAY );
				table.setFont( UIConstants.FONT.deriveFont( 13f ) );
				table.getTableHeader().setBorder(new MatteBorder(0,0,1,0, Color.BLACK));
				table.getTableHeader().setFont( UIConstants.FONT.deriveFont( 13f ) );
				table.setRowHeight( 20 );
				
				JScrollPane scrollPane = new JScrollPane( table );
				table.setFillsViewportHeight( true );
				sheetPanel.add( scrollPane, BorderLayout.CENTER );
				
				JTextField field = new JTextField();
				field.setFont( UIConstants.FONT.deriveFont( 13f ) );
				field.setBorder( new EmptyBorder( 1, 1, 1, 1 ) );
				field.setBackground( new Color( 92, 170, 248 ) );
				field.setForeground( Color.WHITE );
				DefaultCellEditor editor = new DefaultCellEditor( field );
				editor.setClickCountToStart( 1 );
				
				for (int i = 0; i < table.getColumnCount(); i++) {
					table.setDefaultEditor( table.getColumnClass(i), editor );
				} 
				tabbedPane.addTab( sheetName, icon, sheetPanel, sheetName );
			}
			
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public static class CustomRenderer implements TableCellRenderer{
		TableCellRenderer render;
		Border b;
		
		private CustomRenderer( JTable t ) {
			this.render = t.getDefaultRenderer( Object.class );
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JComponent result = (JComponent)render.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if( isSelected ) {
				result.setBackground( Color.LIGHT_GRAY );
				result.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
			}
			else {
				result.setBackground( Color.WHITE );
				result.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
			}
			
			if( hasFocus ) {
				result.setBackground( new Color( 92, 170, 248) );
				result.setForeground( Color.WHITE );
				result.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
			}
			else {
				result.setForeground( Color.BLACK );				
				result.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
			}
			
			return result;
		}
	}
}

