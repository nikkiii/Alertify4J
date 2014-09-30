package org.nikkii.alertify4j.themes;

import org.nikkii.alertify4j.AlertifyColorPair;
import org.nikkii.alertify4j.AlertifyType;

import java.awt.Color;
import java.awt.Font;

/**
 * A theme for alerts using Bootstrap 3 colors.
 *
 * @author Nikki
 */
public class BootstrapTheme extends AlertifyTheme {
	/**
	 * The alert font.
	 */
	private static final Font ALERT_FONT = new Font("SansSerif", Font.PLAIN, 16);

	/**
	 * The color for a "LOG" alert (Bootstrap 2 btn-inverse).
	 */
	private static final AlertifyColorPair LOG_COLOR = new AlertifyColorPair(new Color(54, 54, 54), Color.WHITE);

	/**
	 * The color for an "INFO" alert (Bootstrap 3 btn-info).
	 */
	private static final AlertifyColorPair INFO_COLORS = new AlertifyColorPair(new Color(91,192,222), Color.WHITE);

	/**
	 * The color for a "WARNING" alert (Bootstrap 3 btn-warning).
	 */
	private static final AlertifyColorPair WARNING_COLORS = new AlertifyColorPair(new Color(240,173,78), Color.WHITE);

	/**
	 * The color for an "ERROR" alert (Bootstrap 3 btn-error).
	 */
	private static final AlertifyColorPair ERROR_COLOR = new AlertifyColorPair(new Color(217,83,79), Color.WHITE);

	/**
	 * The color for a "SUCCESS" alert (Bootstrap 3 btn-success).
	 */
	private static final AlertifyColorPair SUCCESS_COLOR = new AlertifyColorPair(new Color(92, 184, 92), Color.WHITE);

	/**
	 * Initialize the theme and colors.
	 */
	public BootstrapTheme() {
		initColor(AlertifyType.LOG, LOG_COLOR);
		initColor(AlertifyType.INFO, INFO_COLORS);
		initColor(AlertifyType.WARNING, WARNING_COLORS);
		initColor(AlertifyType.ERROR, ERROR_COLOR);
		initColor(AlertifyType.SUCCESS, SUCCESS_COLOR);
	}

	@Override
	public Font getFont() {
		return ALERT_FONT;
	}
}
