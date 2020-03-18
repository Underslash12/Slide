import pkg.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Thread;
import java.util.Hashtable;
import java.awt.event.KeyEvent;

import static java.lang.Math.toIntExact;

public class SlidePuzzle implements InputKeyControl{

	private int width, height;
	private int[][] tiles;

	private SlidePuzzleGraphics sg;
	private boolean canMove = true;
	private boolean hasMoved = false;
	private boolean stop = false;

	private ConfigManager cm;
	private Hashtable<String, String[]> movementKeys;

	public SlidePuzzle (int w, int h)
	{
		cm = new ConfigManager("config.ini");
		setKeys();

		width = w;
		height = h;
		initTiles();

		sg = new SlidePuzzleGraphics(w, h);
		setGraphicsColor();
		sg.initGraphics();

		KeyController kC = new KeyController(Canvas.getInstance(), this);
	}

	public void initTiles ()
	{
		tiles = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tiles[i][j] = j + i * width;
			}
		}
	}

	public void shuffle (int times)
	{
		resetTimer();
		// Canvas.pause(100);
		for (int i = 0; i < times; i++) {
			makeRandomMove();
		}
		sg.move(0, new Point(0, 0), true);
		hasMoved = false;
	}

	public void makeRandomMove ()
	{
		Point emptyTile = getEmptyTile();
		ArrayList<Point> possiblePoints = getPossiblePoints(emptyTile.getX(), emptyTile.getY());
		Point v = possiblePoints.get((int)(Math.random() * possiblePoints.size()));
		Point p = new Point(emptyTile.getX() + v.getX(), emptyTile.getY() + v.getY());

		int val = tiles[p.getY()][p.getX()];
		tiles[emptyTile.getY()][emptyTile.getX()] = tiles[p.getY()][p.getX()];
		tiles[p.getY()][p.getX()] = (width * height - 1);
		// sg.move();
		sg.move(val, new Point(-v.getX(), -v.getY()), false);
	}

	public Point getEmptyTile ()
	{
		int xIndex = 0, yIndex = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tiles[i][j] == (width * height - 1)) {
					xIndex = j;
					yIndex = i;
				}
			}
		}
		return new Point(xIndex, yIndex);
	}

	public ArrayList<Point> getPossiblePoints (int x, int y)
	{
		// int[][] points = new int[getTotalPoints(x, y)][2];
		ArrayList<Point> points = new ArrayList<Point>();
		int[] p = {-1, 1};
		for (int i : p) {
			if (y + i >= 0 && y + i < height) {
				points.add(new Point(0, i));
			}
		}
		for (int j : p) {
			if (x + j >= 0 && x + j < width) {
				points.add(new Point(j, 0));
			}
		}
		// System.out.println(points);
		return points;
	}

	public SlidePuzzleGraphics getGraphics ()
	{
		return sg;
	}

	public void move (Point v)
	{
		if (!hasMoved) {
			startTimer();
			hasMoved = true;
		}

		canMove = false;
		Point vector = new Point(-v.getX(), -v.getY());
		Point emptyTile = getEmptyTile();

		int n_x = vector.getX() + emptyTile.getX();
		int n_y = vector.getY() + emptyTile.getY();
		if (n_x < 0 || n_x >= width || n_y < 0 || n_y >= height) {
			canMove = true;
			return;
		}

		int val = tiles[n_y][n_x];
		tiles[emptyTile.getY()][emptyTile.getX()] = tiles[n_y][n_x];
		tiles[n_y][n_x] = (width * height - 1);
		sg.move(val, v, true);
		canMove = true;
		// sg.move(new Point(0, 0), new Point(-1, 0), true);
		// print();
		if (solved()) {
			stopTimer();
		}
	}

	public boolean solved ()
	{
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tiles[i][j] != i * width + j) return false;
			}
		}
		return true;
	}

	public void startTimer ()
	{
		long m = System.currentTimeMillis();
		Thread t = new Thread () {
			public void run () {
				while (true) {
					// sets time to difference between start and current
					sg.setTimer(toIntExact(System.currentTimeMillis() - m));
					try {
					    Thread.sleep(1);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
					if (stop) {
						return;
					}
					// return;
				}
			}
		};
		t.start();
	}

	public void stopTimer ()
	{
		stop = true;
	}

	public void resetTimer ()
	{
		stopTimer();
		Canvas.pause(100);
		sg.setTimer(0);
		stop = false;
	}

	public void reset ()
	{
		initTiles();
		sg.reset();
		resetTimer();
	}

	public void setKeys ()
	{
		movementKeys = cm.getMovementKeys();
	}

	public void setGraphicsColor ()
	{
		sg.setColor(cm.getColor());
	}

	public boolean matchesKey (String inputKey, String keyToMatch)
	{
		// System.out.println(inputKey + " " + keyToMatch);
		if (!movementKeys.containsKey(keyToMatch)) return false;

		String[] allKeys = movementKeys.get(keyToMatch);
		for (String temp : allKeys) {
			if (inputKey.equals(temp)) return true;
		}
		return false;
	}

	public String formatArrowKey (String s, KeyEvent e)
	{
		String f;
		int keyCode = e.getKeyCode();
		// System.out.print(keyCode + " ");
	    switch( keyCode ) {
	        case KeyEvent.VK_UP:
	            f = "up";
	            break;
	        case KeyEvent.VK_DOWN:
	            f = "down";
	            break;
	        case KeyEvent.VK_LEFT:
	            f = "left";
	            break;
	        case KeyEvent.VK_RIGHT :
	            f = "right";
	            break;
			default:
				f = s;
	    }
		// System.out.println(f);
		return f;
	}

	public void keyPress (String s, KeyEvent e)
	{
		final String f = formatArrowKey(s, e);
		if (canMove) {
			// new thread to allow the graphics to update
			Thread t = new Thread() {
				public void run () {
					if (matchesKey(f, "up_key"))
						move(new Point(0, -1));
					else if (matchesKey(f, "down_key"))
						move(new Point(0, 1));
					else if (matchesKey(f, "left_key"))
						move(new Point(-1, 0));
					else if (matchesKey(f, "right_key"))
						move(new Point(1, 0));
					// interrupt();
				}
			};
			t.start();
		}
	}

	public void keyRelease (String s, KeyEvent e){}

	public void print ()
	{
		System.out.println();
		for (int[] i : tiles) {
			// System.out.println(Arrays.toString(i));
			for (int j : i) {
				System.out.print(j);
				System.out.print(j >= 10 ? " " : "  ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
