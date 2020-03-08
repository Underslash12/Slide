import pkg.*;
import java.awt.Dimension;

public class SlidePuzzleGraphics {

	private int animation = 12;
	private int delay = 2;
	private String colorScheme = "rainbow";

	private int width, height;
	private Rectangle[][] boxes;
	private Text[][] numbers;
	private Dimension windowSize;

	private int borderWidth = 40;
	private int borderThickness = borderWidth / 5;
	private Rectangle background, outerBorder, innerBorder;

	public SlidePuzzleGraphics (int w, int h)
	{
		width = w;
		height = h;
		windowSize = Canvas.getInstance().getSize();
		initGraphics();
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

		boxes = new Rectangle[height][width];
		numbers = new Text[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i != height - 1 || j != width - 1) {
					boxes[i][j] = new Rectangle(
						innerBorder.getX() + j * innerBorder.getWidth() / width,
						innerBorder.getY() + i * innerBorder.getHeight() / height,
						innerBorder.getWidth() / (width * 1D),
						innerBorder.getHeight() / (height * 1D)
					);
					boxes[i][j].setColor(getColorScheme(j, i));
					boxes[i][j].fill();

					numbers[i][j] = new Text(0, 0, "" + (i * width + j + 1));
					numbers[i][j].grow(4, 4);
					numbers[i][j].center(boxes[i][j]);
					numbers[i][j].setColor(new Color(43, 43, 43));
					numbers[i][j].draw();
				}
			}
		}

		// boxes[height - 1][width - 1] = new Rectangle(0, 0, 0, 0);
		// numbers[height - 1][width - 1] = new Text(0, 0, "");

	}

	public void move (int movedTileVal, Point vector, boolean draw)
	{
		Point tileToMove = intToPoint(movedTileVal);
		double xMove = vector.getX() * (innerBorder.getWidth() / (width * 1D)) / animation;
		double yMove = vector.getY() * (innerBorder.getHeight() / (height * 1D)) / animation;
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

	public Color getColorScheme (int xPos, int yPos)
	{
		if (colorScheme.equals("flat")) {
			return new Color(196, 196, 196);
		}

		return (xPos + yPos) % 2 == 0 ? new Color(253, 0, 255) : new Color(0, 0, 0);
	}
}
