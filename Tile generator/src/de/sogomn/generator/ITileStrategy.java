package de.sogomn.generator;

import java.awt.geom.Area;
import java.awt.image.BufferedImage;


@FunctionalInterface
public interface ITileStrategy {
	
	BufferedImage generate(final BufferedImage base, final BufferedImage top, final BufferedImage bottom, final BufferedImage left, final BufferedImage right, final float blending, final Area innerMask, final Area outerMask);
	
}
