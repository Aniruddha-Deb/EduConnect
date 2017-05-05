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

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.admin.ui.buttons.OptionPanelButtonListener;
import com.educonnect.admin.ui.menu.NameButtonPopupMenu;
import com.educonnect.admin.ui.table.EditTable;
import com.educonnect.admin.ui.table.EditTableModel;
import com.educonnect.admin.ui.util.UIUtils;
import com.educonnect.common.message.dbclass.ClassOfStudents;
import com.educonnect.common.message.dbclass.DatabaseAllClassesResponse;
import com.educonnect.common.message.dbclass.DatabaseSingleClassRequest;
import com.educonnect.common.message.dbclass.DatabaseSingleClassResponse;
import com.educonnect.common.message.dbclass.Student;
import com.educonnect.common.message.dbupdate.ClassOfRows;
import com.educonnect.common.message.dbupdate.Row;
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
				int selectedRow = table.getSelectedRow();
				stopEditingCurrentCell();
				
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
	
	private void stopEditingCurrentCell() {
		TableCellEditor e = getSelectedEditTable().getCellEditor(); 
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
		stopEditingCurrentCell();
		EditTable table   = getSelectedEditTable();
		
		ClassOfStudents c = table.getClassOfStudents() ;
		
		adminEngine.getClientAdapter().sendAsync( 
				new DatabaseSingleClassRequest( c.getClazz(), 
						                        c.getSection() ) ) ;
	}
	
	private EditTable getSelectedEditTable() {
		int selectedIndex = tabbedPane.getSelectedIndex();
		if( selectedIndex != -1 ) {
			String title = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() );
			return tables.get( title );
		}
		else {
			return tables.entrySet().iterator().next().getValue();
		}
	}

	public void handleDatabaseSingleClassResponse( DatabaseSingleClassResponse d ) {

		Student[] students = d.getClassOfStudents().getStudents();

		int            selIndex   = tabbedPane.getSelectedIndex() ;
		if( selIndex != -1 ) {
			String         titleOfTab = tabbedPane.getTitleAt( selIndex ) ;
			EditTable      table      = tables.get( titleOfTab ) ;
			EditTableModel etm        = (EditTableModel)table.getModel();
		
			etm.updateServerCopy( students );
		
			showStatusMessage( "Showing table " + titleOfTab );
		}
	}
	
	@Override
	public void onSaveButtonClicked() {
		List<ClassOfRows> classesOfDirtyRows = new ArrayList<>();
		
		stopEditingCurrentCell();
		
		for( String s : tables.keySet() ) {
			int clazz = Integer.parseInt( s.split( "-" )[0] );
			char section =  s.split( "-" )[1].charAt( 0 );
			
			EditTable t = tables.get( s );
			EditTableModel m = (EditTableModel)t.getModel();
			List<Row> dirtyRows = m.getDirtyRows();
			
			classesOfDirtyRows.add( new ClassOfRows( 
				dirtyRows.toArray( new Row[dirtyRows.size()] ), clazz, section ) );
		}
		
		RowUpdateResponse res = (RowUpdateResponse)adminEngine.getClientAdapter().send( 
								new RowUpdateRequest( classesOfDirtyRows.toArray( 
										new ClassOfRows[classesOfDirtyRows.size()] ) ) );
		
		if( res.isSuccessful() ) {
			onRefreshButtonClicked();
		}
		infoLabel.setText( "Successfully saved all classes" );
	}
	
	public boolean unsavedChangesArePresent() {
		if( tables != null && tabbedPane != null ) {
			stopEditingCurrentCell();
			for( String key : tables.keySet() ) {
				EditTable t = tables.get( key );
				if( ((EditTableModel)t.getModel()).unsavedChangesPresent() ) {
					System.out.println( "Unsaved changes are present in table " + key );
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onRefreshButtonClicked() {
		stateChanged( null ) ; 
	}

	@Override
	public void onNameButtonClicked() {
		stopEditingCurrentCell();
		NameButtonPopupMenu menu = new NameButtonPopupMenu();
		menu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed( ActionEvent e ) {
				
				switch( e.getActionCommand() ) {
					case NameButtonPopupMenu.LOGOUT_COMMAND:
						logOutUser();
					break;
				}
			}
		} );
		menu.show( optionPanel, 3, optionPanel.getHeight() );
	}
	
	private void logOutUser() {
		if( unsavedChangesArePresent() ) {
			int response = -1;
			try {
				response = JOptionPane.showConfirmDialog( this, 
						"Unsaved changes present. \n" + 
						"Would you like to save and exit?", 
						"Unsaved alert", 
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE,
						new ImageIcon( ImageIO.read( getClass().getResource( UIConstants.ALERT_ICON_RES ) ) ) );
			} catch ( Exception e1 ) {
				e1.printStackTrace();
			}
			
			if( response == JOptionPane.YES_OPTION ) {
				onSaveButtonClicked();
				adminEngine.logout();
			}
			else if( response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION ) {				
				// do nothing
			}
			else {
				discardAllUnsavedChanges();
				adminEngine.logout();
			}
		}
		else {
			adminEngine.logout();
		}
	}
	
	private void discardAllUnsavedChanges() {
		if( tables != null && tabbedPane != null ) {
			stopEditingCurrentCell();
			for( String key : tables.keySet() ) {
				EditTable t = tables.get( key );
				if( ((EditTableModel)t.getModel()).unsavedChangesPresent() ) {
					((EditTableModel)t.getModel()).discardUnsavedChanges();
				}
			}
		}
	}

	@Override
	public void onAddStudentButtonClicked() {
		stopEditingCurrentCell();
		EditTable table = getSelectedEditTable();
		EditTableModel model = (EditTableModel)table.getModel();
		
		model.addRow( table.getSelectedRow() + 1 );
		table.setRequestFocusEnabled( true );
		table.requestFocus();
		table.requestFocusInWindow();
		table.changeSelection( table.getRowCount(), 1, false, false );
	}

	@Override
	public void onDeleteStudentButtonClicked() {
		stopEditingCurrentCell();
		EditTable table = getSelectedEditTable();
		EditTableModel model = (EditTableModel)table.getModel();
		int rowIndex = table.getSelectedRow();
		
		if( rowIndex < 0 || rowIndex >= model.getRowCount() ) {
			UIUtils.showError( null, "Unable to delete row" );
		}
		else {
			model.deleteRow( table.getSelectedRow() );
			if( rowIndex-1 >= 0 ) {
				table.changeSelection( rowIndex-1, 1, false, false );
			}
			else {
				table.changeSelection( rowIndex, 1, false, false );			
			}
		}
	}
}