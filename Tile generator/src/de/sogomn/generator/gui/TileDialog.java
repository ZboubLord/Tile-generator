package de.sogomn.generator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	private JLabel previewBase, previewTop, previewBottom, previewLeft, previewRight;
	private JButton chooseBase, chooseTop, chooseBottom, chooseLeft, chooseRight;
	private JButton generate, save;
	
	private JPanel previewPanel;
	
	private JFileChooser fileChooser;
	
	private ImageSet imageSet;
	private BufferedImage spriteSheet;
	
	private long resizeTimer;
	
	private static final int PREVIEW_PADDING = 25;
	private static final int RESIZE_INTERVAL = 500;
	
	public TileDialog() {
		frame = new JFrame();
		
		previewBase = new JLabel();
		previewTop = new JLabel();
		previewBottom = new JLabel();
		previewLeft = new JLabel();
		previewRight = new JLabel();
		
		chooseBase = new JButton("Choose base");
		chooseTop = new JButton("Choose top");
		chooseBottom = new JButton("Choose bottom");
		chooseLeft = new JButton("Choose left");
		chooseRight = new JButton("Choose right");
		
		generate = new JButton("Generate");
		save = new JButton("Save");
		
		previewPanel = new JPanel() {
			private static final long serialVersionUID = -4424492961331659573L;
			
			@Override
			protected void paintComponent(final Graphics g) {
				final int width = getWidth();
				final int height = getHeight();
				final int size = Math.min(width, height) - PREVIEW_PADDING;
				final int x = width / 2 - size / 2;
				final int y = height / 2 - size / 2;
				
				super.paintComponent(g);
				
				g.drawImage(spriteSheet, x, y, size, size, null);
			}
		};
		fileChooser = new JFileChooser();
		imageSet = new ImageSet();
		
		fileChooser.setCurrentDirectory(new File("/"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		chooseBase.addActionListener(a -> {
			final BufferedImage image = chooseImageFile();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				imageSet.setBase(image);
				previewBase.setIcon(icon);
				
				resizePreviews();
			}
		});
		chooseTop.addActionListener(a -> {
			final BufferedImage image = chooseImageFile();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				imageSet.setTop(image);
				previewTop.setIcon(icon);
				
				resizePreviews();
			}
		});
		chooseBottom.addActionListener(a -> {
			final BufferedImage image = chooseImageFile();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				imageSet.setBottom(image);
				previewBottom.setIcon(icon);
				
				resizePreviews();
			}
		});
		chooseLeft.addActionListener(a -> {
			final BufferedImage image = chooseImageFile();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				imageSet.setLeft(image);
				previewLeft.setIcon(icon);
				
				resizePreviews();
			}
		});
		chooseRight.addActionListener(a -> {
			final BufferedImage image = chooseImageFile();
			
			if (image != null) {
				final ImageIcon icon = new ImageIcon(image);
				
				imageSet.setRight(image);
				previewRight.setIcon(icon);
				
				resizePreviews();
			}
		});
		generate.addActionListener(a -> {
			final BufferedImage[] images = imageSet.generateTiles();
			
			if (images != null) {
				spriteSheet = generateSpriteSheet(images);
				
				previewPanel.repaint();
			}
		});
		save.addActionListener(a -> {
			if (spriteSheet != null) {
				final File file = chooseSaveFile();
				
				if (file != null) {
					String path = file.getPath();
					
					if (!path.toUpperCase().endsWith(".PNG")) {
						path += ".png";
					}
					
					ImageUtils.write(spriteSheet, path);
					
					alert("Sprite sheet saved!");
				}
			}
		});
		
		setPreferences(previewBase);
		setPreferences(previewTop);
		setPreferences(previewBottom);
		setPreferences(previewLeft);
		setPreferences(previewRight);
		
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
					previewPanel.repaint();
				}
			}
		};
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panel);
		frame.addComponentListener(resizeAdapter);
		frame.setMinimumSize(new Dimension(1200, 400));
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setTitle("Tile generator");
		frame.setIconImage(icon);
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
		panel.add(previewTop, c);
		
		c.gridx = 2;
		panel.add(previewBottom, c);
		
		c.gridx = 3;
		panel.add(previewLeft, c);
		
		c.gridx = 4;
		panel.add(previewRight, c);
		
		c.gridx = 0;
		c.gridy = 1;
		panel.add(chooseBase, c);
		
		c.gridx = 1;
		panel.add(chooseTop, c);
		
		c.gridx = 2;
		panel.add(chooseBottom, c);
		
		c.gridx = 3;
		panel.add(chooseLeft, c);
		
		c.gridx = 4;
		panel.add(chooseRight, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		panel.add(generate, c);
		
		c.gridx = 3;
		c.gridwidth = 2;
		panel.add(save, c);
		
		c.gridx = 5;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.weightx = 5.0;
		panel.add(previewPanel, c);
		
		return panel;
	}
	
	private void setPreferences(final JLabel label) {
		label.setPreferredSize(new Dimension(150, 150));
		label.setMinimumSize(new Dimension(150, 150));
		label.setMaximumSize(new Dimension(150, 150));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(new LineBorder(Color.GRAY, 2, true));
	}
	
	private void resizeIcon(final JLabel label) {
		final int size = Math.min(label.getWidth(), label.getHeight());
		
		Icon icon = label.getIcon();
		
		if (icon != null) {
			final Image image = ((ImageIcon)icon).getImage();
			final Image smallImage = image.getScaledInstance(size, size, Image.SCALE_FAST);
			
			icon = new ImageIcon(smallImage);
			
			label.setIcon(icon);
		}
	}
	
	private void resizePreviews() {
		resizeIcon(previewBase);
		resizeIcon(previewTop);
		resizeIcon(previewBottom);
		resizeIcon(previewLeft);
		resizeIcon(previewRight);
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
	
	private BufferedImage chooseImageFile() {
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
	
	private BufferedImage generateSpriteSheet(final BufferedImage[] images) {
		final BufferedImage firstImage = images[0];
		final int width = firstImage.getWidth();
		final int height = firstImage.getHeight();
		final int tilesWide = (int)Math.sqrt(TileConstants.ALL_TILE_STRATEGIES.length);
		final BufferedImage spriteSheet = ImageUtils.toSpriteSheet(width, height, tilesWide, images);
		
		return spriteSheet;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
}
