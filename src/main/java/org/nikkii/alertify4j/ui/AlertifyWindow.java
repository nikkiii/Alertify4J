package org.nikkii.alertify4j.ui;

import com.sun.awt.AWTUtilities;
import org.nikkii.alertify4j.AlertifyColorPair;
import org.nikkii.alertify4j.AlertifyConfig;
import org.nikkii.alertify4j.themes.AlertifyTheme;
import org.nikkii.alertify4j.util.Optional;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.ScheduledFuture;

/**
 * The alert bubble window.
 *
 * @author Nikki
 */
public class AlertifyWindow extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The real window width.
	 */
	private int actualWidth;

	/**
	 * The real window height.
	 */
	private int actualHeight;

	/**
	 * Flag for whether we're 'hidden'.
	 */
	private boolean hidden = false;

	/**
	 * The close future, if we are set to auto close.
	 */
	private ScheduledFuture<?> closeFuture;

	/**
	 * Construct a new window.
	 *
	 * @param theme The theme to construct from.
	 * @param config The alert config.
	 */
	public AlertifyWindow(AlertifyTheme theme, AlertifyConfig config) {
		AlertifyColorPair colors = theme.getColors(config.getType());

		if (colors == null) {
			throw new IllegalArgumentException("Theme does not have support for " + config.getType());
		}

		JLabel label = config.getLabel();

		Optional<Font> font = Optional.ofNullable(config.getFont());

		label.setFont(font.isPresent() ? font.get() : theme.getFont()); //checking null state to see which font to use
		label.setForeground(colors.getForeground());

		JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
		content.setBackground(colors.getBackground());

		// Configure the unique theme properties
		theme.configure(content);

		content.add(label);

		add(content);

		Dimension contentSize = content.getPreferredSize();

		actualWidth = Math.max(contentSize.width, 300);
		actualHeight = Math.max(contentSize.height, 64);

		setAlwaysOnTop(true);
		setPreferredSize(new Dimension(actualWidth, actualHeight));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0, 0, actualWidth, actualHeight, 4, 4));
	}

	/**
	 * Hide the alert, change the cursor, and remove the mouse listener.
	 */
	public void hideAlert() {
		hidden = true;
		// Set the cursor to the default one so it appears it can't be clicked
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// Disable mouse listener
		for (MouseListener listener : this.getListeners(MouseListener.class)) {
			removeMouseListener(listener);
		}
		// Cancel the auto close
		if (closeFuture != null) {
			closeFuture.cancel(false);
		}
	}

	/**
	 * Gets the hidden flag.
	 *
	 * @return The hidden flag.
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Sets the {@link ScheduledFuture} for the close task.
	 *
	 * @param closeFuture The close future.
	 */
	public void setCloseFuture(ScheduledFuture<?> closeFuture) {
		this.closeFuture = closeFuture;
	}


	/**
	 * Get the actual window width (bypass Component.getWidth()).
	 *
	 * @return The window width.
	 */
	public int getActualWidth() {
		return actualWidth;
	}

	/**
	 * Get the actual window height (bypass Component.getHeight()).
	 *
	 * @return The window height.
	 */
	public int getActualHeight() {
		return actualHeight;
	}
}
