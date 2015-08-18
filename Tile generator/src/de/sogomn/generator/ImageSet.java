package de.sogomn.generator;

import java.awt.image.BufferedImage;

public final class ImageSet {
	
	private BufferedImage base, vertical, horizontal;
	private BufferedImage innerMask, outerMask;
	
	public ImageSet() {
		//...
	}
	
	public BufferedImage[] generateTiles() {
		if (base == null || vertical == null || horizontal == null) {
			return null;
		}
		
		final int length = TileConstants.ALL_TILE_STRATEGIES.length;
		final BufferedImage[] images = new BufferedImage[length];
		
		for (int i = 0; i < length; i++) {
			final ITileStrategy strategy = TileConstants.ALL_TILE_STRATEGIES[i];
			final BufferedImage image = strategy.generate(base, vertical, horizontal, innerMask, outerMask);
			
			images[i] = image;
		}
		
		return images;
	}
	
	public void setBase(final BufferedImage base) {
		this.base = base;
	}
	
	public void setVertical(final BufferedImage vertical) {
		this.vertical = vertical;
	}
	
	public void setHorizontal(final BufferedImage horizontal) {
		this.horizontal = horizontal;
	}
	
	public void setInnerMask(final BufferedImage innerMask) {
		this.innerMask = innerMask;
	}
	
	public void setOuterMask(final BufferedImage outerMask) {
		this.outerMask = outerMask;
	}
	
}
