package de.sogomn.generator.util;

import java.io.File;

public final class FileUtils {
	
	private FileUtils() {
		//...
	}
	
	public static boolean isImage(final File file) {
		final String name = file.getName().toUpperCase();
		final boolean image = name.endsWith("PNG") || name.endsWith("JPG") || name.endsWith("GIF");
		
		return image;
	}
	
}
