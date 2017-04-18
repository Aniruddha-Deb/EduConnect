package com.educonnect.admin.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;

import com.educonnect.admin.Constants;
import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.payload.DatabasePayload;
import com.educonnect.common.bean.payload.InfoPayload;

public class EditPanel extends JPanel {

	private static final long serialVersionUID = 8043632711448308358L;
	
	private JTabbedPane tabbedPane = null;
	private JPanel optionPanel = null;
	private JLabel infoLabel = null;
	private ImageIcon icon = null;
	private JPanel[] panels = null;
	private GridBagConstraints c = null;
	
	public EditPanel() {
		super();
		super.setBackground( Color.WHITE );
		super.setLayout( new BorderLayout() );
		optionPanel = new JPanel();
		optionPanel.setLayout( new GridBagLayout() );
		super.add( optionPanel, BorderLayout.NORTH );
		infoLabel = new JLabel( "Test" );
		super.add( infoLabel, BorderLayout.SOUTH );
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setBackground( Color.WHITE );
		tabbedPane.addChangeListener( new ChangeListener() {
			
			@Override
			public void stateChanged( ChangeEvent e ) {
				AdminEngine.getInstance().send( 
					new InfoBean( "Requesting table " +  
						tabbedPane.getTitleAt( 
							tabbedPane.getSelectedIndex() 
						) 
					) 
				);
				
				System.out.println( "Sent request " + "Requesting table " +  
						tabbedPane.getTitleAt( 
							tabbedPane.getSelectedIndex() ) );
			}
		} );
		super.add( tabbedPane, BorderLayout.CENTER );
	}
	
	private void setUpOptionPanel() {
		
		JPopupMenu menu = new JPopupMenu();
		menu.setFont( UIConstants.FONT.deriveFont( 12f ) );
		menu.setBackground( Color.WHITE );
		menu.add( "Logout" );
		
		optionPanel.setBackground( Color.BLACK );
		c = new GridBagConstraints();
		JButton nameButton = new JButton( Constants.userName );
		nameButton.setForeground( Color.WHITE );
		nameButton.setHorizontalAlignment( SwingConstants.LEFT );
		nameButton.setFont( UIConstants.FONT.deriveFont( Font.BOLD, 15f ) );
		nameButton.setBorder( new EmptyBorder( 0, 3, 0, 0 ) );
		nameButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed( ActionEvent e ) {
				menu.show( nameButton, 3, nameButton.getHeight() );
			}
		} );
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH ;
		optionPanel.add( nameButton, c );
		
		Image image1 = null;
		try {
			image1 = ImageIO.read( new File( "src/main/resources/save_to_db.png" ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon saveIcon = new ImageIcon( image1 );
		JButton saveButton = new JButton( saveIcon );
		saveButton.setBorder( new EmptyBorder( 4, 4, 4, 4 ) );
		saveButton.setBackground( Color.BLACK );
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		optionPanel.add( saveButton, c );
		
		Image image2 = null;
		try {
			image2 = ImageIO.read( new File( "src/main/resources/export_to_excel.png" ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon exportIcon = new ImageIcon( image2 );
		JButton exportButton = new JButton( exportIcon );
		exportButton.setBorder( new EmptyBorder( 4, 4, 4, 4 ) );
		exportButton.setBackground( Color.BLACK );
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		optionPanel.add( exportButton, c );		
	}
	
	
	public void load( InfoPayload p ) {
		
		icon = new ImageIcon( "src/main/resources/db.png", "lool" );
		setUpOptionPanel();
		optionPanel.repaint();
		
		String[] parts = p.getInfo().split( " " );
		
		panels = new JPanel[parts.length];
		
		for( int i=0; i<panels.length; i++ ) {
			System.out.println( "Made panel" );   
			panels[i] = new JPanel();
			panels[i].setLayout( new BorderLayout() );
		}
		
		for( int i=0; i<parts.length; i++ ) {
			tabbedPane.addTab( parts[i], icon, panels[i], parts[i] );
		}
		System.out.println( "loaded" ); 
	}
	
	public void display( DatabasePayload d ) {
		
		String[] headers = d.getHeaders();
		String[][] data  = d.getData();
		
		JTable table = new JTable( data, headers );
		table.setDefaultRenderer( Object.class, new CustomRenderer( table ) );
		table.setGridColor( Color.GRAY );
		table.setFont( UIConstants.FONT.deriveFont( 13f ) );
		table.getTableHeader().setBorder( new MatteBorder( 0,0,1,0, Color.BLACK ) );
		table.getTableHeader().setFont( UIConstants.FONT.deriveFont( 13f ) );
		table.setRowHeight( 20 );
		
		JScrollPane scrollPane = new JScrollPane( table );
		scrollPane.setBackground( Color.WHITE );
		table.setFillsViewportHeight( true );
		
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
		
		if( panels[0] == null ) {
			System.out.println( "Bjørk!" );
		}
		System.out.println( "displaying panel " );
		this.panels[tabbedPane.getSelectedIndex()].add( scrollPane, BorderLayout.CENTER );
		tabbedPane.repaint();
		System.out.println( "Displayed panel" );
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

