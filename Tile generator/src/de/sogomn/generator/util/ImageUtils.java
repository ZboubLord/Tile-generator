package de.sogomn.generator.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ImageUtils {
	
	private ImageUtils() {
		//...
	}
	
	public static BufferedImage load(final String path) {
		try {
			final BufferedImage image = ImageIO.read(ImageUtils.class.getResource(path));
			
			return image;
		} catch (final IOException ex) {
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static BufferedImage combine(final BufferedImage base, final BufferedImage image) {
		final BufferedImage result = new BufferedImage(base.getWidth(), base.getHeight(), base.getType());
		final Graphics2D g = result.createGraphics();
		
		g.drawImage(base, 0, 0, null);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		return result;
	}
	
	public static void write(final BufferedImage image, final String path) {
		final File file = new File(path);
		
		try {
			ImageIO.write(image, "png", file);
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static BufferedImage loadExternal(final File file) {
		try {
			final BufferedImage image = ImageIO.read(file);
			
			return image;
		} catch (final IOException ex) {
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static BufferedImage toSpriteSheet(final int tileWidth, final int tileHeight, final int tilesWide, final BufferedImage... images) {
		final int tilesHigh = images.length / tilesWide;
		final int width = tilesWide * tileWidth;
		final int height = tilesHigh * tileHeight;
		final BufferedImage spriteSheet = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = spriteSheet.createGraphics();
		
		for (int i = 0; i < images.length; i++) {
			final BufferedImage image = images[i];
			final int x = (i % tilesWide) * tileWidth;
			final int y = (i / tilesWide) * tileHeight;
			
			g.drawImage(image, x, y, null);
		}
		
		g.dispose();
		
		return spriteSheet;
	}
	
}
