package de.sogomn.generator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.sogomn.generator.ImageSet;
import de.sogomn.generator.TileConstants;
import de.sogomn.generator.util.FileUtils;
import de.sogomn.generator.util.ImageUtils;

public final class TileDialog {
	
	private JFrame frame;
	private JLabel previewBase, previewVertical, previewHorizontal;
	private JButton chooseBase, chooseVertical, chooseHorizontal;
	private JButton generate;
	
	private JPanel previewPanel;
	
	private JFileChooser fileChooser;
	
	private ImageSet imageSet;
	
	private long resizeTimer;
	
	private static final int PREVIEW_PADDING = 35;
	private static final int RESIZE_INTERVAL = 500;
	
	public TileDialog() {
		frame = new JFrame();
		previewBase = new JLabel();
		previewVertical = new JLabel();
		previewHorizontal = new JLabel();
		chooseBase = new JButton("Choose base");
		chooseVertical = new JButton("Choose vertical");
		chooseHorizontal = new JButton("Choose horizontal");
		generate = new JButton("Generate");
		previewPanel = new JPanel();
		fileChooser = new JFileChooser();
		imageSet = new ImageSet();
		
		fileChooser.setCurrentDirectory(new File("/"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		chooseBase.addActionListener(a -> {
			final BufferedImage image = chooseImage();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				imageSet.setBase(image);
				previewBase.setIcon(icon);
				
				resizePreviews();
			}
		});
		chooseVertical.addActionListener(a -> {
			final BufferedImage image = chooseImage();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				imageSet.setVertical(image);
				previewVertical.setIcon(icon);
				
				resizePreviews();
			}
		});
		chooseHorizontal.addActionListener(a -> {
			final BufferedImage image = chooseImage();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				imageSet.setHorizontal(image);
				previewHorizontal.setIcon(icon);
				
				resizePreviews();
			}
		});
		generate.addActionListener(a -> {
			final BufferedImage[] images = imageSet.generateTiles();
			
			if (images != null) {
				final File file = chooseSaveFile();
				
				if (file != null) {
					writeImages(images, file);
					
					alert("Sprite sheet saved!");
				}
			}
		});
		
		previewBase.setPreferredSize(new Dimension(150, 150));
		previewBase.setMinimumSize(new Dimension(150, 150));
		previewBase.setMaximumSize(new Dimension(150, 150));
		previewBase.setHorizontalAlignment(JLabel.CENTER);
		previewBase.setBorder(new LineBorder(Color.GRAY, 2, true));
		
		previewVertical.setPreferredSize(new Dimension(150, 150));
		previewVertical.setMinimumSize(new Dimension(150, 150));
		previewVertical.setMaximumSize(new Dimension(150, 150));
		previewVertical.setHorizontalAlignment(JLabel.CENTER);
		previewVertical.setBorder(new LineBorder(Color.GRAY, 2, true));
		
		previewHorizontal.setPreferredSize(new Dimension(150, 150));
		previewHorizontal.setMinimumSize(new Dimension(150, 150));
		previewHorizontal.setMaximumSize(new Dimension(150, 150));
		previewHorizontal.setHorizontalAlignment(JLabel.CENTER);
		previewHorizontal.setBorder(new LineBorder(Color.GRAY, 2, true));
		
		chooseBase.setPreferredSize(new Dimension(150, 75));
		chooseVertical.setPreferredSize(new Dimension(150, 75));
		chooseHorizontal.setPreferredSize(new Dimension(150, 75));
		
		generate.setPreferredSize(new Dimension(450, 75));
		
		previewPanel.setPreferredSize(new Dimension(350, 350));
		previewPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		
		final BufferedImage icon = ImageUtils.load("/icon.png");
		final JPanel panel = createPanel();
		final ComponentAdapter resizeAdapter = new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent c) {
				final long now = System.currentTimeMillis();
				
				if (now - resizeTimer >= RESIZE_INTERVAL) {
					resizeTimer = now;
					
					resizePreviews();
				}
			}
		};
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panel);
		frame.addComponentListener(resizeAdapter);
		frame.setMinimumSize(new Dimension(850, 450));
		frame.setPreferredSize(new Dimension(950, 550));
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setTitle("Tile generator");
		frame.setIconImage(icon);
	}
	
	private void resizePreviews() {
		final int size = Math.min(previewBase.getWidth(), previewVertical.getHeight()) - PREVIEW_PADDING;
		
		Icon iconBase = previewBase.getIcon();
		Icon iconVertical = previewVertical.getIcon();
		Icon iconHorizontal = previewHorizontal.getIcon();
		
		if (iconBase != null) {
			final Image imageBase = ((ImageIcon)iconBase).getImage();
			final Image smallImageBase = imageBase.getScaledInstance(size, size, Image.SCALE_FAST);
			
			iconBase = new ImageIcon(smallImageBase);
			
			previewBase.setIcon(iconBase);
		}
		
		if (iconVertical != null) {
			final Image imageVertical = ((ImageIcon)iconVertical).getImage();
			final Image smallImageVertical = imageVertical.getScaledInstance(size, size, Image.SCALE_FAST);
			
			iconVertical = new ImageIcon(smallImageVertical);
			
			previewVertical.setIcon(iconVertical);
		}
		
		if (iconHorizontal != null) {
			final Image imageHorizontal = ((ImageIcon)iconHorizontal).getImage();
			final Image smallImageHorizontal = imageHorizontal.getScaledInstance(size, size, Image.SCALE_FAST);
			
			iconHorizontal = new ImageIcon(smallImageHorizontal);
			
			previewHorizontal.setIcon(iconHorizontal);
		}
	}
	
	private JPanel createPanel() {
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
		panel.add(chooseBase, c);
		
		c.gridx = 1;
		panel.add(chooseVertical, c);
		
		c.gridx = 2;
		panel.add(chooseHorizontal, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		panel.add(generate, c);
		
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.weightx = 5.0;
		panel.add(previewPanel, c);
		
		return panel;
	}
	
	private File chooseSaveFile() {
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "PNG"));
		
		final int input = fileChooser.showSaveDialog(frame);
		
		if (input == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();
			
			return file;
		}
		
		return null;
	}
	
	private BufferedImage chooseImage() {
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.png, *.jpg, *.gif", "PNG", "JPG", "GIF"));
		
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
	
	private void alert(final String message) {
		JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void writeImages(final BufferedImage[] images, final File file) {
		final BufferedImage firstImage = images[0];
		final int width = firstImage.getWidth();
		final int height = firstImage.getHeight();
		final int tilesWide = (int)Math.sqrt(TileConstants.ALL_TILE_STRATEGIES.length);
		
		final BufferedImage spriteSheet = ImageUtils.toSpriteSheet(width, height, tilesWide, images);
		
		String path = file.getPath();
		
		if (!path.toUpperCase().endsWith(".PNG")) {
			path += ".png";
		}
		
		ImageUtils.write(spriteSheet, path);
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
}
