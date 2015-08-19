package de.sogomn.generator;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.sogomn.generator.gui.TileDialog;


public final class Main {
	
	private Main() {
		//...
	}
	
	private static String findLookAndFeel(final String name) {
		final LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
		
		for (final LookAndFeelInfo info : lafs) {
			final String infoName = info.getName();
			
			if (infoName.equalsIgnoreCase(name)) {
				final String className = info.getClassName();
				
				return className;
			}
		}
		
		return null;
	}
	
	private static void configureNimbus() {
		UIManager.put("nimbusBase", new Color(236, 236, 236));
		UIManager.put("control", new Color(238, 238, 238));
		UIManager.put("nimbusFocus", new Color(108, 122, 137));
	}
	
	private static void changeLookAndFeel() {
		final String nimbus = findLookAndFeel("Nimbus");
		
		try {
			if (nimbus != null) {
				configureNimbus();
				UIManager.setLookAndFeel(nimbus);
			} else {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(final String[] args) {
		changeLookAndFeel();
		
		final TileDialog dialog = new TileDialog();
		
		dialog.show();
	}
	
}
