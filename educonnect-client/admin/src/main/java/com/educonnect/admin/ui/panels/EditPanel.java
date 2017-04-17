package com.educonnect.admin.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.payload.DatabasePayload;
import com.educonnect.common.bean.payload.InfoPayload;

public class EditPanel extends JPanel {

	private static final long serialVersionUID = 8043632711448308358L;
	
	private JTabbedPane tabbedPane = null;
	private ImageIcon icon = null;
	private JPanel[] panels = null;
	
	public EditPanel() {
		super();
		super.setBackground( Color.WHITE );
		super.setLayout( new BorderLayout() );
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
	
	public void load( InfoPayload p ) {
		
		icon = new ImageIcon( "src/main/resources/db.png", "lool" );
		
		String[] parts = p.getInfo().split( " " );
		
		panels = new JPanel[parts.length];
		
		for( int i=0; i<panels.length; i++ ) {
			System.out.println( "Made panel" );   
			panels[i] = new JPanel();
			panels[i].setBackground( Color.WHITE );
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

