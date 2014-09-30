package org.nikkii.alertify4j;

import java.awt.Color;

/**
 * A class representing a color pair.
 *
 * @author Nikki
 */
public class AlertifyColorPair {
	/**
	 * The background color.
	 */
	private final Color background;

	/**
	 * The foreground/text color.
	 */
	private final Color foreground;

	/**
	 * Construct a new color pair.
	 *
	 * @param background The background color.
	 * @param foreground The foreground color.
	 */
	public AlertifyColorPair(Color background, Color foreground) {
		this.background = background;
		this.foreground = foreground;
	}

	/**
	 * Get the background color.
	 *
	 * @return The background color.
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Get the foreground color.
	 *
	 * @return The foreground color.
	 */
	public Color getForeground() {
		return foreground;
	}
}
