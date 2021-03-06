package de.sogomn.generator;

import static de.sogomn.generator.util.ImageUtils.combine;
import static de.sogomn.generator.util.ImageUtils.combineAndBlend;
import static de.sogomn.generator.util.ImageUtils.mask;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import de.sogomn.generator.util.ImageUtils;

public final class TileConstants {
	
	public static final ITileStrategy BASE = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		return base;
	};
	
	public static final ITileStrategy TOP = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage image = combine(base, top);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage image = combine(base, bottom);
		
		return image;
	};
	
	public static final ITileStrategy LEFT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage image = combine(base, left);
		
		return image;
	};
	
	public static final ITileStrategy RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage image = combine(base, right);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		BufferedImage image = combine(base, bottom);
		
		image = combine(image, top);
		
		return image;
	};
	
	public static final ITileStrategy TOP_LEFT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(top, left, blending);
		
		BufferedImage image = combine(base, decoration);
		
		outerMask = clipTop(outerMask);
		outerMask = clipLeft(outerMask);
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy TOP_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(top, right, blending);
		
		BufferedImage image = combine(base, decoration);
		
		outerMask = clipTop(outerMask);
		outerMask = clipRight(outerMask);
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_LEFT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(bottom, left, blending);
		
		BufferedImage image = combine(base, decoration);
		
		outerMask = clipBottom(outerMask);
		outerMask = clipLeft(outerMask);
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(bottom, right, blending);
		
		BufferedImage image = combine(base, decoration);
		
		outerMask = clipBottom(outerMask);
		outerMask = clipRight(outerMask);
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy LEFT_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(left, right, blending);
		final BufferedImage image = combine(base, decoration);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_LEFT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(top, combineAndBlend(bottom, left, blending), blending);
		
		BufferedImage image = combine(base, decoration);
		
		outerMask = clipLeft(outerMask);
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(top, combineAndBlend(bottom, right, blending), blending);
		
		BufferedImage image = combine(base, decoration);
		
		outerMask = clipRight(outerMask);
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy TOP_LEFT_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(top, combineAndBlend(left, right, blending), blending);
		
		BufferedImage image = combine(base, decoration);
		
		outerMask = clipTop(outerMask);
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy BOTTOM_LEFT_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(bottom, combineAndBlend(left, right, blending), blending);
		
		BufferedImage image = combine(base, decoration);
		
		outerMask = clipBottom(outerMask);
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy TOP_BOTTOM_LEFT_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		final BufferedImage decoration = combineAndBlend(top, combineAndBlend(bottom, combineAndBlend(left, right, blending), blending), blending);
		
		BufferedImage image = combine(base, decoration);
		
		image = mask(image, outerMask);
		
		return image;
	};
	
	public static final ITileStrategy CORNER_TOP_LEFT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		BufferedImage image = ImageUtils.createOverlap(left, top);
		
		image = combine(base, image);
		
		return image;
	};
	
	public static final ITileStrategy CORNER_TOP_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		BufferedImage image = ImageUtils.createOverlap(right, top);
		
		image = combine(base, image);
		
		return image;
	};
	
	public static final ITileStrategy CORNER_BOTTOM_LEFT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		BufferedImage image = ImageUtils.createOverlap(bottom, left);
		
		image = combine(base, image);
		
		return image;
	};
	
	public static final ITileStrategy CORNER_BOTTOM_RIGHT = (base, top, bottom, left, right, blending, innerMask, outerMask) -> {
		BufferedImage image = ImageUtils.createOverlap(bottom, right);
		
		image = combine(base, image);
		
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
		TOP_BOTTOM_LEFT_RIGHT,
		CORNER_TOP_LEFT,
		CORNER_TOP_RIGHT,
		CORNER_BOTTOM_LEFT,
		CORNER_BOTTOM_RIGHT
	};
	
	private TileConstants() {
		//...
	}
	
	public static Area clipTop(final Area mask) {
		final Area area = new Area(mask);
		final Rectangle bounds = area.getBounds();
		final Rectangle bottom = new Rectangle(bounds.x, bounds.y + bounds.height / 2, bounds.width, bounds.height / 2);
		
		area.add(new Area(bottom));
		
		return area;
	}
	
	private static Area clipBottom(final Area mask) {
		final Area area = new Area(mask);
		final Rectangle bounds = area.getBounds();
		final Rectangle top = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height / 2);
		
		area.add(new Area(top));
		
		return area;
	}
	
	private static Area clipLeft(final Area mask) {
		final Area area = new Area(mask);
		final Rectangle bounds = area.getBounds();
		final Rectangle right = new Rectangle(bounds.x + bounds.width / 2, bounds.y, bounds.width / 2, bounds.height);
		
		area.add(new Area(right));
		
		return area;
	}
	
	private static Area clipRight(final Area mask) {
		final Area area = new Area(mask);
		final Rectangle bounds = area.getBounds();
		final Rectangle left = new Rectangle(bounds.x, bounds.y, bounds.width / 2, bounds.height);
		
		area.add(new Area(left));
		
		return area;
	}
	
}
