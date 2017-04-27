package com.educonnect.admin.ui.panels.editpanel;

import static com.educonnect.admin.ui.UIConstants.DB_ICON_RES;

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
import com.educonnect.admin.ui.util.UIUtils;
import com.educonnect.common.message.db.ClassOfStudents;
import com.educonnect.common.message.db.Student;
import com.educonnect.common.message.dbclass.DatabaseAllClassesResponse;
import com.educonnect.common.message.dbclass.DatabaseSingleClassRequest;
import com.educonnect.common.message.dbclass.DatabaseSingleClassResponse;

public class EditPanel extends JPanel implements ChangeListener, OptionPanelButtonListener {

	private static final long serialVersionUID = 3873660039173146777L;

	private HashMap<String, JTable> tables = null;
	
	private JTabbedPane  tabbedPane  = null;
	private OptionPanel  optionPanel = null;
	private JPanel[]     tablePanels = null;
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
		
	private void loadOptionPanel() {
		optionPanel.loadNameOntoNameButton();
		optionPanel.repaint();
	}
	
	private void createInfoLabel() {
		infoLabel = new JLabel( "Test" );
	}
	
	private void createTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setBackground( Color.WHITE );
		tabbedPane.addChangeListener( this );
	}
	
	public void load( DatabaseAllClassesResponse r ) {
		
		tables = new LinkedHashMap<>();
		tabbedPane.removeAll();
		
		ImageIcon icon = new ImageIcon( UIUtils.getImageResource( DB_ICON_RES ) );
		loadOptionPanel();

		ClassOfStudents[] classes = r.getClasses();
		
		tablePanels = new JPanel[classes.length];
		
		for( int i=0; i<tablePanels.length; i++ ) {
			String classTitle = classes[i].getClazz() + "-" + classes[i].getSection();
			setUpTablePanelAt( i, classes[i] );
			tabbedPane.addTab( classTitle, icon, tablePanels[i], classTitle );
		}		
		infoLabel.setText( "Loaded tables" );
	}
	
	private void setUpTablePanelAt( int index, ClassOfStudents c ) {
		String classTitle = c.getClazz() + "-" + c.getSection();
		JTable table = initEditTable( c );
		tables.put( classTitle, table );
		
		JScrollPane scrollPane = new JScrollPane( table );
		scrollPane.setBackground( Color.WHITE );
		scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		table.setFillsViewportHeight( true );
		
		tablePanels[index] = new JPanel();
		tablePanels[index].setLayout( new BorderLayout() );
		tablePanels[index].add( scrollPane, BorderLayout.CENTER );
	}
	
	private JTable initEditTable( ClassOfStudents c ) {
		EditTable table = new EditTable( new EditTableModel().withStudents( c.getStudents() ) );
		table.setEnterKeystrokeFunction( "newRow", new AbstractAction() {
			
			private static final long serialVersionUID = 1402568481026919305L;

			@Override
			public void actionPerformed( ActionEvent e ) {
				EditTableModel model = (EditTableModel)table.getModel();
				int selectedRow = table.getSelectedRow();
				if( table.getSelectedRow() == model.getRowCount()-1 ) {
					model.addRow( selectedRow+1 );
					table.changeSelection( selectedRow+1, 1, false, false );
				}
				else {
					table.changeSelection( selectedRow+1, table.getSelectedColumn(), false, false );					
				}
			}
		});
		return table;
	}
	
	public void display( DatabaseSingleClassResponse d ) {

		Student[] students = d.getClassOfStudents().getStudents();
		String titleOfTab = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() );

		JTable table = tables.get( titleOfTab );
		EditTableModel etm = (EditTableModel)table.getModel();
		
		if( etm.isGoldenCopyPresent() ) {
			etm.updateServerCopy( students );
		}
		else {
			initializeTable( table, students );
		}
		
		infoLabel.setText( "Showing table " + titleOfTab );
	}
	
	private void initializeTable( JTable table, Student[] students )  {
		EditTableModel etm = (EditTableModel)table.getModel();
		table.setModel( etm.withStudents( students ) );
		
		JScrollPane scrollPane = new JScrollPane( table );
		scrollPane.setBackground( Color.WHITE );
		scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		table.setFillsViewportHeight( true );
		
		this.tablePanels[tabbedPane.getSelectedIndex()].add( scrollPane, BorderLayout.CENTER );
		tabbedPane.repaint();
	}
	
	private int getSelectedClass() {
		String title = null;
		
		try {
			title = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() );
			return Integer.parseInt( title.split( "-" )[0] );
		} catch( IndexOutOfBoundsException e ) {
			title = tabbedPane.getTitleAt( 0 );
			return Integer.parseInt( title.split( "-" )[0] );			
		}
	}
	
	private char getSelectedSection() {
		String title = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() );
		return title.split( "-" )[1].charAt( 0 );
	}
		
	@Override
	public void stateChanged( ChangeEvent e ) {
		adminEngine.getClientAdapter().send( 
				new DatabaseSingleClassRequest( 
					getSelectedClass(),
					getSelectedSection()
				) 
			);
	}

	@Override
	public void onSaveButtonClicked() {
		
	}

	@Override
	public void onExportButtonClicked() {
		
	}

	@Override
	public void onRefreshButtonClicked() {
		adminEngine.getClientAdapter().send( 
			new DatabaseSingleClassRequest( 
				getSelectedClass(),
				getSelectedSection()
			) 
		);
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