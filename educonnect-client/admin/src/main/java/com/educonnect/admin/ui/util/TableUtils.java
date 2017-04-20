package com.educonnect.admin.ui.util;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class TableUtils {
	
	public static String[] scrapeHeaders( JTable table ) {
	    TableColumnModel tcm = (TableColumnModel)table.getColumnModel();
	    int numCols = tcm.getColumnCount();
	    String[] tableHeaders = new String[numCols];
	    
	    for( int i=0; i<numCols; i++ ) {
	    	tableHeaders[i] = (String)tcm.getColumn(i).getHeaderValue();
	    }
	    return tableHeaders;
	}

	public static String[][] scrapeData( JTable table ) {
		
	    TableModel dtm = (TableModel)table.getModel();
	    int numRows = dtm.getRowCount(); 
	    int numCols = dtm.getColumnCount();
	    String[][] tableData = new String[numRows][numCols];
	    
	    for ( int i=0; i<numRows; i++ ) {
	        for ( int j=0; j<numCols; j++ ) {	        	
	        	tableData[i][j] = (String)dtm.getValueAt(i,j);
	        }
	    }
	    return tableData;
	}	
}
