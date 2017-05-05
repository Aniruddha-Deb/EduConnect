package com.educonnect.admin.ui.util;

import static com.educonnect.admin.Constants.RES_DIR_PATH;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.educonnect.admin.ui.UIConstants;
import com.educonnect.admin.ui.panels.editpanel.EditPanel;

public class UIUtils {

	public static void serialize( Component comp, String name ) {
		File file = new File( RES_DIR_PATH + File.separator + name );
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
		File file = new File( RES_DIR_PATH );
		
		for( String s : file.list() ) {
			if( s.equals( name ) )  {
				return true;
			}
		}
		return false;
	}

	public static Component deserialize( String name ) {
		File file = new File( RES_DIR_PATH + File.separator + name );
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
	
	public static void showError( JFrame relativeFrame, String errorMessage ) {
		try {
			JOptionPane.showMessageDialog( relativeFrame, errorMessage, 
									"Error", JOptionPane.ERROR_MESSAGE, 
									new ImageIcon( ImageIO.read( UIUtils.class.getResource( UIConstants.ERROR_ICON_RES ) ) ) );
		} catch ( HeadlessException | IOException e ) {
			e.printStackTrace();
		}
	}	
}
