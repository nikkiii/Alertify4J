package org.nikkii.alertify4j;

import org.nikkii.alertify4j.ui.AlertifyWindowClick;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * A basic Builder to build alert configs.
 *
 * @author Nikki
 */
public class AlertifyBuilder {

	/**
	 * The default type.
	 */
	private AlertifyType type = AlertifyType.LOG;

	/**
	 * The default label. This is updated by text and icon, or replaced if label is called.
	 */
	private JLabel label = new JLabel();

	/**
	 * The close delay.
	 */
	private long closeDelay = 0;

	/**
	 * The window callback.
	 */
	private AlertifyWindowClick callback;

	/**
	 * Set the alert type.
	 *
	 * @param type The alert type.
	 * @return The builder instance, for chaining.
	 */
	public AlertifyBuilder type(AlertifyType type) {
		this.type = type;
		return this;
	}

	/**
	 * Set the alert text.
	 *
	 * @param text The alert text.
	 * @return The builder instance, for chaining.
	 */
	public AlertifyBuilder text(String text) {
		label.setText(text);
		return this;
	}

	/**
	 * Set the alert icon.
	 *
	 * @param icon The alert icon.
	 * @return The builder instance, for chaining.
	 */
	public AlertifyBuilder icon(Icon icon) {
		label.setIcon(icon);
		return this;
	}

	/**
	 * Set the alert label. This will overwrite the text and icon already set.
	 *
	 * @param label The {@link JLabel}. This will still take the foreground and font settings set in the theme!
	 * @return The builder instance, for chaining.
	 */
	public AlertifyBuilder label(JLabel label) {
		this.label = label;
		return this;
	}

	/**
	 * Set the auto close delay.
	 *
	 * @param closeDelay The auto close delay, in milliseconds.
	 * @return The builder instance, for chaining.
	 */
	public AlertifyBuilder autoClose(long closeDelay) {
		this.closeDelay = closeDelay;
		return this;
	}

	/**
	 * Set the callback used on click.
	 *
	 * @param callback The callback.
	 * @return The builder instance, for chaining.
	 */
	public AlertifyBuilder callback(AlertifyWindowClick callback) {
		this.callback = callback;
		return this;
	}

	/**
	 * Construct an {@link AlertifyConfig} from this Builder.
	 *
	 * @return The {@link AlertifyConfig}.
	 */
	public AlertifyConfig build() {
		return new AlertifyConfig(type, label, closeDelay, callback);
	}
}
