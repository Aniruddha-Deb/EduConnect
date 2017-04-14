package com.educonnect.admin.ui;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

import com.educonnect.admin.ui.panels.LoginPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2981649205219251588L;
	private static final String USER_HOME = System.getProperty( "user.home" );
	private static final String DIR_PATH  = USER_HOME + "/.educonnect/admin";
	
	private LoginPanel loginPanel = null;
	
	public MainFrame() {
		super( "EduConnect admin" );
		super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		super.setSize( 500, 400 );
		super.setLocationRelativeTo( null );
		super.setBackground( new Color( 255, 255, 255 ) );
	
		File dir = new File( DIR_PATH );
		if( !dir.exists() ) {
			dir.mkdirs();
		}
		
		loginPanel = new LoginPanel();
		super.add( loginPanel );
	}
	
	public static void serialize( Component comp, String name ) {
		File file = new File( DIR_PATH + "/" + name );
		try {
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( file ) );
			oos.writeObject( comp );
			oos.flush();
			oos.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public static boolean isSerialized( String name ) {
		File file = new File( DIR_PATH );
		
		for( String s : file.list() ) {
			if( s.equals( name ) )  {
				return true;
			}
		}
		return false;
	}

	public static Component deserialize( String name ) {
		File file = new File( DIR_PATH + "/" + name );
		Component comp = null;
		try {
			ObjectInputStream ois = new ObjectInputStream( new FileInputStream( file ) );
			comp = (Component)ois.readObject();
			ois.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return comp;
	}
	
	public void display() {
		super.setVisible( true );
	}
}
