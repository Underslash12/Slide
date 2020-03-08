import pkg.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Thread;

public class SlidePuzzle implements InputKeyControl{

	private int width, height;
	private int[][] tiles;

	private SlidePuzzleGraphics sg;
	private boolean canMove = true;

	public SlidePuzzle (int w, int h)
	{
		width = w;
		height = h;
		initTiles();
		sg = new SlidePuzzleGraphics(w, h);

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
		for (int i = 0; i < times; i++) {
			makeRandomMove();
		}
		sg.move(0, new Point(0, 0), true);
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

	public void move (Point v)
	{
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
	}

	public void keyPress (String s)
	{
		if (canMove) {
			// new thread to allow the graphics to update
			Thread t = new Thread() {
				public void run() {
					if (s.equalsIgnoreCase("w")) {
						move(new Point(0, -1));
					} else if (s.equalsIgnoreCase("s")) {
						move(new Point(0, 1));
					} else if (s.equalsIgnoreCase("a")) {
						move(new Point(-1, 0));
					} else if (s.equalsIgnoreCase("d")) {
						move(new Point(1, 0));
					}
					interrupt();
				}
			};
			t.start();
		}
	}

	public void keyRelease (String s){}

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
