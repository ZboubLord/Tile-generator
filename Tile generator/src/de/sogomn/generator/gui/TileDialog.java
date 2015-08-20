package de.sogomn.generator.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.sogomn.generator.ImageSet;
import de.sogomn.generator.util.ImageUtils;


public final class TileDialog {
	
	private JFrame frame;
	private ChoicePanel base, top, bottom, left, right;
	private JButton generate, save;
	private OuterMaskScrollLabel outerMaskScrollLabel;
	private JSlider blendSlider;
	private JPanel previewPanel;
	
	private JFileChooser fileChooser;
	
	private ImageSet imageSet;
	private BufferedImage spriteSheet;
	
	private long resizeTimer;
	
	private static final int PREVIEW_PADDING = 25;
	private static final int RESIZE_INTERVAL = 500;
	
	public TileDialog() {
		frame = new JFrame();
		
		base = new ChoicePanel("Base");
		top = new ChoicePanel("Top");
		bottom = new ChoicePanel("Bottom");
		left = new ChoicePanel("Left");
		right = new ChoicePanel("Right");
		
		generate = new JButton("Generate");
		save = new JButton("Save");
		
		outerMaskScrollLabel = new OuterMaskScrollLabel();
		blendSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		
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
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "PNG"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		blendSlider.setBorder(new TitledBorder("Blending"));
		
		generate.addActionListener(a -> {
			updateImageSet();
			
			spriteSheet = imageSet.generateSpriteSheet();
			
			if (spriteSheet == null) {
				alert("You need at least one base tile!");
			}
			
			previewPanel.repaint();
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
		
		previewPanel.setPreferredSize(new Dimension(350, 350));
		previewPanel.setBorder(new TitledBorder("Preview"));
		
		final BufferedImage icon = ImageUtils.load("/icon.png");
		final JPanel panel = createPanel();
		final ComponentAdapter resizeAdapter = new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent c) {
				final long now = System.currentTimeMillis();
				
				if (now - resizeTimer >= RESIZE_INTERVAL) {
					resizeTimer = now;
					
					previewPanel.repaint();
				}
			}
		};
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panel);
		frame.addComponentListener(resizeAdapter);
		frame.setMinimumSize(new Dimension(1400, 500));
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setTitle("Tile generator");
		frame.setIconImage(icon);
	}
	
	private JPanel createPanel() {
		final JPanel panel = new JPanel();
		final GridBagConstraints c = new GridBagConstraints();
		
		panel.setLayout(new GridBagLayout());
		panel.setBorder(new EmptyBorder(3, 3, 3, 3));
		
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(3, 3, 3, 3);
		c.fill = GridBagConstraints.BOTH;
		panel.add(base.getPanel(), c);
		
		c.gridx = 1;
		panel.add(top.getPanel(), c);
		
		c.gridx = 2;
		panel.add(bottom.getPanel(), c);
		
		c.gridx = 3;
		panel.add(left.getPanel(), c);
		
		c.gridx = 4;
		panel.add(right.getPanel(), c);
		
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
		c.gridheight = 2;
		panel.add(outerMaskScrollLabel.getLabel(), c);
		
		c.gridy = 2;
		c.gridheight = 1;
		panel.add(blendSlider, c);
		
		c.gridx = 6;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 5.0;
		panel.add(previewPanel, c);
		
		return panel;
	}
	
	private void updateImageSet() {
		final BufferedImage baseTile = base.getTile();
		final BufferedImage topTile = top.getTile();
		final BufferedImage bottomTile = bottom.getTile();
		final BufferedImage leftTile = left.getTile();
		final BufferedImage rightTile = right.getTile();
		
		if (baseTile != null) {
			final Area outerMask = outerMaskScrollLabel.getArea(baseTile.getWidth(), baseTile.getHeight());
			final float blending = (float)blendSlider.getValue() / blendSlider.getMaximum();
			
			imageSet.setBase(baseTile);
			imageSet.setTop(topTile);
			imageSet.setBottom(bottomTile);
			imageSet.setLeft(leftTile);
			imageSet.setRight(rightTile);
			imageSet.setOuterMask(outerMask);
			imageSet.setBlending(blending);
		}
	}
	
	private File chooseSaveFile() {
		final int input = fileChooser.showSaveDialog(frame);
		
		if (input == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();
			
			return file;
		}
		
		return null;
	}
	
	private void alert(final String message) {
		JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
}
