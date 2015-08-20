package de.sogomn.generator.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

final class OuterMaskScrollLabel extends AbstractScrollLabel {
	
	private static final int PADDING = 25;
	private static final int MAX = 100;
	private static final String TEXT = "Use wheel";
	
	public OuterMaskScrollLabel() {
		//...
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
		
		final int fontSize = (int)(size / TEXT.length() / 0.7);
		final Font font = new Font("Consolas", Font.BOLD, fontSize);
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(x, y, size, size, (int)rounding, (int)rounding);
		g.setColor(Color.WHITE);
		
		g.setFont(font);
		
		final FontMetrics fm = g.getFontMetrics();
		final int xPos = x + size / 2 - fm.stringWidth(TEXT) / 2;
		final int yPos = y + size / 2 + fm.getDescent();
		
		g.drawString(TEXT, xPos, yPos);
	}
	
	public Area getArea(final int width, final int height) {
		final double widthAmount = (double)amount / MAX * width;
		final double heightAmount = (double)amount / MAX * height;
		
		final RoundRectangle2D rectangle = new RoundRectangle2D.Double(0, 0, width, height, widthAmount, heightAmount);
		final Area area = new Area(rectangle);
		
		return area;
	}
	
}
