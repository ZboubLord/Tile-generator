package de.sogomn.generator;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.sogomn.generator.gui.TileDialog;


public final class Main {
	
	private Main() {
		//...
	}
	
	private static void configureNimbus() {
		UIManager.put("nimbusBase", new Color(236, 236, 236));
		UIManager.put("control", new Color(238, 238, 238));
		UIManager.put("nimbusFocus", new Color(108, 122, 137));
	}
	
	public static void main(final String[] args) {
		final LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
		boolean useNimbus = false;
		
		try {
			for (final LookAndFeelInfo info : lafs) {
				final String name = info.getName();
				
				if (name.equalsIgnoreCase("Nimbus")) {
					UIManager.setLookAndFeel(info.getClassName());
					
					configureNimbus();
					
					useNimbus = true;
				}
			}
			
			if (!useNimbus) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		
		final TileDialog dialog = new TileDialog();
		
		dialog.show();
	}
	
}
