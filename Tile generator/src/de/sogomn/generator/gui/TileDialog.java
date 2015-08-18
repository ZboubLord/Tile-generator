package de.sogomn.generator.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.sogomn.generator.ImageSet;
import de.sogomn.generator.util.FileUtils;
import de.sogomn.generator.util.ImageUtils;

public final class TileDialog {
	
	private JFrame frame;
	private JLabel previewBase, previewVertical, previewHorizontal;
	private JButton chooseBase, chooseVertical, chooseHorizontal;
	private JButton generate;
	
	private JFileChooser fileChooser;
	
	private ImageSet imageSet;
	
	public TileDialog() {
		frame = new JFrame();
		previewBase = new JLabel();
		previewVertical = new JLabel();
		previewHorizontal = new JLabel();
		chooseBase = new JButton("Choose base");
		chooseVertical = new JButton("Choose vertical");
		chooseHorizontal = new JButton("Choose horizontal");
		generate = new JButton("Generate");
		fileChooser = new JFileChooser();
		imageSet = new ImageSet();
		
		final JPanel panel = addComponents();
		
		chooseBase.addActionListener(a -> {
			final BufferedImage image = chooseImage();
			
			if (image != null) {
				final Image smallImage = image.getScaledInstance(previewBase.getWidth(), previewBase.getHeight(), BufferedImage.SCALE_FAST);
				final ImageIcon icon = new ImageIcon(smallImage);
				
				imageSet.setBase(image);
				previewBase.setIcon(icon);
			}
		});
		chooseVertical.addActionListener(a -> {
			final BufferedImage image = chooseImage();
			
			if (image != null) {
				final Image smallImage = image.getScaledInstance(previewVertical.getWidth(), previewVertical.getHeight(), BufferedImage.SCALE_FAST);
				final ImageIcon icon = new ImageIcon(smallImage);
				
				imageSet.setVertical(image);
				previewVertical.setIcon(icon);
			}
		});
		chooseHorizontal.addActionListener(a -> {
			final BufferedImage image = chooseImage();
			
			if (image != null) {
				final Image smallImage = image.getScaledInstance(previewHorizontal.getWidth(), previewHorizontal.getHeight(), BufferedImage.SCALE_FAST);
				final ImageIcon icon = new ImageIcon(smallImage);
				
				imageSet.setHorizontal(image);
				previewHorizontal.setIcon(icon);
			}
		});
		generate.addActionListener(a -> {
			final BufferedImage[] images = imageSet.generateTiles();
			
			if (images != null) {
				final File folder = chooseFolder();
				
				if (folder != null) {
					writeImages(images, folder);
				}
			}
		});
		
		previewBase.setPreferredSize(new Dimension(100, 100));
		previewBase.setHorizontalAlignment(JLabel.CENTER);
		previewVertical.setPreferredSize(new Dimension(100, 100));
		previewVertical.setHorizontalAlignment(JLabel.CENTER);
		previewHorizontal.setPreferredSize(new Dimension(100, 100));
		previewHorizontal.setHorizontalAlignment(JLabel.CENTER);
		
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setMinimumSize(new Dimension(500, 350));
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setTitle("Tile generator");
	}
	
	private JPanel addComponents() {
		final JPanel panel = new JPanel();
		final GridBagConstraints c = new GridBagConstraints();
		
		panel.setLayout(new GridBagLayout());
		panel.setBorder(new EmptyBorder(0, 3, 0, 3));
		
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(3, 3, 3, 3);
		c.fill = GridBagConstraints.BOTH;
		panel.add(previewBase, c);
		
		c.gridx = 1;
		panel.add(previewVertical, c);
		
		c.gridx = 2;
		panel.add(previewHorizontal, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(chooseBase, c);
		
		c.gridx = 1;
		panel.add(chooseVertical, c);
		
		c.gridx = 2;
		panel.add(chooseHorizontal, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		panel.add(generate, c);
		
		return panel;
	}
	
	private File chooseFolder() {
		fileChooser.setFileFilter(null);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		final int input = fileChooser.showSaveDialog(frame);
		
		if (input == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();
			
			return file;
		}
		
		return null;
	}
	
	private BufferedImage chooseImage() {
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "PNG", "JPG", "GIF"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		final int input = fileChooser.showOpenDialog(frame);
		
		if (input == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();
			
			if (FileUtils.isImage(file)) {
				final BufferedImage image = ImageUtils.loadExternal(file);
				
				return image;
			}
		}
		
		return null;
	}
	
	private void writeImages(final BufferedImage[] images, final File folder) {
		final String path = folder + File.separator;
		
		for (int i = 0; i < images.length; i++) {
			final BufferedImage image = images[i];
			final String absolutePath = path + "tile_" + i + ".png";
			
			ImageUtils.write(image, absolutePath);
		}
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
}
