package de.sogomn.generator;

import static de.sogomn.generator.util.ImageUtils.combine;

import java.awt.image.BufferedImage;

public final class TileConstants {
	
	public static final ITileStrategy BASE = (base, top, bottom, left, right, innerMask, outerMask) -> {
		return base;
	};
	
	public static final ITileStrategy TOP = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(base, top);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(base, bottom);
		
		return image;
	};
	
	public static final ITileStrategy LEFT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(base, left);
		
		return image;
	};
	
	public static final ITileStrategy RIGHT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(base, right);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(base, bottom), top);
		
		return image;
	};
	
	public static final ITileStrategy TOP_LEFT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(base, left), top);
		
		return image;
	};
	
	public static final ITileStrategy TOP_RIGHT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(base, right), top);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_LEFT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(base, left), bottom);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_RIGHT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(base, right), bottom);
		
		return image;
	};
	
	public static final ITileStrategy LEFT_RIGHT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(base, right), left);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_LEFT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(combine(base, left), bottom), top);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_RIGHT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(combine(base, right), bottom), top);
		
		return image;
	};
	
	public static final ITileStrategy TOP_LEFT_RIGHT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(combine(base, right), left), top);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_LEFT_RIGHT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(combine(base, right), left), bottom);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_LEFT_RIGHT = (base, top, bottom, left, right, innerMask, outerMask) -> {
		final BufferedImage image = combine(combine(combine(combine(base, right), left), bottom), top);
		
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
