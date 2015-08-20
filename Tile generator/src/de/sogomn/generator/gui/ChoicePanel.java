package de.sogomn.generator.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.sogomn.generator.util.FileUtils;
import de.sogomn.generator.util.ImageUtils;

final class ChoicePanel {
	
	private JPanel panel;
	
	private JLabel preview;
	private JButton button;
	
	private JFileChooser fileChooser;
	
	private BufferedImage tile;
	
	public ChoicePanel(final String name) {
		panel = new JPanel();
		preview = new JLabel();
		button = new JButton("Choose " + name.toLowerCase());
		fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File("/"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.png, *.jpg, *.gif", "PNG", "JPG", "GIF"));
		
		preview.setPreferredSize(new Dimension(150, 150));
		preview.setMinimumSize(new Dimension(150, 150));
		preview.setMaximumSize(new Dimension(150, 150));
		preview.setHorizontalAlignment(JLabel.CENTER);
		preview.setBorder(new TitledBorder(name));
		preview.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent c) {
				resizeIcon();
			}
		});
		
		button.addActionListener(a -> {
			final BufferedImage image = chooseImageFile();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				tile = image;
				
				preview.setIcon(icon);
				resizeIcon();
			}
		});
		
		panel.setLayout(new GridLayout(2, 1, 5, 5));
		panel.add(preview);
		panel.add(button);
	}
	
	private BufferedImage chooseImageFile() {
		final int input = fileChooser.showOpenDialog(button);
		
		if (input == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();
			
			if (FileUtils.isImage(file)) {
				final BufferedImage image = ImageUtils.loadExternal(file);
				
				return image;
			}
		}
		
		return null;
	}
	
	private void resizeIcon() {
		final int size = Math.min(preview.getWidth(), preview.getHeight());
		
		ImageIcon icon = (ImageIcon)preview.getIcon();
		
		if (icon != null) {
			final Image image = icon.getImage();
			final Image smallImage = image.getScaledInstance(size, size, Image.SCALE_FAST);
			
			icon = new ImageIcon(smallImage);
			
			preview.setIcon(icon);
		}
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public BufferedImage getTile() {
		return tile;
	}
	
}
