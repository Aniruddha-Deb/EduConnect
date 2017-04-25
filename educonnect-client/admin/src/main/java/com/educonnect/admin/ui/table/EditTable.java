package com.educonnect.admin.ui.table;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableModel;

import com.educonnect.admin.ui.UIConstants;

public class EditTable extends JTable{

	private static final long serialVersionUID = -2558045536129893621L;

	public EditTable() {
		super( new EditTableModel() );
		setUpTableAttributes();
	}
	
	public EditTable( EditTableModel etm ) {
		super( etm );
		setUpTableAttributes();
		setUpTableCellEditor();
	}
	
	private void setUpTableAttributes() {
		super.setDefaultRenderer( Object.class, new EditTableRenderer( this ) );
		super.setEnabled( true );
		super.setGridColor( Color.GRAY );
		super.setFont( UIConstants.FONT.deriveFont( 13f ) );
		super.getTableHeader().setBorder( new MatteBorder( 0,0,1,0, Color.BLACK ) );
		super.getTableHeader().setFont( UIConstants.FONT.deriveFont( 13f ) );
		super.setRowHeight( 20 );
	}
	
	private void setUpTableCellEditor() {
		JTextField field = new JTextField();
		field.setFont( UIConstants.FONT.deriveFont( 13f ) );
		field.setBorder( new EmptyBorder( 1, 1, 1, 1 ) );
		field.setBackground( new Color( 92, 170, 248 ) );
		field.setForeground( Color.WHITE );
		
		DefaultCellEditor editor = new DefaultCellEditor( field );
		
		for ( int i=0; i<super.getColumnCount(); i++ ) {
			super.setDefaultEditor( super.getColumnClass(i), editor );
		}
	}
	
	public void setEnterKeystrokeFunction( String function, AbstractAction actionToPerform ) {
		KeyStroke enter = KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 );
		super.getInputMap( JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT ).put( enter, function );
		super.getActionMap().put( function, actionToPerform );
	}
	
	@Override
	public TableModel getModel() {
		return (EditTableModel)super.getModel();
	}
}
