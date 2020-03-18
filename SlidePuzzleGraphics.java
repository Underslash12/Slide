import pkg.*;
import java.awt.Dimension;

public class SlidePuzzleGraphics {

	private int animation = 12;
	private int delay = 2;
	private String colorScheme;
	// private String colorScheme = "pink-orange";

	private int width, height;
	private Rectangle[][] boxes;
	private Text[][] numbers;
	private Dimension windowSize;

	private int borderWidth = 40;
	private int borderThickness = borderWidth / 5;
	private Rectangle background, outerBorder, innerBorder;

	private Text timer;

	public SlidePuzzleGraphics (int w, int h)
	{
		windowSize = Canvas.getInstance().getSize();

		width = w;
		height = h;
		colorScheme = "none";
	}

	public void initGraphics ()
	{
		background = new Rectangle(0, 0, windowSize.getWidth(), windowSize.getHeight());
		background.setColor(new Color(244, 244, 244));
		background.fill();

		outerBorder = new Rectangle(
			borderWidth, borderWidth, windowSize.getWidth() - borderWidth * 2, windowSize.getHeight() - borderWidth * 2
		);
		outerBorder.setColor(new Color(145, 145, 145));
		outerBorder.fill();

		innerBorder = new Rectangle(
			outerBorder.getX() + borderThickness, outerBorder.getY() + borderThickness,
			outerBorder.getWidth() - borderThickness * 2, outerBorder.getHeight() - borderThickness * 2
		);
		innerBorder.setColor(new Color(229, 229, 229));
		innerBorder.fill();

		initBoxes();

		timer = new Text(0, 0, formatTime(0));
		timer.grow(3, 3);
		alignTimer(10);
		timer.draw();
		// boxes[height - 1][width - 1] = new Rectangle(0, 0, 0, 0);
		// numbers[height - 1][width - 1] = new Text(0, 0, "");

	}

	public void initBoxes ()
	{
		boxes = new Rectangle[height][width];
		numbers = new Text[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i != height - 1 || j != width - 1) {
					boxes[i][j] = new Rectangle(
						innerBorder.getX() + j * innerBorder.getWidth() / (width * 1D),
						innerBorder.getY() + i * innerBorder.getHeight() / (height * 1D),
						innerBorder.getWidth() / (width * 1D),
						innerBorder.getHeight() / (height * 1D)
					);
					boxes[i][j].setColor(getColorScheme(j, i));
					boxes[i][j].fill();

					numbers[i][j] = new Text(0, 0, "" + (i * width + j + 1));
					numbers[i][j].grow(4, 4);
					while (numbers[i][j].getHeight() > boxes[i][j].getHeight()) {
						numbers[i][j].grow(0.5, 0.5);
					}
					numbers[i][j].center(boxes[i][j]);
					numbers[i][j].setColor(new Color(43, 43, 43));
					numbers[i][j].draw();
				}
			}
		}
	}

	public void move (int movedTileVal, Point vector, boolean draw)
	{
		Point tileToMove = intToPoint(movedTileVal);
		double xMove = vector.getX() * (innerBorder.getWidth() / (width * 1D)) / animation * 1D;
		double yMove = vector.getY() * (innerBorder.getHeight() / (height * 1D)) / animation * 1D;
		if (draw) {
			for (int i = 0; i < animation; i++) {
				// System.out.println(i);
				boxes[tileToMove.getY()][tileToMove.getX()].translate(xMove, yMove);
				numbers[tileToMove.getY()][tileToMove.getX()].translate(xMove, yMove);
				Canvas.pause(delay);
			}
		} else {
			for (int i = 0; i < animation; i++) {
				// System.out.println(i);
				boxes[tileToMove.getY()][tileToMove.getX()].translateNoRe(xMove, yMove);
				numbers[tileToMove.getY()][tileToMove.getX()].translateNoRe(xMove, yMove);
				// Canvas.pause(delay);
			}
		}
	}

	public Point intToPoint (int num)
	{
		int x, y;
		x = num % width;
		y = num / height;
		return new Point(x, y);
	}

	public void setColor (String s)
	{
		colorScheme = s;
	}

	public Color getColorScheme (int xPos, int yPos)
	{
		// default
		if (colorScheme.equals("none")) {
			return new Color(196, 196, 196);
		}

		// flat, hex codes
		if (colorScheme.substring(0, 1).equals("#")) {
			return new Color(
			   Integer.valueOf(colorScheme.substring(1, 3), 16),
			   Integer.valueOf(colorScheme.substring(3, 5), 16),
			   Integer.valueOf(colorScheme.substring(5, 7), 16)
			);
		}

		// pattern case
		int normalized = yPos * width + xPos + 1;
		double x = xPos, y = yPos;
		switch (colorScheme) {
			case "pink-orange":
				// double sX = x / 2 + width / 4;
				// double sY = y / 2 + height / 4;
				double green = (x + (height - y)) / (height + width - 3) * 77 + 75;
				double blue = (x + y) / (height + width - 3) * 107;
				// System.out.println(green);
				return new Color(248, (int) green <= 255 ? (int) green : 255, (int) blue <= 255 ? (int) blue : 255);
			case "competetive-16":
				switch (normalized) {
					case 6:
					case 10:
					case 14:
						return new Color(77, 98, 255);
					case 7:
					case 8:
					case 11:
					case 12:
					case 15:
						return new Color(246, 215, 54);
					default:
						return new Color(176, 19, 202);
				}
		}

		// error case
		return (xPos + yPos) % 2 == 0 ? new Color(253, 0, 255) : new Color(0, 0, 0);
	}

	public String formatTime (int milliseconds)
	{
		// int mT = milliseconds / (60 * 1000);
		// String m =
		// int sT = (milliseconds / 1000) % 60;
		// int msT = milliseconds % 1000;
		// return "" + m + ":" + s + "." + ms;
		long millis = milliseconds % 1000;
		long second = (milliseconds / 1000) % 60;
		long minute = (milliseconds / (1000 * 60)) % 60;
		// long hour = (milliseconds / (1000 * 60 * 60)) % 24;

		return String.format("%02d:%02d.%03d", minute, second, millis);
	}

	public void alignTimer (double margin)
	{
		if (timer == null) return;

		timer.translateNoRe(-timer.getX(), -timer.getY());
		// top right
		timer.translate(windowSize.getWidth() - timer.getWidth() - margin, margin);
		// bottom right
		// timer.translate(windowSize.getWidth() - timer.getWidth() - margin,  + windowSize.getHeight() - timer.getHeight() - margin);
	}

	public void setTimer (int milliseconds)
	{
		timer.setText(formatTime(milliseconds));
		alignTimer(10);
	}

	public void reset ()
	{
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i != height - 1 || j != width - 1) {
					boxes[i][j].undraw();
					numbers[i][j].undraw();
				}
			}
		}
		initBoxes();
	}
}
