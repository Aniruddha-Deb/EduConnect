package com.educonnect.admin.ui.panels.editpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.admin.ui.util.TableUtils;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.payload.db.DatabasePayload;

public class EditPanel extends JPanel implements ChangeListener, ActionListener{

	private static final long   serialVersionUID         = 8043632711448308358L;
	private static final String DB_RESOURCE              = "src/main/resources/db.png"; 
	private static final String SAVE_TO_DB_RESOURCE      = "src/main/resources/save_to_db.png";
	private static final String EXPORT_TO_EXCEL_RESOURCE = "src/main/resources/export_to_excel.png";

	private HashMap<String, JTable> tables = null;
	
	private JTabbedPane  tabbedPane  = null;
	
	private JPanel       optionPanel = null;
	private NameButton   nameButton  = null;
	
	private JPanel[]     panels      = null;
	
	private JLabel       infoLabel   = null;
	
	private AdminEngine  instance    = null;
	
	public EditPanel( AdminEngine instance ) {		
		super();
		super.setBackground( Color.WHITE );
		super.setLayout( new BorderLayout() );
		this.instance = instance;
		
		setUpOptionPanel();
		setUpInfoLabel();
		setUpTabbedPane();
	}
	
	private void setUpOptionPanel() {
		optionPanel = new JPanel();
		optionPanel.setLayout( new GridBagLayout() );
		optionPanel.setBackground( Color.BLACK );
		
		try {
			setUpNameButton();
			setUpSaveToDatabaseButton();
			setUpExportToExcelButton();
		} catch( IOException ex ) {
			ex.printStackTrace(); 
		}
		
		super.add( optionPanel, BorderLayout.NORTH );
	}
	
	private void setUpNameButton() {

		GridBagConstraints c = new GridBagConstraints();
		nameButton = new NameButton( instance );
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH ;
		optionPanel.add( nameButton, c );

	}
	
	private void setUpSaveToDatabaseButton() throws IOException{
		
		GridBagConstraints c = new GridBagConstraints();
		JButton saveButton = createImageButton( SAVE_TO_DB_RESOURCE );
		saveButton.addActionListener( this );
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		optionPanel.add( saveButton, c );
	}
	
	private void setUpExportToExcelButton() throws IOException {
		
		GridBagConstraints c = new GridBagConstraints();
		JButton exportButton = createImageButton( EXPORT_TO_EXCEL_RESOURCE );
		c.gridx = 2;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		optionPanel.add( exportButton, c );				
	}
	
	private JButton createImageButton( String respath ) throws IOException{
		Image image = ImageIO.read( new File( respath ) );
		ImageIcon icon = new ImageIcon( image );
		JButton button = new JButton( icon );
		button.setBorder( new EmptyBorder( 4, 4, 4, 4 ) );
		button.setBackground( Color.BLACK );
		return button;
	}
	
	private void loadOptionPanel() {
		nameButton.load();
		optionPanel.repaint();
	}
	
	private void setUpInfoLabel() {
		infoLabel = new JLabel( "Test" );
		super.add( infoLabel, BorderLayout.SOUTH );
	}
	
	private void setUpTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setBackground( Color.WHITE );
		tabbedPane.addChangeListener( this );
		super.add( tabbedPane, BorderLayout.CENTER );
	}
	
	public void load( String s ) {
		
		tables = new LinkedHashMap<>();
		tabbedPane.removeAll();
		
		ImageIcon icon = new ImageIcon( DB_RESOURCE );
		loadOptionPanel();
		
		String[] parts = s.split( " " );
		
		panels = new JPanel[parts.length];
		tables.put( s, new JTable() );
		
		for( int i=0; i<panels.length; i++ ) {
			panels[i] = new JPanel();
			panels[i].setLayout( new BorderLayout() );
			tabbedPane.addTab( parts[i], icon, panels[i], parts[i] );
		}		
		System.out.println( "loaded" ); 
	}
	
	public void display( DatabasePayload d ) {
		
		String[] headers = d.getHeaders();
		String[][] data  = d.getData();
		System.out.println( headers[0] );
		String title = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() );

		JTable table = setUpEditTable( data, headers );
		tables.put( title, table );
		
		JScrollPane scrollPane = new JScrollPane( table );
		scrollPane.setBackground( Color.WHITE );
		table.setFillsViewportHeight( true );
		
		this.panels[tabbedPane.getSelectedIndex()].add( scrollPane, BorderLayout.CENTER );
		tabbedPane.repaint();
	}
	
	private JTable setUpEditTable( String[][] data, String[] headers ) {
		JTable table = createTable( data, headers );
		table = setOneClickEditable( table );
		return table;
	}
	
	private JTable createTable( String[][] data, String[] headers ) {
		JTable table = new JTable( data, headers );
		table.setDefaultRenderer( Object.class, new CustomRenderer( table ) );
		table.setGridColor( Color.GRAY );
		table.setFont( UIConstants.FONT.deriveFont( 13f ) );
		table.getTableHeader().setBorder( new MatteBorder( 0,0,1,0, Color.BLACK ) );
		table.getTableHeader().setFont( UIConstants.FONT.deriveFont( 13f ) );
		table.setRowHeight( 20 );
		return table;
	}
	
	private JTable setOneClickEditable( JTable table ) {
		
		JTextField field = new JTextField();
		field.setFont( UIConstants.FONT.deriveFont( 13f ) );
		field.setBorder( new EmptyBorder( 1, 1, 1, 1 ) );
		field.setBackground( new Color( 92, 170, 248 ) );
		field.setForeground( Color.WHITE );
		
		DefaultCellEditor editor = new DefaultCellEditor( field );
		editor.setClickCountToStart( 1 );
		
		for ( int i=0; i<table.getColumnCount(); i++ ) {
			table.setDefaultEditor( table.getColumnClass(i), editor );
		}
		
		return table;
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

	@Override
	public void stateChanged( ChangeEvent e ) {
		try {
			instance.send( new InfoBean( "Requesting table " + 
							tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ) ) );
		} catch( IndexOutOfBoundsException ex ) {
			instance.send( new InfoBean( "Requesting table " + 
					tabbedPane.getTitleAt( 0 ) ) );			
		}
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		for( String s : tables.keySet() ) {
			JTable t = tables.get( s );
			String[]   headers = null;
			String[][] data    = null;
			
			if( t != null ) {
				headers = getHeaders( t );
				data = getData( s, t );
				for( int i=0; i<headers.length; i++ ) {
					System.out.print( headers[i] + " " );
				}
				System.out.println();
				for( int i=0; i<data.length; i++ ) {
					for( int j=0; j<data[i].length; j++ ) {
						System.out.print( data[i][j] + " " );
					}
					System.out.println();
				}
			}
		}
	}
	
	private String[] getHeaders( JTable table ) {
		
		String[] tableHeaders = TableUtils.scrapeHeaders( table );
		String[] headers   = new String[tableHeaders.length + 2];
		headers[0] = "class";
		headers[1] = "section";
		
		for( int i=0, c=2; i<tableHeaders.length; c++, i++ ) {
			headers[c] = tableHeaders[i]; 
		}
		return headers;
	}
	
	private String[][] getData( String clazz, JTable table ) {
		
		String[][] tableData = TableUtils.scrapeData( table );
		String[][] data = null;
		try {
			data = new String[tableData.length]
					[tableData[0].length + 2];
			String[] clazzParts = clazz.split( "-" );
			
			for( int i=0; i<data.length; i++ ) {
				data[i][0] = clazzParts[0];
				data[i][1] = clazzParts[1];

				for( int j=0, c=2; j<data[i].length; c++, j++ ) {
					data[i][c] = tableData[i][j]; 
				}
			}
		} catch( ArrayIndexOutOfBoundsException e ) {
			System.out.println( "le lulz" );
			return new String[0][0];
		}
		
		return data;
	}
}

