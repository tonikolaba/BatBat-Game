package al.tonikolaba.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.swing.JPanel;

import al.tonikolaba.gamestate.GameStateManager;
//import al.tonikolaba.gamestate.GameStateManager;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.handlers.LoggingHelper;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	// dimensions
	// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int SCALE = 1;
	// public final int SCALE = (int) screenSize.getHeight() / HEIGHT;
	/**
	 *
	 */
	private static final long serialVersionUID = 1275876853084636658L;
	// game thread
	private transient Thread thread;
	private boolean running;
	private int fps = 60;
	private long targetTime = 1000 / fps;

	// image
	private transient BufferedImage image;
	private transient Graphics2D g;

	// game state manager
	private transient GameStateManager gsm;

	// other
	private boolean recording = false;
	private int recordingCount = 0;
	private boolean screenshot;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	private void init() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		running = true;

		gsm = new GameStateManager();

	}

	@Override
	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		// game loop
		while (running) {

			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
			}

		}

	}

	private void update() {
		gsm.update();
		Keys.update();
	}

	private void draw() {
		gsm.draw(g);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
		if (screenshot) {
			screenshot = false;
			try {
				java.io.File out = new java.io.File("screenshot " + System.nanoTime() + ".gif");
				javax.imageio.ImageIO.write(image, "gif", out);
			} catch (Exception e) {
				LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
			}
		}
		if (!recording)
			return;
		try {
			java.io.File out = new java.io.File("C:\\out\\frame" + recordingCount + ".gif");
			javax.imageio.ImageIO.write(image, "gif", out);
			recordingCount++;
		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.isControlDown()) {
			if (key.getKeyCode() == KeyEvent.VK_R) {
				recording = !recording;
				return;
			}
			if (key.getKeyCode() == KeyEvent.VK_S) {
				screenshot = true;
				return;
			}
		}
		Keys.keySet(key.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Not necessary

	}

}
