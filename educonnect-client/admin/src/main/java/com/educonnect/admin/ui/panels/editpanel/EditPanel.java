package com.educonnect.admin.ui.panels.editpanel;

import static com.educonnect.admin.ui.UIConstants.DB_ICON_RES;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.admin.ui.buttons.OptionPanelButtonListener;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.db.Student;
import com.educonnect.common.bean.payload.db.DatabasePayload;

@SuppressWarnings("serial")
public class EditPanel extends JPanel implements ChangeListener, OptionPanelButtonListener {

	private HashMap<String, JTable> tables = null;
	
	private JTabbedPane  tabbedPane  = null;
	private OptionPanel  optionPanel = null;
	private JPanel[]     panels      = null;
	private JLabel       infoLabel   = null;
	
	private AdminEngine  adminEngine    = null;
	
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
		setUpInfoLabel();
		setUpTabbedPane();

	}
	
	private void createOptionPanel() {
		optionPanel = new OptionPanel();
		optionPanel.addOptionPaneButtonListener( this );
	}
		
	private void loadOptionPanel() {
		optionPanel.loadNameOntoNameButton();
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
		
		InputStream is = EditPanel.class.getResourceAsStream( DB_ICON_RES ) ;
	 	Image image = null;
		try {
			image = ImageIO.read( is );
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon( image );
		loadOptionPanel();
		
		String[] parts = s.split( " " );
		
		panels = new JPanel[parts.length];
		
		for( int i=0; i<panels.length; i++ ) {
			tables.put( parts[i], createEditTable() );
			panels[i] = new JPanel();
			panels[i].setLayout( new BorderLayout() );
			tabbedPane.addTab( parts[i], icon, panels[i], parts[i] );
		}		
		System.out.println( "loaded" ); 
	}
	
	public void display( DatabasePayload d ) {

		Student[] students = d.getStudents();
		String title = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() );

		JTable table = tables.get( title );
		EditTableModel etm = (EditTableModel)table.getModel();
		if( etm.isGoldenCopyPresent() ) {
			System.out.println( "Updating server copy" );
			etm.updateServerCopy( students );
		}
		else {
			System.out.println( "Initializing server copy" );
			table.setModel( etm.withStudents( students ) );
			table.setPreferredSize( null );
			
			JScrollPane scrollPane = new JScrollPane( table );
			scrollPane.setBackground( Color.WHITE );
			scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
			table.setFillsViewportHeight( true );
			
			this.panels[tabbedPane.getSelectedIndex()].add( scrollPane, BorderLayout.CENTER );
			tabbedPane.repaint();
		}
	}
	
	private JTable createEditTable() {
		JTable table = createTable();
		table = setCellEditor( table );
		return table;
	}
	
	private JTable createTable() {
		JTable table = new JTable( new EditTableModel() );
		table.setDefaultRenderer( Object.class, new EditTableRenderer( table ) );
		table.setEnabled( true );
		table.setGridColor( Color.GRAY );
		table.setFont( UIConstants.FONT.deriveFont( 13f ) );
		table.getTableHeader().setBorder( new MatteBorder( 0,0,1,0, Color.BLACK ) );
		table.getTableHeader().setFont( UIConstants.FONT.deriveFont( 13f ) );
		table.setRowHeight( 20 );
		
		KeyStroke enter = KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 );
		table.getInputMap( JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT ).put( enter, "newRow" );
		table.getActionMap().put( "newRow", new AbstractAction() {
			
			private static final long serialVersionUID = -2036038687905519612L;

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
		} );
		System.out.println( "Put enterAction in input map" );
		
		return table;
	}
	
	private JTable setCellEditor( JTable table ) {
		
		JTextField field = new JTextField();
		field.setFont( UIConstants.FONT.deriveFont( 13f ) );
		field.setBorder( new EmptyBorder( 1, 1, 1, 1 ) );
		field.setBackground( new Color( 92, 170, 248 ) );
		field.setForeground( Color.WHITE );
		
		DefaultCellEditor editor = new DefaultCellEditor( field );
		
		for ( int i=0; i<table.getColumnCount(); i++ ) {
			table.setDefaultEditor( table.getColumnClass(i), editor );
		}
		
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