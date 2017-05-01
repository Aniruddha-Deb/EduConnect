package com.educonnect.admin.ui.panels.editpanel;

import static com.educonnect.admin.ui.UIConstants.DB_ICON_RES;
import static com.educonnect.admin.ui.util.UIUtils.getImageResource;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.CellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.buttons.OptionPanelButtonListener;
import com.educonnect.admin.ui.menu.NameButtonPopupMenu;
import com.educonnect.admin.ui.table.EditTable;
import com.educonnect.admin.ui.table.EditTableModel;
import com.educonnect.common.message.dbclass.ClassOfStudents;
import com.educonnect.common.message.dbclass.DatabaseAllClassesResponse;
import com.educonnect.common.message.dbclass.DatabaseSingleClassRequest;
import com.educonnect.common.message.dbclass.DatabaseSingleClassResponse;
import com.educonnect.common.message.dbclass.Student;
import com.educonnect.common.message.dbupdate.Row;
import com.educonnect.common.message.dbupdate.Row.RowAction;
import com.educonnect.common.message.dbupdate.RowUpdateRequest;
import com.educonnect.common.message.dbupdate.RowUpdateResponse;

public class EditPanel extends JPanel 
	implements ChangeListener, OptionPanelButtonListener {

	private static final long serialVersionUID = 3873660039173146777L;
	
	private static ImageIcon DB_ICON = new ImageIcon( getImageResource( DB_ICON_RES ) );

	private HashMap<String, EditTable> tables = null;
	
	private JTabbedPane  tabbedPane  = null;
	private OptionPanel  optionPanel = null;
	private JLabel       infoLabel   = null;
	
	private AdminEngine  adminEngine = null;
	
	public EditPanel( AdminEngine engine ) {
		super();
		this.adminEngine = engine;
		setUpUI() ;
	}
	
	private void setUpUI() {
		super.setBackground( Color.WHITE );
		super.setLayout( new BorderLayout() );
		
		createOptionPanel();
		super.add( optionPanel, BorderLayout.NORTH );
		
		createInfoLabel();
		super.add( infoLabel, BorderLayout.SOUTH );
		
		createTabbedPane();
		super.add( tabbedPane, BorderLayout.CENTER );
	}
	
	private void createOptionPanel() {
		optionPanel = new OptionPanel();
		optionPanel.addOptionPaneButtonListener( this );
	}
		
	private void createInfoLabel() {
		infoLabel = new JLabel( "Test" );
	}
	
	private void createTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setBackground( Color.WHITE );
		tabbedPane.addChangeListener( this );
	}
	
	private void showStatusMessage( String msg ) {
		infoLabel.setText( msg ) ;
	}
	
	public void load( DatabaseAllClassesResponse r ) {

		tabbedPane.removeAll();
		tables = new LinkedHashMap<>();
		
		optionPanel.loadNameOntoNameButton();
		optionPanel.repaint();

		ClassOfStudents[] classes = r.getClasses();
		
		for( int i=0; i<classes.length; i++ ) {
			ClassOfStudents c = classes[i] ;
			JPanel panel = createTablePanel( c ) ;
			tabbedPane.addTab( c.getClassName(), DB_ICON, panel ) ;
		}
		
		showStatusMessage( "Loaded tables" );
	}
	
	private JPanel createTablePanel( ClassOfStudents c ) {
		
		EditTable table = createClassEditTable( c );
		tables.put( c.getClassName(), table );
		
		JScrollPane scrollPane = new JScrollPane( table );
		scrollPane.setBackground( Color.WHITE );
		scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		table.setFillsViewportHeight( true );
		
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
		panel.add( scrollPane, BorderLayout.CENTER );
		
		return panel ;
	}
	
	@SuppressWarnings("serial")
	private EditTable createClassEditTable( ClassOfStudents c ) {
		
		EditTable table = new EditTable( c ) ;
		table.setEnterKeystrokeFunction( "newRow", new AbstractAction() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				
				JTable table = ( JTable )e.getSource() ;
				EditTableModel model = ( EditTableModel )table.getModel() ;
				CellEditor editor = table.getCellEditor();
				int selectedRow = table.getSelectedRow();

				stopEditingCurrentCell( editor );
				if( table.getSelectedRow() == model.getRowCount()-1 ) {
					model.addRow( selectedRow+1 );
					table.changeSelection( selectedRow+1, 1, false, false );
				}
				else {
					table.changeSelection( selectedRow+1, 
							               table.getSelectedColumn(), 
							               false, false );					
				}
			}
		});
		return table;
	}
	
	private void stopEditingCurrentCell( CellEditor e ) {
		if ( e != null ) {
		    if( e.getCellEditorValue() != null ) {
		        e.stopCellEditing();
		    }
		    else {
		        e.cancelCellEditing();
		    }
		}					
	}
	
	// This method is called when the user switches tabs. An async request is
	// sent to the server to fetch an updated copy of the students for the
	// class representing the now visible tab. 
	//
	// The response would be notified by calling back the 
	// handleDatabaseSingleClassResponse method
	@Override
	public void stateChanged( ChangeEvent e ) {
		
		int selectedIndex = tabbedPane.getSelectedIndex();
		if( selectedIndex != -1 ) {
			String titleOfTab = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ) ;
			EditTable table   = tables.get( titleOfTab ) ;
			
			ClassOfStudents c = table.getClassOfStudents() ;
			
			adminEngine.getClientAdapter().sendAsync( 
					new DatabaseSingleClassRequest( c.getClazz(), 
							                        c.getSection() ) ) ;
		}
	}

	public void handleDatabaseSingleClassResponse( DatabaseSingleClassResponse d ) {

		Student[] students = d.getClassOfStudents().getStudents();

		int            selIndex   = tabbedPane.getSelectedIndex() ;
		String         titleOfTab = tabbedPane.getTitleAt( selIndex ) ;
		EditTable      table      = tables.get( titleOfTab ) ;
		EditTableModel etm        = (EditTableModel)table.getModel();
		
		etm.updateServerCopy( students );
		
		showStatusMessage( "Showing table " + titleOfTab );
	}
	
	@Override
	public void onSaveButtonClicked() {
		List<Row> dirtyRows = new ArrayList<>();
		for( String s : tables.keySet() ) {
			int clazz = Integer.parseInt( s.split( "-" )[0] );
			char section =  s.split( "-" )[1].charAt( 0 );
			
			EditTable t = tables.get( s );
			EditTableModel m = (EditTableModel)t.getModel();
			List<Student> dirtyStudents = m.getDirtyStudents();
			
			for( Student student : dirtyStudents ) {
				if( student.getUID() == -1 ) {
					dirtyRows.add( new Row( RowAction.CREATE, clazz, section, student ) );
				}
				else {
					dirtyRows.add( new Row( RowAction.UPDATE, clazz, section, student ) );					
				}
				System.out.println( student.toString() );
			}
		}
		RowUpdateResponse res = (RowUpdateResponse)adminEngine.getClientAdapter().send( 
								new RowUpdateRequest( dirtyRows.toArray( 
										new Row[dirtyRows.size()] ) ) );
		
		if( res.isSuccessful() ) {
			onRefreshButtonClicked();
		}
		infoLabel.setText( "Successfully saved all classes" );
	}
	
	public boolean unsavedChangesArePresent() {
		for( String key : tables.keySet() ) {
			EditTable t = tables.get( key );
			if( ((EditTableModel)t.getModel()).unsavedChangesPresent() ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onExportButtonClicked() {
		// TODO
	}

	@Override
	public void onRefreshButtonClicked() {
		stateChanged( null ) ; 
	}

	@Override
	public void onNameButtonClicked() {
		NameButtonPopupMenu menu = new NameButtonPopupMenu();
		menu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed( ActionEvent e ) {
				
				switch( e.getActionCommand() ) {
					case NameButtonPopupMenu.LOGOUT_COMMAND:
						adminEngine.logout();
				}
			}
		} );
		menu.show( optionPanel, 3, optionPanel.getHeight() );
	}
}