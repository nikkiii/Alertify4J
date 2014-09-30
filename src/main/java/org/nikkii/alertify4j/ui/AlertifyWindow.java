package org.nikkii.alertify4j.ui;

import org.nikkii.alertify4j.AlertifyColorPair;
import org.nikkii.alertify4j.AlertifyType;
import org.nikkii.alertify4j.themes.AlertifyTheme;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;
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

	private int actualWidth;
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
	 * @param type The alert type.
	 * @param label The alert label.
	 */
	public AlertifyWindow(AlertifyTheme theme, AlertifyType type, JLabel label) {
		AlertifyColorPair colors = theme.getColors(type);

		if (colors == null) {
			throw new IllegalArgumentException("Theme does not have support for " + type);
		}

		label.setFont(theme.getFont());
		label.setForeground(colors.getForeground());

		JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
		content.setBackground(colors.getBackground());
		content.setBorder(new EmptyBorder(15, 30, 15, 30));
		content.add(label);

		add(content);

		Dimension contentSize = content.getPreferredSize();

		actualWidth = Math.max(contentSize.width, 300);
		actualHeight = Math.max(contentSize.height, 64);

		setAlwaysOnTop(true);
		setPreferredSize(new Dimension(actualWidth, actualHeight));
		setShape(new RoundRectangle2D.Double(0, 0, actualWidth, actualHeight, 4, 4));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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


	public int getActualWidth() {
		return actualWidth;
	}

	public int getActualHeight() {
		return actualHeight;
	}
}
