import pkg.*;
import java.awt.Dimension;

public class SlidePuzzleGraphics {

	private int width, height;
	private Rectangle[][] boxes;
	private Text[][] numbers;

	private int borderWidth = 40;
	private int borderThickness = 6;

	public SlidePuzzleGraphics (int w, int h)
	{
		width = w;
		height = h;
		initGraphics();
	}

	public void initGraphics ()
	{
		Dimension windowSize = Canvas.getInstance().getSize();

		Rectangle background = new Rectangle(0, 0, windowSize.getWidth(), windowSize.getHeight());
		background.setColor(new Color(244, 244, 244));
		background.fill();

		Rectangle outerBorder = new Rectangle(
			borderWidth, borderWidth, windowSize.getWidth() - borderWidth * 2, windowSize.getHeight() - borderWidth * 2
		);
		outerBorder.setColor(new Color(145, 145, 145));
		outerBorder.fill();

		Rectangle innerBorder = new Rectangle(
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
						innerBorder.getWidth() / width,
						innerBorder.getHeight() / height
					);
					boxes[i][j].setColor(new Color(196, 196, 196));
					boxes[i][j].fill();

					numbers[i][j] = new Text(0, 0, "" + (i * width + j + 1));
					numbers[i][j].grow(4, 4);
					numbers[i][j].center(boxes[i][j]);
					numbers[i][j].setColor(new Color(43, 43, 43));
					numbers[i][j].draw();
				}
			}
		}


	}
}
