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
import com.educonnect.admin.ui.util.UIUtils;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.db.Student;
import com.educonnect.common.bean.payload.db.DatabasePayload;

public class EditPanel extends JPanel implements ChangeListener, OptionPanelButtonListener {

	private static final long serialVersionUID = 3873660039173146777L;

	private HashMap<String, EditTable> tables = null;
	
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
	
	public void load( String s ) {
		
		tables = new LinkedHashMap<>();
		tabbedPane.removeAll();
		
		ImageIcon icon = new ImageIcon( UIUtils.getImageResource( DB_ICON_RES ) );
		loadOptionPanel();
		
		String[] parts = s.split( " " );
		
		tablePanels = new JPanel[parts.length];
		
		for( int i=0; i<tablePanels.length; i++ ) {
			tables.put( parts[i], createEditTable() );
			tablePanels[i] = new JPanel();
			tablePanels[i].setLayout( new BorderLayout() );
			tabbedPane.addTab( parts[i], icon, tablePanels[i], parts[i] );
		}		
		infoLabel.setText( "Loaded tables" );
	}
	
	public void display( DatabasePayload d ) {

		Student[] students = d.getStudents();
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
	
	private EditTable createEditTable() {
		EditTable table = new EditTable();
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
		
	@Override
	public void stateChanged( ChangeEvent e ) {
		try {
			adminEngine.send( new InfoBean( "Requesting table " + 
							tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ) ) );
		} catch( IndexOutOfBoundsException ex ) {
			adminEngine.send( new InfoBean( "Requesting table " + 
					tabbedPane.getTitleAt( 0 ) ) );			
		}
	}

	@Override
	public void onSaveButtonClicked() {
		
	}

	@Override
	public void onExportButtonClicked() {
		
	}

	@Override
	public void onRefreshButtonClicked() {
		try {
			adminEngine.send( new InfoBean( "Requesting table " + 
							tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ) ) );
		} catch( IndexOutOfBoundsException ex ) {
			adminEngine.send( new InfoBean( "Requesting table " + 
					tabbedPane.getTitleAt( 0 ) ) );			
		}
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