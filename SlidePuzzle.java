import pkg.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SlidePuzzle {

	private int width, height;
	private int[][] tiles;

	private SlidePuzzleGraphics sg;

	public SlidePuzzle (int w, int h)
	{
		width = w;
		height = h;
		initTiles();
		sg = new SlidePuzzleGraphics(w, h);
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
	}

	public void makeRandomMove ()
	{
		Point emptyTile = getEmptyTile();
		ArrayList<Point> possiblePoints = getPossiblePoints(emptyTile.getX(), emptyTile.getY());
		Point p = possiblePoints.get((int)(Math.random() * possiblePoints.size()));
		// System.out.println(p);
		// System.out.println(tiles[emptyTile.getY()][emptyTile.getX()]);
		// System.out.println(tiles[p.getY()][p.getX()]);
		tiles[emptyTile.getY()][emptyTile.getX()] = tiles[p.getY()][p.getX()];
		// System.out.println(tiles[emptyTile.getY()][emptyTile.getX()]);
		tiles[p.getY()][p.getX()] = (width * height - 1);
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
				points.add(new Point(x, y + i));
			}
		}
		for (int j : p) {
			if (x + j >= 0 && x + j < width) {
				points.add(new Point(x + j, y));
			}
		}
		// System.out.println(points);
		return points;
	}

	// public int getTotalPoints (int x, int y)
	// {
	// 	int total = 4;
	// 	if (x == 0 || x == width - 1) total --;
	// 	if (y == 0 || y == height - 1) total --;
	// 	return total;
	// }

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
