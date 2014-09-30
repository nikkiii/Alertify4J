package org.nikkii.alertify4j.themes;

import org.nikkii.alertify4j.AlertifyColorPair;
import org.nikkii.alertify4j.AlertifyType;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 * A Theme wrapper for customizing the Alertify look.
 * @author Nikki
 */
public abstract class AlertifyTheme {
	/**
	 * The color map.
	 */
	private Map<AlertifyType, AlertifyColorPair> colorsMap = new HashMap<>();

	/**
	 * Put a color type into the map.
	 *
	 * @param type The alert type.
	 * @param colors The color pair.
	 */
	public void initColor(AlertifyType type, AlertifyColorPair colors) {
		colorsMap.put(type, colors);
	}

	/**
	 * Get the color pair for the specified type.
	 * If a color pair isn't registered, attempt to fall back to LOG.
	 *
	 * @param type The alert type.
	 * @return The color pair.
	 */
	public AlertifyColorPair getColors(AlertifyType type) {
		if (!colorsMap.containsKey(type)) {
			if (type == AlertifyType.LOG) {
				return null; // Nothing we can do!
			}

			return colorsMap.get(AlertifyType.LOG);
		}

		return colorsMap.get(type);
	}

	/**
	 * Get the theme font.
	 * @return The font.
	 */
	public abstract Font getFont();
}
