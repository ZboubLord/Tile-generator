package de.sogomn.generator;

import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import de.sogomn.generator.util.ImageUtils;

public final class ImageSet {
	
	private BufferedImage base, top, bottom, left, right;
	private float blending;
	
	private Area innerMask, outerMask;
	
	public ImageSet() {
		innerMask = new Area();
		outerMask = new Area();
	}
	
	public BufferedImage[] generateTiles() {
		if (base == null) {
			return null;
		}
		
		final int length = TileConstants.ALL_TILE_STRATEGIES.length;
		final BufferedImage[] images = new BufferedImage[length];
		
		for (int i = 0; i < length; i++) {
			final ITileStrategy strategy = TileConstants.ALL_TILE_STRATEGIES[i];
			final BufferedImage image = strategy.generate(base, top, bottom, left, right, blending, innerMask, outerMask);
			
			images[i] = image;
		}
		
		return images;
	}
	
	public BufferedImage generateSpriteSheet() {
		final BufferedImage[] images = generateTiles();
		
		if (images != null) {
			final BufferedImage firstImage = images[0];
			final int width = firstImage.getWidth();
			final int height = firstImage.getHeight();
			final int tilesWide = (int)Math.ceil(Math.sqrt(TileConstants.ALL_TILE_STRATEGIES.length));
			final BufferedImage spriteSheet = ImageUtils.toSpriteSheet(width, height, tilesWide, images);
			
			return spriteSheet;
		} else {
			return null;
		}
	}
	
	public void setBase(final BufferedImage base) {
		this.base = base;
	}
	
	public void setTop(final BufferedImage top) {
		this.top = top;
	}
	
	public void setBottom(final BufferedImage bottom) {
		this.bottom = bottom;
	}
	
	public void setLeft(final BufferedImage left) {
		this.left = left;
	}
	
	public void setRight(final BufferedImage right) {
		this.right = right;
	}
	
	public void setBlending(final float blending) {
		this.blending = blending;
	}
	
	public void setInnerMask(final Area innerMask) {
		this.innerMask = innerMask;
	}
	
	public void setOuterMask(final Area outerMask) {
		this.outerMask = outerMask;
	}
	
}
