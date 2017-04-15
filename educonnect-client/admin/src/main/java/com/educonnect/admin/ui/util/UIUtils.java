package com.educonnect.admin.ui.util;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.educonnect.admin.Constants.DIR_PATH;;

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
}
