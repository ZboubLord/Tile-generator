package de.sogomn.generator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public abstract class AbstractScrollLabel {
	
	protected JLabel label;
	
	protected int amount;
	
	private static final int SPEED = 5;
	
	public AbstractScrollLabel() {
		label = new JLabel() {
			private static final long serialVersionUID = -9124138278739901745L;
			
			@Override
			protected void paintComponent(final Graphics g) {
				super.paintComponent(g);
				
				paintLabel(g);
			}
		};
		
		label.setPreferredSize(new Dimension(150, 150));
		label.setMinimumSize(new Dimension(150, 150));
		label.setMaximumSize(new Dimension(150, 150));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(new LineBorder(Color.GRAY, 2, true));
		label.addMouseWheelListener(m -> {
			amount += m.getWheelRotation() * SPEED;
			
			onScroll();
			
			label.repaint();
		});
	}
	
	protected abstract void onScroll();
	
	protected abstract void paintLabel(final Graphics g);
	
	public final int getAmount() {
		return amount;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
}
