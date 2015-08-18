package de.sogomn.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.sogomn.generator.util.ImageUtils;

public final class TileConstants {
	
	public static final ITileStrategy BASE = (base, vertical, horizontal, innerMask, outerMask) -> {
		return base;
	};
	
	public static final ITileStrategy TOP = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage top = vertical.getSubimage(0, 0, vertical.getWidth(), vertical.getHeight() / 2);
		final BufferedImage image = ImageUtils.combine(base, top);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage half = vertical.getSubimage(0, vertical.getHeight() / 2, vertical.getWidth(), vertical.getHeight() / 2);
		final BufferedImage bottom = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = bottom.createGraphics();
		
		g.drawImage(half, 0, half.getHeight(), null);
		g.dispose();
		
		final BufferedImage image = ImageUtils.combine(base, bottom);
		
		return image;
	};
	
	public static final ITileStrategy LEFT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage left = horizontal.getSubimage(0, 0, horizontal.getWidth() / 2, horizontal.getHeight());
		final BufferedImage image = ImageUtils.combine(base, left);
		
		return image;
	};
	
	public static final ITileStrategy RIGHT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage half = horizontal.getSubimage(horizontal.getWidth() / 2, 0, horizontal.getWidth() / 2, horizontal.getHeight());
		final BufferedImage right = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = right.createGraphics();
		
		g.drawImage(half, half.getWidth(), 0, null);
		g.dispose();
		
		final BufferedImage image = ImageUtils.combine(base, right);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage image = ImageUtils.combine(base, vertical);
		
		return image;
	};
	
	public static final ITileStrategy TOP_LEFT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage top = vertical.getSubimage(0, 0, vertical.getWidth(), vertical.getHeight() / 2);
		final BufferedImage left = LEFT.generate(base, vertical, horizontal, innerMask, outerMask);
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, left), top);
		
		return image;
	};
	
	public static final ITileStrategy TOP_RIGHT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage top = vertical.getSubimage(0, 0, vertical.getWidth(), vertical.getHeight() / 2);
		final BufferedImage right = RIGHT.generate(base, vertical, horizontal, innerMask, outerMask);
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, right), top);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_LEFT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage left = LEFT.generate(base, vertical, horizontal, innerMask, outerMask);
		final BufferedImage half = vertical.getSubimage(0, vertical.getHeight() / 2, vertical.getWidth(), vertical.getHeight() / 2);
		final BufferedImage bottom = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = bottom.createGraphics();
		
		g.drawImage(half, 0, half.getHeight(), null);
		g.dispose();
		
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, left), bottom);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_RIGHT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage right = RIGHT.generate(base, vertical, horizontal, innerMask, outerMask);
		final BufferedImage half = vertical.getSubimage(0, vertical.getHeight() / 2, vertical.getWidth(), vertical.getHeight() / 2);
		final BufferedImage bottom = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = bottom.createGraphics();
		
		g.drawImage(half, 0, half.getHeight(), null);
		g.dispose();
		
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, right), bottom);
		
		return image;
	};
	
	public static final ITileStrategy LEFT_RIGHT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage image = ImageUtils.combine(base, horizontal);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_LEFT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage left = LEFT.generate(base, vertical, horizontal, innerMask, outerMask);
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, left), vertical);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_RIGHT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage right = RIGHT.generate(base, vertical, horizontal, innerMask, outerMask);
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, right), vertical);
		
		return image;
	};
	
	public static final ITileStrategy TOP_LEFT_RIGHT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage top = vertical.getSubimage(0, 0, vertical.getWidth(), vertical.getHeight() / 2);
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, horizontal), top);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_LEFT_RIGHT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage half = vertical.getSubimage(0, vertical.getHeight() / 2, vertical.getWidth(), vertical.getHeight() / 2);
		final BufferedImage bottom = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = bottom.createGraphics();
		
		g.drawImage(half, 0, half.getHeight(), null);
		g.dispose();
		
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, horizontal), bottom);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_LEFT_RIGHT = (base, vertical, horizontal, innerMask, outerMask) -> {
		final BufferedImage image = ImageUtils.combine(ImageUtils.combine(base, horizontal), vertical);
		
		return image;
	};
	
	public static final ITileStrategy[] ALL_TILE_STRATEGIES = {
		BASE,
		TOP,
		BOTTOM,
		LEFT,
		RIGHT,
		TOP_BOTTOM,
		TOP_LEFT,
		TOP_RIGHT,
		BOTTOM_LEFT,
		BOTTOM_RIGHT,
		LEFT_RIGHT,
		TOP_BOTTOM_LEFT,
		TOP_BOTTOM_RIGHT,
		TOP_LEFT_RIGHT,
		BOTTOM_LEFT_RIGHT,
		TOP_BOTTOM_LEFT_RIGHT
	};
	
	private TileConstants() {
		//...
	}
	
}
