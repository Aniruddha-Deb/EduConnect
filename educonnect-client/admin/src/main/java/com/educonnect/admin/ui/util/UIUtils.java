package com.educonnect.admin.ui.util;

import static com.educonnect.admin.Constants.DIR_PATH;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.panels.editpanel.EditPanel;

public class UIUtils {

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
	
	public static Image getImageResource( String respath ) {
		Image image = null;
		try {
			InputStream is = EditPanel.class.getResourceAsStream( respath ) ;
			image = ImageIO.read( is );
		} catch( IOException ex ) {
			ex.printStackTrace();
		}
		return image;
	}
	
	public static void showError( int errorCode, String errorMessage ) {
		JOptionPane.showMessageDialog( null, "ERROR " + errorCode + ": \n" + 
											 errorMessage );
	}
	
	public static void showYesNoPrompt( String errorMessage, AdminEngine e ) {
		try {
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run() {
					int response = JOptionPane.showConfirmDialog( null, errorMessage );
					if( response == JOptionPane.NO_OPTION ) {
						e.shutdown();
					}
					else {
						// Let the user continue
					}				
				}
			});
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
