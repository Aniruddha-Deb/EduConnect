package com.educonnect.admin.ui.panels.editpanel;

import static com.educonnect.admin.ui.UIConstants.DB_ICON_RES;
import static com.educonnect.admin.ui.util.UIUtils.getImageResource;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.AbstractAction;
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
import com.educonnect.common.message.db.ClassOfStudents;
import com.educonnect.common.message.db.Student;
import com.educonnect.common.message.dbclass.DatabaseAllClassesResponse;
import com.educonnect.common.message.dbclass.DatabaseSingleClassRequest;
import com.educonnect.common.message.dbclass.DatabaseSingleClassResponse;

public class EditPanel extends JPanel 
	implements ChangeListener, OptionPanelButtonListener {

	private static final long serialVersionUID = 3873660039173146777L;
	
	private static ImageIcon DB_ICON = new ImageIcon( getImageResource( DB_ICON_RES ) );

	private HashMap<String, EditTable> tables = new LinkedHashMap<String, EditTable>() ;
	
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
	
	// This method is called when the user switches tabs. An async request is
	// sent to the server to fetch an updated copy of the students for the
	// class representing the now visible tab. 
	//
	// The response would be notified by calling back the 
	// handleDatabaseSingleClassResponse method
	@Override
	public void stateChanged( ChangeEvent e ) {
		
		String titleOfTab = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ) ;
		EditTable table   = tables.get( titleOfTab ) ;
		
		ClassOfStudents c = table.getClassOfStudents() ;
		
		adminEngine.getClientAdapter().sendAsync( 
				new DatabaseSingleClassRequest( c.getClazz(), 
						                        c.getSection() ) ) ;
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
		// TODO
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