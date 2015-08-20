package de.sogomn.generator.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.TitledBorder;

final class OuterMaskScrollLabel extends AbstractScrollLabel {
	
	private static final int PADDING = 25;
	private static final int MAX = 100;
	
	public OuterMaskScrollLabel() {
		label.setBorder(new TitledBorder("Round edges (use mouse wheel)"));
	}
	
	@Override
	protected void onScroll() {
		if (amount < 0) {
			amount = 0;
		} else if (amount > MAX) {
			amount = MAX;
		}
	}
	
	@Override
	protected void paintLabel(final Graphics g2) {
		final Graphics2D g = (Graphics2D)g2;
		
		final int width = label.getWidth();
		final int height = label.getHeight();
		final int size = Math.min(width, height) - PADDING * 2;
		final int x = width / 2 - size / 2;
		final int y = height / 2 - size / 2;
		final double rounding = (double)amount / MAX * size;
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(x, y, size, size, (int)rounding, (int)rounding);
	}
	
	public Area getArea(final int width, final int height) {
		final double widthAmount = (double)amount / MAX * width;
		final double heightAmount = (double)amount / MAX * height;
		
		final RoundRectangle2D rectangle = new RoundRectangle2D.Double(0, 0, width, height, widthAmount, heightAmount);
		final Area area = new Area(rectangle);
		
		return area;
	}
	
}
