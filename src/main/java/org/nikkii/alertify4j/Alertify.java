package org.nikkii.alertify4j;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;
import org.nikkii.alertify4j.themes.AlertifyTheme;
import org.nikkii.alertify4j.themes.BootstrapTheme;
import org.nikkii.alertify4j.tween.ComponentAccessor;
import org.nikkii.alertify4j.ui.AlertifyWindow;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A better Alert/Notification system for Java.
 *
 * Loosely based on Alertify.js
 *
 * @author Nikki
 */
public class Alertify {
	static {
		Tween.registerAccessor(AlertifyWindow.class, new ComponentAccessor());
	}

	/**
	 * We need to have only one instance otherwise notifications will overlap. Unfortunately, this means using a singleton.
	 */
	private static final Alertify instance = new Alertify();

	/**
	 * Get the single instance.
	 * @return The Alertify instance.
	 */
	public static Alertify instance() {
		return instance;
	}

	/**
	 * Shorthand for instance().setTheme()
	 *
	 * @param theme The theme class.
	 */
	public static void theme(AlertifyTheme theme) {
		instance.setTheme(theme);
	}

	/**
	 * Shorthand for instance().showAlert()
	 *
	 * @param config The alertify config.
	 */
	public static void show(AlertifyConfig config) {
		instance.showAlert(config);
	}

	/**
	 * Scheduled Executor used to auto close notifications.
	 */
	private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	/**
	 * The current alert theme.
	 */
	private AlertifyTheme theme = new BootstrapTheme();

	/**
	 * The list of visible windows.
	 */
	private final List<AlertifyWindow> windows = new LinkedList<>();

	/**
	 * The list of windows being removed. This is used to make sure no windows are moved while we're removing them.
	 */
	private final List<AlertifyWindow> remove = new LinkedList<>();

	/**
	 * The queue of windows which won't fit on screen until we close a couple.
	 */
	private final Queue<AlertifyConfig> windowQueue = new LinkedList<>();

	/**
	 * The TweenManager handling animations
	 */
	private final TweenManager manager = new TweenManager();

	/**
	 * Constructor which starts a single thread to update the manager.
	 * TODO time the update call and adjust accordingly.
	 */
	private Alertify() {
		new Thread(() -> {
			while (true) {
				manager.update(0.01f);

				try {
					Thread.sleep(10);
				} catch (Exception e) {
				}
			}
		}).start();
	}

	/**
	 * Shows an alert on the screen, based on the config passed.
	 *
	 * @param config The alert config.
	 * @return This class instance, best used for chaining.
	 */
	public Alertify showAlert(final AlertifyConfig config) {
		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

		synchronized(windows) {
			int baseY = screen.height;

			for (AlertifyWindow window : windows) {
				baseY -= window.getHeight() + 10;
			}

			if (baseY < 0) {
				// Append to a queue?
				windowQueue.add(config);
				return this;
			}

			final AlertifyWindow window = new AlertifyWindow(theme, config.getType(), config.getLabel());

			baseY -= window.getPreferredSize().height + 10;

			window.setLocation(screen.x + screen.width, baseY);
			window.pack();

			window.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (window.isHidden()) {
						return;
					}

					if (config.hasCallback()) {
						config.getCallback().alertClicked(window);
					}

					hideWindow(window);
				}
			});


			windows.add(window);

			showWindow(window, () -> {
				if (config.shouldAutoClose()) {
					window.setCloseFuture(scheduler.schedule(() -> hideWindow(window), config.getCloseDelay(), TimeUnit.MILLISECONDS));
				}
			});
		}
		return this;
	}

	/**
	 * Internal method to call the Tween animation.
	 *
	 * @param window The window to show.
	 * @param callback The callback to run after the window is shown.
	 */
	private void showWindow(AlertifyWindow window, Runnable callback) {
		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

		Tween
			.to(window, ComponentAccessor.POSITION_X, 0.5f)
			.setCallback((event, tween) -> {
				if (event == TweenCallback.START) // Show it when this starts
					window.setVisible(true);
				else if (event == TweenCallback.STEP) { // Attempt to hide the window off-screen
					int width = screen.width - 10 - window.getX(); // 10 extra for the spacing.
					if (width <= window.getActualWidth() + 1) {
						window.setSize(width, window.getActualHeight());
					}
				} else if (event == TweenCallback.COMPLETE)
					callback.run();
			})
			.setCallbackTriggers(TweenCallback.START | TweenCallback.STEP)
			.ease(Back.OUT)
			.target((screen.x + screen.width) - (window.getActualWidth() + 10))
			.start(manager);
	}

	/**
	 * Moves a window to the specified Y position.
	 *
	 * @param window The window to move.
	 * @param targetY The Y position on screen.
	 */
	private void moveWindow(AlertifyWindow window, int targetY) {
		Tween
			.to(window, ComponentAccessor.POSITION_Y, 0.5f)
			.ease(Back.IN)
			.target(targetY)
			.start(manager);
	}

	/**
	 * Hide a window.
	 *
	 * @param window The window to hide.
	 */
	private void hideWindow(AlertifyWindow window) {
		if (window.isHidden()) {
			return;
		}

		window.hideAlert();

		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

		remove.add(window);
		Tween.to(window, ComponentAccessor.POSITION_X, 0.5f)
			.ease(Back.IN)
			.target(screen.width)
			.setCallback((event, tween) -> {
				if (event == TweenCallback.COMPLETE)
					removeWindow(window);
				else if (event == TweenCallback.STEP)
					window.setSize(screen.width - window.getX(), window.getActualHeight());
			})
			.setCallbackTriggers(TweenCallback.COMPLETE | TweenCallback.STEP)
			.start(manager);
	}

	/**
	 * Remove a window. This consolidates remaining windows and shows ones which would not have fit on screen.
	 *
	 * @param window The window to remove.
	 */
	private void removeWindow(AlertifyWindow window) {
		window.dispose();

		synchronized(windows) {
			windows.remove(window);

			remove.remove(window);

			// if hide queue empty, consolidate?

			if (remove.isEmpty()) {
				consolidateWindows();
			}

			if (!windowQueue.isEmpty()) {
				show(windowQueue.poll());
			}
		}
	}

	/**
	 * Consolidate/move the windows so they're spaced evenly.
	 */
	private void consolidateWindows() {
		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

		int newY = screen.height;
		for (AlertifyWindow w : windows) {
			newY -= w.getHeight() + 10; // 10px spacing
			if (w.getY() != newY) {
				moveWindow(w, newY);
			}
		}
	}

	/**
	 * Set the window theme.
	 *
	 * @param theme The theme.
	 */
	public void setTheme(AlertifyTheme theme) {
		this.theme = theme;
	}
}
