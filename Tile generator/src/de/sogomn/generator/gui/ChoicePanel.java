package de.sogomn.generator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private TitledBorder border, borderHover;
	
	private JFileChooser fileChooser;
	
	private BufferedImage tile;
	
	private static final int PREVIEW_PADDING = 25;
	
	public ChoicePanel(final String name) {
		panel = new JPanel();
		preview = new JLabel();
		button = new JButton("Choose " + name.toLowerCase());
		border = new TitledBorder(name);
		borderHover = new TitledBorder("Click to clear");
		fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File("/"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.png, *.jpg, *.gif", "PNG", "JPG", "GIF"));
		
		borderHover.setTitleColor(Color.RED);
		
		preview.setPreferredSize(new Dimension(150, 150));
		preview.setMinimumSize(new Dimension(150, 150));
		preview.setMaximumSize(new Dimension(150, 150));
		preview.setHorizontalAlignment(JLabel.CENTER);
		preview.setBorder(border);
		preview.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent c) {
				resizeIcon();
			}
		});
		preview.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent m) {
				if (m.getButton() == MouseEvent.BUTTON1) {
					removeTile();
				}
			}
			@Override
			public void mouseEntered(final MouseEvent m) {
				preview.setBorder(borderHover);
			}
			@Override
			public void mouseExited(final MouseEvent m) {
				preview.setBorder(border);
			}
		});
		
		button.addActionListener(a -> {
			final BufferedImage image = chooseImageFile();
			
			if (image != null) {
				setTile(image);
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
		final int size = Math.min(preview.getWidth(), preview.getHeight()) - PREVIEW_PADDING;
		
		ImageIcon icon = (ImageIcon)preview.getIcon();
		
		if (icon != null) {
			final Image image = icon.getImage();
			final Image smallImage = image.getScaledInstance(size, size, Image.SCALE_FAST);
			
			icon = new ImageIcon(smallImage);
			
			preview.setIcon(icon);
		}
	}
	
	public void removeTile() {
		tile = null;
		preview.setIcon(null);
	}
	
	public void setTile(final BufferedImage tile) {
		this.tile = tile;
		
		final ImageIcon icon = new ImageIcon(tile);
		
		preview.setIcon(icon);
		resizeIcon();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public BufferedImage getTile() {
		return tile;
	}
	
}
