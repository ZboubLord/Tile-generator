package de.sogomn.generator;

import java.awt.image.BufferedImage;


@FunctionalInterface
public interface ITileStrategy {
	
	BufferedImage generate(final BufferedImage base, final BufferedImage vertical, final BufferedImage horizontal, final BufferedImage innerMask, final BufferedImage outerMask);
	
}
