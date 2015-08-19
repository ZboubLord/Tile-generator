package de.sogomn.generator;

import java.awt.image.BufferedImage;


@FunctionalInterface
public interface ITileStrategy {
	
	BufferedImage generate(final BufferedImage base, final BufferedImage top, final BufferedImage bottom, final BufferedImage left, final BufferedImage right, final BufferedImage innerMask, final BufferedImage outerMask);
	
}
