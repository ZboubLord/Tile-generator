package de.sogomn.generator;

import javax.swing.UIManager;

import de.sogomn.generator.gui.TileDialog;


public final class Main {
	
	private Main() {
		//...
	}
	
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		
		final TileDialog dialog = new TileDialog();
		
		dialog.show();
	}
	
}
