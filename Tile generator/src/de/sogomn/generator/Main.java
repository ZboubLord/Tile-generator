package de.sogomn.generator;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.sogomn.generator.gui.TileDialog;


public final class Main {
	
	private Main() {
		//...
	}
	
	private static void configureNimbus(final NimbusLookAndFeel nimbus) {
		final UIDefaults defaults = nimbus.getDefaults();
		
		defaults.put("nimbusBase", new Color(236, 236, 236));
		defaults.put("control", new Color(238, 238, 238));
		defaults.put("nimbusFocus", new Color(108, 122, 137));
		defaults.put("Button.font", new Font("Consolas", Font.BOLD, 15));
		defaults.put("TitledBorder.font", new Font("Consolas", Font.PLAIN, 12));
	}
	
	private static void changeLookAndFeel() {
		final NimbusLookAndFeel nimbus = new NimbusLookAndFeel();
		
		configureNimbus(nimbus);
		
		try {
			UIManager.setLookAndFeel(nimbus);
		} catch (final UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(final String[] args) {
		changeLookAndFeel();
		
		final TileDialog dialog = new TileDialog();
		
		dialog.show();
	}
	
}
